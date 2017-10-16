package pl.tjanek.email.provider.mailgun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailGunRequestFactory {

    private final String url;

    public MailGunRequestFactory(@Value("${mailgun.url}") String url) {
        this.url = url;
    }

    public String buildRequestUrl(MailGunRequestParams requestParams) {
        return url + buildUrlQueryParams(requestParams);
    }

    private String buildUrlQueryParams(MailGunRequestParams requestParams) {
        return "?from=" + requestParams.getFrom() +
                "&to=" + requestParams.getTo() +
                "&subject=" + requestParams.getSubject() +
                "&text=" + requestParams.getText();
    }

}
