package pl.tjanek.email.endpoint;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class EmailMessage {

    private Contact from;
    private Contact to;
    private String subject;
    private String message;

    public EmailMessage() {
    }

    public EmailMessage(Contact from, Contact to,
                        String subject, String message) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    public Contact getFrom() {
        return from;
    }

    public Contact getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}
