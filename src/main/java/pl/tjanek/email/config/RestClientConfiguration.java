package pl.tjanek.email.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfiguration {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
