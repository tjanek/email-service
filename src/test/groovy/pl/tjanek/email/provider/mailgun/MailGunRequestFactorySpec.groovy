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
                          '?from=John+Doe+%3Cjohn.doe%40company.com%3E' +
                          '&to=Jane+Doe+%3Cjane.doe%40company.com%3E' +
                          '&subject=Some+subject' +
                          '&text=Some+message'
    }

    MailGunRequestParams aRequestParams() {
        return new MailGunRequestParams(
            anEmailMessage()
        )
    }
}
