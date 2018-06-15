package hu.ortosch.vikwikileaks;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import hu.ortosch.vikwikileaks.services.SubjectNameGetter;
import hu.ortosch.vikwikileaks.web.LoginController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {VikWikiLeaksApplication.class, LoginController.class}, 
        loader = AnnotationConfigContextLoader.class)
@SpringBootTest
public class SubjectNameGetterIT {

    @Autowired
    SubjectNameGetter subjects;
    
    @Test
    public void getSubjectName() {
        assertEquals("Failed to load name by course code", 
                "Elektronikai rendszerek tervezése", subjects.getSubjectName("VIAUA000"));
        assertEquals("Failed to load name by course code", 
                "A programozás alapjai 2", subjects.getSubjectName("VIAUA116"));
    }
    
}
