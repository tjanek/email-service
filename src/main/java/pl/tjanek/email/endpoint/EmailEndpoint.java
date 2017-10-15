package pl.tjanek.email.endpoint;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/email")
public class EmailEndpoint {

    @PostMapping(value = "/send")
    public SendEmailResponse send(@RequestBody EmailMessage message) {
        return new SendEmailResponse(false);
    }
}
