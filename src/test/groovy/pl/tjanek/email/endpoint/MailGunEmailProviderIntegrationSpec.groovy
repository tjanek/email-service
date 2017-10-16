package pl.tjanek.email.endpoint

import com.github.tomakehurst.wiremock.client.BasicCredentials
import com.jayway.restassured.response.Response
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import pl.tjanek.email.IntegrationSpec

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static pl.tjanek.email.EmailMessageBuilder.*
import static pl.tjanek.email.endpoint.SendEmailStatus.NOT_SENT
import static pl.tjanek.email.endpoint.SendEmailStatus.SENT

@AutoConfigureWireMock(port = 8020)
class MailGunEmailProviderIntegrationSpec extends IntegrationSpec {

    def 'should send email when mailGun provider is available'() {
        given:
            mailGunProviderIsAvailable()

        and:
            EmailMessage message = anEmailMessage(
                JOHN_DOE, JANE_DOE,
                'Greetings', 'Hello')

        when:
            Response response = post('/email/send', message)

        then:
            verifyThatMailGunProviderWasUsed()

        and:
            messageWasSent(response)

    }

    def 'should not send email when mailGun provider is failing to accept request'() {
        given:
            mailGunProviderIsFailing()

        and:
            EmailMessage message = anEmailMessage(
                JOHN_DOE, JANE_DOE,
                'Greetings', 'Hello')

        when:
            Response response = post('/email/send', message)

        then:
            verifyThatMailGunProviderWasUsed()

        and:
            messageNotSent(response)

    }

    def 'should not send email when mailGun provider is down'() {
        given:
            EmailMessage message = anEmailMessage(
                JOHN_DOE, JANE_DOE,
                'Greetings', 'Hello')

        when:
            Response response = post('/email/send', message)

        then:
            verifyThatMailGunProviderWasUsed()

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

    void verifyThatMailGunProviderWasUsed() {
        verify(postRequestedFor(urlPathEqualTo("/mailgun-test"))
                .withBasicAuth(new BasicCredentials("api", "key-12345"))
                .withQueryParam("from", equalTo("John Doe <john.doe@company.com>"))
                .withQueryParam("to", equalTo("Jane Doe <jane.doe@company.com>"))
                .withQueryParam("subject", equalTo("Greetings"))
                .withQueryParam("text", equalTo("Hello"))
        )
    }

    void mailGunProviderIsAvailable() {
        stubFor(post(urlPathEqualTo("/mailgun-test"))
                .withBasicAuth("api", "key-12345")
                .willReturn(
                    aResponse()
                        .withStatus(200)
                )
        )
    }

    void mailGunProviderIsFailing() {
        stubFor(post(urlPathEqualTo("/mailgun-test"))
                .withBasicAuth("api", "key-12345")
                .willReturn(
                    aResponse()
                        .withStatus(400)
                )
        )
    }

}
