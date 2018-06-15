package hu.ortosch.vikwikileaks;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import hu.ortosch.vikwikileaks.model.SubjectEntity;
import hu.ortosch.vikwikileaks.services.SubjectService;

@Component
@PropertySource(value = "classpath:customnames.properties", encoding = "UTF-8")
public class CustomNameConfig {

    @Autowired
    SubjectService subjects;
    
    @Value("${values}")
    private String values;
    
    @PostConstruct
    public void loadCustomNames() {
        for (String sub : values.split(";")) {
            SubjectEntity se = subjects.getByIdOrNull(sub.split(" ", 2)[0]);
            if (se == null) {
                subjects.save(new SubjectEntity(sub.split(" ", 2)[0], sub.split(" ", 2)[1]));
            } else if (se.getName().length() == 0 || se.getName().equals("N/A")) {
                se.setName(sub.split(" ", 2)[1]);
                subjects.save(se);
            }
        }
    }
    
}
