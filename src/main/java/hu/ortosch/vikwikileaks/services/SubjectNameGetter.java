package hu.ortosch.vikwikileaks.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SubjectNameGetter {

    @Autowired
    RestTemplate rest;
    
    public String getSubjectName(String subject) {
        try {
            String result = rest.getForObject("https://portal.vik.bme.hu/kepzes/targyak/{subject}", String.class, subject);
            return result.substring(95, 95 + result.substring(95).indexOf('<'));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "N/A";
    }
    
}
