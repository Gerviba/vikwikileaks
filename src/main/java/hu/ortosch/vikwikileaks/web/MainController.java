package hu.ortosch.vikwikileaks.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import hu.ortosch.vikwikileaks.model.ResourceEntity;
import hu.ortosch.vikwikileaks.model.SubjectEntity;
import hu.ortosch.vikwikileaks.services.CommentService;
import hu.ortosch.vikwikileaks.services.ResourceService;
import hu.ortosch.vikwikileaks.services.SubjectService;

@Controller
public class MainController {

    @Autowired
    ControllerUtil util;
    
    @Autowired
    SubjectService subjects;
    
    @Autowired
    CommentService comments;

    @Autowired
    ResourceService resources;
    
    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }
 
    @GetMapping("/courses")
    public String courses(HttpServletRequest request, Map<String, Object> model) {
        model.put("subjects", subjects.getAll(util.getUser(request).getCourses()));
        return "courses";
    }
    
    @GetMapping("/course/{course}")
    public String course(@PathVariable String course, HttpServletRequest request, 
            Map<String, Object> model) {
        
        if (!util.getUser(request).getCourses().contains(course))
            return "redirect:/courses/?403";
        
        SubjectEntity subject = subjects.getById(course);
        model.put("name", subject.getName());
        model.put("code", subject.getId());
        model.put("files", resources.getAllByCourse(course));
        model.put("comments", comments.getAll(course));
        return "course";
    }
    
    @GetMapping("/course/{course}/file/{file}")
    public String getFile(@PathVariable String course, 
            @PathVariable Long file, 
            HttpServletRequest request, 
            Map<String, Object> model) {
        
        if (!util.getUser(request).getCourses().contains(course))
            return "redirect:/courses/?403";

        ResourceEntity res = resources.getById(file);
        if (res == null)
            return "redirect:/course/" + course + "?404";
        
        if (!res.getSubject().equals(course))
            return "redirect:/course/" + course + "?404";
        
        
        return "forward:/cdn/" + course + "/" + res.getStoredName();
    }
    
    @PostMapping("/course/{course}/comment")
    public String newComment(@PathVariable String course, 
            String comment,
            Long parent,
            HttpServletRequest request) {
        
        if (!util.getUser(request).getCourses().contains(course))
            return "redirect:/courses/?403";
        
        if (parent == -1) {
            comments.addRootComment(util.getUser(request).getName(), 
                    comment, course, System.currentTimeMillis());
        } else {
            comments.addSubComment(util.getUser(request).getName(), 
                    comment, course, System.currentTimeMillis(), parent);
        }
        
        return "redirect:/course/" + course + "/";
    }
    
    @PostMapping("/course/{course}/file")
    public String adminAddCircle(HttpServletRequest request, 
            @PathVariable String course, 
            String displayName,
            @RequestParam MultipartFile file) throws IllegalStateException, IOException {
        
        if (!util.isValidFileName(file.getOriginalFilename())) 
            return "redirect:/course/" + course + "?invalid-filename";
        
        String fileName = util.uploadFile(course, file);
        if (fileName == null)
            return "redirect:/course/" + course + "?failed-to-upload";
        
        resources.save(new ResourceEntity(util.getUser(request).getOwnershipId(), 
                displayName, fileName, 
                util.getExtention(file.getOriginalFilename()),
                course, System.currentTimeMillis()));

        return "redirect:/course/" + course + "/";
    }
    
    
    
}
