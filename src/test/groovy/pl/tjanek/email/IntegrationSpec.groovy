package pl.tjanek.email

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.restassured.RestAssured
import com.jayway.restassured.parsing.Parser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static com.jayway.restassured.RestAssured.given
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@ActiveProfiles(["test"])
@SpringBootTest(webEnvironment=RANDOM_PORT)
@ContextConfiguration
@AutoConfigureWireMock(port = 0)
abstract class IntegrationSpec extends Specification {

    @Autowired
    ObjectMapper jsonMapper

    @LocalServerPort
    int port

    def setup() {
        RestAssured.port = port
        RestAssured.defaultParser = Parser.JSON
    }

    String asJson(Object object) {
        return jsonMapper.writeValueAsString(object)
    }

    def post(String path, Object payload) {
        given()
                .contentType("application/json;charset=UTF-8")
                .body(asJson(payload))
                .when()
                .post(path)
                .thenReturn()
    }

}
