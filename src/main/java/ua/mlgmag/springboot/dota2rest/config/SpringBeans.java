package ua.mlgmag.springboot.dota2rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringBeans {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
