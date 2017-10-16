package pl.tjanek.email.provider.mailgun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.tjanek.email.endpoint.EmailMessage;

@Service
@Slf4j
public class MailGunEmailProvider {

    private static final Object EMPTY_PAYLOAD = null;

    private final RestTemplate restTemplate;
    private final MailGunRequestFactory requestFactory;

    public MailGunEmailProvider(RestTemplate restTemplate, MailGunRequestFactory requestFactory) {
        this.restTemplate = restTemplate;
        this.requestFactory = requestFactory;
    }

    public boolean send(EmailMessage message) {
        try {
            String requestUrl = requestFactory.buildRequestUrl(new MailGunRequestParams(message));
            ResponseEntity<Object> responseEntity = restTemplate.postForEntity(requestUrl, EMPTY_PAYLOAD, Object.class);
            return responseEntity.getStatusCode().equals(HttpStatus.OK);
        } catch (Exception e) {
            log.error("There was an error when trying to send email via MailGun: ", e);
        }
        return false;
    }

}
