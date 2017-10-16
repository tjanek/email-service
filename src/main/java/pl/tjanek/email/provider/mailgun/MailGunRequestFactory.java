package pl.tjanek.email.provider.mailgun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
        return "?from=" + encodeQueryParamValue(requestParams.getFrom()) +
                "&to=" + encodeQueryParamValue(requestParams.getTo()) +
                "&subject=" + encodeQueryParamValue(requestParams.getSubject()) +
                "&text=" + encodeQueryParamValue(requestParams.getText());
    }

    private String encodeQueryParamValue(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("Error in encoding mailGun query param value: ", e);
            throw new RuntimeException(e);
        }
    }

}
