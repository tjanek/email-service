package pl.tjanek.email.provider.mailgun

import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static org.springframework.http.HttpMethod.POST
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import static pl.tjanek.email.EmailMessageBuilder.anEmailMessage

@Unroll
class MailGunEmailProviderSpec extends Specification {

    @Subject
    MailGunEmailProvider provider

    RestTemplate restClient = Stub()
    MailGunRequestFactory requestFactory = Stub()

    def setup() {
        provider = new MailGunEmailProvider(restClient, requestFactory)
    }

    def 'should mark email as sent: #expected when mailGun return status: #status'() {
        given:
            mailGunServiceIsAvailableAt('mailgun-test')

        and:
            mailGunServiceRespond('mailgun-test', httpStatusIs(status))

        expect:
            sentUsingMailGunProvider() == expected

        where:
            status                | expected
            OK                    | true
            CREATED               | false
            BAD_REQUEST           | false
            UNPROCESSABLE_ENTITY  | false
            INTERNAL_SERVER_ERROR | false
    }

    def 'should not sent email when building request url failed'() {
        given:
            mailGunServiceIsNotAvailable()

        expect:
            !sentUsingMailGunProvider()

    }

    def 'should not sent email when sending email using mailGun failed'() {
        given:
            mailGunServiceIsAvailableAt('mailgun-test')

        and:
            mailGunServiceNotResponding('mailgun-test')

        expect:
            !sentUsingMailGunProvider()

    }

    boolean sentUsingMailGunProvider() {
        return provider.send(anEmailMessage())
    }

    void mailGunServiceRespond(String url, Object result) {
        restClient.exchange(url, POST, (HttpEntity)_, Object) >> result
    }

    void mailGunServiceNotResponding(String url) {
        restClient.exchange(url, POST, (HttpEntity)_, Object) >> new RuntimeException()
    }

    void mailGunServiceIsAvailableAt(Object url) {
        requestFactory.buildRequestUrl(_) >> url
    }

    void mailGunServiceIsNotAvailable() {
        requestFactory.buildRequestUrl(_) >> new RuntimeException()
    }

    ResponseEntity<Object> httpStatusIs(HttpStatus status) {
        return ResponseEntity.status(status).build()
    }

}
