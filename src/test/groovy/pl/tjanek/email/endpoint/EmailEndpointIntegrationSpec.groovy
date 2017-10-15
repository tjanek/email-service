package pl.tjanek.email.endpoint

import com.jayway.restassured.response.Response
import pl.tjanek.email.IntegrationSpec

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static java.lang.String.format
import static pl.tjanek.email.EmailMessageBuilder.*
import static pl.tjanek.email.endpoint.SendEmailStatus.NOT_SENT
import static pl.tjanek.email.endpoint.SendEmailStatus.SENT

class EmailEndpointIntegrationSpec extends IntegrationSpec {

    def 'should send email when mailGun server is available'() {
        given:
            EmailMessage message = anEmailMessage(
                JOHN_DOE, JANE_DOE,
                'Greetings', 'Hello')

        and:
            mailGunIsUp()

        when:
            Response response = post('/email/send', message)

        then:
            verifyThatMailGunWasCalled(message)

        and:
            messageWasSent(response)

    }

    def 'should not send email when mailGun server is failing to accept request'() {
        given:
            EmailMessage message = anEmailMessage(
                JOHN_DOE, JANE_DOE,
                'Greetings', 'Hello')

        and:
            mailGunIsFailing()

        when:
            Response response = post('/email/send', message)

        then:
            verifyThatMailGunWasCalled(message)

        and:
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
            verifyThatMailGunWasCalled(message)

        and:
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

    void verifyThatMailGunWasCalled(EmailMessage message) {
        verify(postRequestedFor(urlEqualTo("/mailgun-test"))
                .withQueryParam("from", equalTo(contactInfo(message.from)))
                .withQueryParam("to", equalTo(contactInfo(message.to)))
                .withQueryParam("subject", equalTo(message.subject))
                .withQueryParam("text", equalTo(message.message))
        )
    }

    void mailGunIsUp() {
        stubFor(post(urlPathEqualTo("/mailgun-test"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                )
        )
    }

    void mailGunIsFailing() {
        stubFor(post(urlPathEqualTo("/mailgun-test"))
                .willReturn(
                    aResponse()
                        .withStatus(500)
                )
        )
    }

    String contactInfo(Contact contact) {
        return format("%s <%s>", contact.name, contact.email)
    }

}
