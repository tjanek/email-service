package pl.tjanek.email.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfiguration {

    @Value("${mailgun.api-key}")
    String mailGunApiKey;

    @Bean
    RestTemplate mailGunRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .basicAuthorization("api", mailGunApiKey)
                .build();
    }
}
