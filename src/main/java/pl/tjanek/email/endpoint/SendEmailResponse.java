package pl.tjanek.email.endpoint;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static pl.tjanek.email.endpoint.SendEmailStatus.NOT_SENT;
import static pl.tjanek.email.endpoint.SendEmailStatus.SENT;

@JsonAutoDetect(fieldVisibility = ANY)
public class SendEmailResponse {

    private SendEmailStatus status;

    public SendEmailResponse() {
    }

    public SendEmailResponse(boolean sent) {
        this.status = sent ? SENT : NOT_SENT;
    }

    public SendEmailStatus getStatus() {
        return status;
    }
}
