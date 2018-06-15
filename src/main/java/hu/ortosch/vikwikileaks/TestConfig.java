package hu.ortosch.vikwikileaks;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import hu.ortosch.vikwikileaks.model.ResourceEntity;
import hu.ortosch.vikwikileaks.services.CommentService;
import hu.ortosch.vikwikileaks.services.ResourceService;

@Component
@Profile("test")
public class TestConfig {

    @Autowired
    CommentService comments;
    
    @Autowired
    ResourceService resources;
    
    @PostConstruct
    public void insertDbData() {
        Long id = comments.addRootComment("BATMAN0000000000000000000000000000000000", 
                "This is the message of my comment.",
                "VIIIAA03",
                System.currentTimeMillis());
        comments.addSubComment("1234567890abcdefghijklmnopqrstuvwxyzabcd", 
                "This is a subcomment", 
                "VIIIAA03", 
                System.currentTimeMillis() + 1, id);
        comments.addRootComment("HITMAN7890abcdefghijklmnopqrstuvwxyzabcd", 
                "This is another comment. It is created way after the first one.",
                "VIIIAA03",
                System.currentTimeMillis() + 2);
        comments.addSubComment("BATMAN0000000000000000000000000000000000", 
                "This is a subcomment posted after the new comment", 
                "VIIIAA03", 
                System.currentTimeMillis() + 3, id);
        
        resources.save(new ResourceEntity("TESTER", "How about no", "29345869_1908508009181373_828844169_n.jpg", 
                "jpg", "VIIIAA03", System.currentTimeMillis()));
        resources.save(new ResourceEntity("TESTER", "Test2 description", "33343063_1938755336168780_5712196840280031232_n.jpg", 
                "jpg", "VIIIAA03", System.currentTimeMillis() + 4000000));
        resources.save(new ResourceEntity("TESTER", "This is a longer description", "33475150_1937539469623700_6218553697564098560_n.jpg", 
                "jpg", "VIIIAA03", System.currentTimeMillis() + 9000000));
    }
    
}
