package pl.tjanek.email.provider.mailgun;

import pl.tjanek.email.endpoint.Contact;
import pl.tjanek.email.endpoint.EmailMessage;

import static java.lang.String.format;

public class MailGunRequestParams {

    private final String from;
    private final String to;
    private final String subject;
    private final String text;

    public MailGunRequestParams(EmailMessage message) {
        this.from = formatContact(message.getFrom());
        this.to = formatContact(message.getTo());
        this.subject = message.getSubject();
        this.text = message.getMessage();
    }

    private String formatContact(Contact contact) {
        return format("%s <%s>", contact.getName(), contact.getEmail());
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }
}
