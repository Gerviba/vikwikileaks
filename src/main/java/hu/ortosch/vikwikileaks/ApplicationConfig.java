package hu.ortosch.vikwikileaks;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import hu.ortosch.vikwikileaks.web.ControllerUtil;

@Configuration
public class ApplicationConfig {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public ControllerUtil controllerUtil() {
        return new ControllerUtil();
    }
    
}
