package pl.tjanek.email.provider.mailgun

import spock.lang.Specification
import spock.lang.Subject

import static pl.tjanek.email.EmailMessageBuilder.anEmailMessage

class MailGunRequestFactorySpec extends Specification {

    static final MAILGUN_URL = 'mailgun-url'

    @Subject
    MailGunRequestFactory factory = new MailGunRequestFactory(MAILGUN_URL)

    def 'should create request url for sending email message to mailgun'() {
        given:
            MailGunRequestParams requestParams = aRequestParams()

        when:
            String requestUrl = factory.buildRequestUrl(requestParams)

        then:
            requestUrl == 'mailgun-url' +
                          '?from=John Doe <john.doe@company.com>' +
                          '&to=Jane Doe <jane.doe@company.com>' +
                          '&subject=Some subject' +
                          '&text=Some message'
    }

    MailGunRequestParams aRequestParams() {
        return new MailGunRequestParams(
            anEmailMessage()
        )
    }
}
