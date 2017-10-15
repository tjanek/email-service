package pl.tjanek.email.endpoint

import com.jayway.restassured.response.Response
import pl.tjanek.email.IntegrationSpec

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static java.lang.String.format
import static pl.tjanek.email.endpoint.SendEmailStatus.NOT_SENT
import static pl.tjanek.email.endpoint.SendEmailStatus.SENT

class EmailEndpointIntegrationSpec extends IntegrationSpec {

    static final Contact JOHN_DOE = new Contact("John Doe", "john.doe@company.com")
    static final Contact JANE_DOE = new Contact("Jane Doe", "jane.doe@company.com")

    def 'should send email when mailGun server is available'() {
        given:
            EmailMessage message = anEmailMessage(
                JOHN_DOE, JANE_DOE,
                'Greetings', 'Hello')

        and:
            mailGunIsUp(message)

        when:
            Response response = post('/email/send', message)

        then:
            messageWasSent(response)
    }

    def 'should not send email when mailGun server is failing to accept request'() {
        given:
            EmailMessage message = anEmailMessage(
                JOHN_DOE, JANE_DOE,
                'Greetings', 'Hello')

        and:
            mailGunIsFailing(message)

        when:
            Response response = post('/email/send', message)

        then:
            messageNotSent(response)
    }

    def 'should not send email when mailGun server is down'() {
        given:
            EmailMessage message = anEmailMessage(
                JOHN_DOE, JANE_DOE,
                'Greetings', 'Hello')

        when:
            Response response = post('/email/send', message)

        then:
            messageNotSent(response)
    }

    void messageWasSent(Response response) {
        assert response.statusCode == 200
        assert response.as(SendEmailResponse).status == SENT
    }

    void messageNotSent(Response response) {
        assert response.statusCode == 200
        assert response.as(SendEmailResponse).status == NOT_SENT
    }

    void mailGunIsUp(EmailMessage message) {
        stubFor(post(urlPathEqualTo("/mailgun-test"))
                .withQueryParam("from", equalTo(contactInfo(message.from)))
                .withQueryParam("to", equalTo(contactInfo(message.to)))
                .withQueryParam("subject", equalTo(message.subject))
                .withQueryParam("text", equalTo(message.message))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                )
        )
    }

    void mailGunIsFailing(EmailMessage message) {
        stubFor(post(urlPathEqualTo("/mailgun-test"))
                .withQueryParam("from", equalTo(contactInfo(message.from)))
                .withQueryParam("to", equalTo(contactInfo(message.to)))
                .withQueryParam("subject", equalTo(message.subject))
                .withQueryParam("text", equalTo(message.message))
                .willReturn(
                    aResponse()
                        .withStatus(500)
                )
        )
    }

    EmailMessage anEmailMessage(Contact from, Contact to, String subject, String message) {
        return new EmailMessage(from, to, subject, message)
    }

    String contactInfo(Contact contact) {
        return format("%s <%s>", contact.name, contact.email)
    }

}
