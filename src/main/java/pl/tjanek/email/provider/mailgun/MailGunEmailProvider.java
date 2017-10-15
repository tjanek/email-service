package pl.tjanek.email.provider.mailgun;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.tjanek.email.endpoint.EmailMessage;

@Service
public class MailGunEmailProvider {

    private final RestTemplate restTemplate;
    private final MailGunRequestFactory requestFactory;

    public MailGunEmailProvider(RestTemplate restTemplate, MailGunRequestFactory requestFactory) {
        this.restTemplate = restTemplate;
        this.requestFactory = requestFactory;
    }

    public boolean send(EmailMessage message) {
        return false;
    }

}
