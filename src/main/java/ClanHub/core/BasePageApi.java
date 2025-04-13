package ClanHub.core;

import ClanHub.dto.RegistRequestDto;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BasePageApi {
    public final String registrDto = "/auth/sign-up";

    public void errorRegistrationResponseValidation(RegistRequestDto requestBody, String keyName, String validationMessage) {
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().ifValidationFails()
                .post(registrDto)
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("error", equalTo("Validation Failed"))
                .assertThat().body("errors." + keyName, equalTo(validationMessage))
                .assertThat().body("status", equalTo(400));
    }

    public void userRegistration (RegistRequestDto requestBody) {
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(registrDto);
    }
}
