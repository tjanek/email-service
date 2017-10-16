package pl.tjanek.email.provider.mailgun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.tjanek.email.endpoint.EmailMessage;

import static org.springframework.http.HttpMethod.POST;

@Service
@Slf4j
public class MailGunEmailProvider {

    private final RestTemplate restTemplate;
    private final MailGunRequestFactory requestFactory;

    public MailGunEmailProvider(RestTemplate restTemplate, MailGunRequestFactory requestFactory) {
        this.restTemplate = restTemplate;
        this.requestFactory = requestFactory;
    }

    public boolean send(EmailMessage message) {
        try {
            String requestUrl = requestFactory.buildRequestUrl(new MailGunRequestParams(message));
            ResponseEntity<Object> responseEntity = exchange(requestUrl);
            return responseEntity.getStatusCode().equals(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            log.error("There was an error when trying to send email via MailGun: ", e);
            log.error("Response from MailGun: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("There was an error when trying to send email via MailGun: ", e);
        }
        return false;
    }

    private ResponseEntity<Object> exchange(String requestUrl) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(httpHeaders);
        return restTemplate.exchange(requestUrl, POST, requestEntity, Object.class);
    }

}
