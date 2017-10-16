package pl.tjanek.email.provider.mailgun

import pl.tjanek.email.endpoint.EmailMessage
import spock.lang.Specification

import static pl.tjanek.email.EmailMessageBuilder.anEmailMessage

class MailGunRequestParamsSpec extends Specification {

    def 'should get contact \'from\' info from email message'() {
        given:
            EmailMessage message =  anEmailMessage()

        when:
            MailGunRequestParams params = new MailGunRequestParams(message)

        then:
            params.from == 'John Doe <john.doe@company.com>'
    }

    def 'should get contact \'to\' info from email message'() {
        given:
            EmailMessage message =  anEmailMessage()

        when:
            MailGunRequestParams params = new MailGunRequestParams(message)

        then:
            params.to == 'Jane Doe <jane.doe@company.com>'
    }

    def 'should get contact \'subject\' info from email message'() {
        given:
            EmailMessage message =  anEmailMessage()

        when:
            MailGunRequestParams params = new MailGunRequestParams(message)

        then:
            params.subject == 'Some subject'
    }

    def 'should get contact \'text\' info from email message'() {
        given:
            EmailMessage message =  anEmailMessage()

        when:
            MailGunRequestParams params = new MailGunRequestParams(message)

        then:
            params.text == 'Some message'
    }

}
