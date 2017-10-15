package pl.tjanek.email.endpoint;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tjanek.email.provider.mailgun.MailGunEmailProvider;

@RestController
@RequestMapping(value = "/email")
public class EmailEndpoint {

    private final MailGunEmailProvider emailProvider;

    public EmailEndpoint(MailGunEmailProvider emailProvider) {
        this.emailProvider = emailProvider;
    }

    @PostMapping(value = "/send")
    public SendEmailResponse send(@RequestBody EmailMessage message) {
        boolean sent = emailProvider.send(message);
        return new SendEmailResponse(sent);
    }
}
