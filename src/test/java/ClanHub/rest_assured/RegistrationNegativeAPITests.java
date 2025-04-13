package ClanHub.rest_assured;

import ClanHub.core.BasePageApi;
import ClanHub.core.TestBaseAPI;
import ClanHub.data.RegistrData;
import ClanHub.dto.RegistRequestDto;
import ClanHub.utils.DataProvidersAPI;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RegistrationNegativeAPITests extends TestBaseAPI {

    @Test
    public void registrationEmptyEmailNegativeTest() {
        new BasePageApi()
                .errorRegistrationResponseValidation(
                        RegistrData.emptyEmailUser,
                        "email",
                        "Email cannot be empty"
                );
    }

    @Test
    public void registrationEmptyUsernameNegativeTest() {
        new BasePageApi()
                .errorRegistrationResponseValidation(
                        RegistrData.emptyUsernameUser,
                        "username",
                        "must not be blank"
                );
    }

    @Test
    public void registrationEmptyPasswordNegativeTest() {
        new BasePageApi()
                .errorRegistrationResponseValidation(
                        RegistrData.emptyPasswordUser,
                        "password",
                        "Password cannot be empty"
                );
    }

    @Test
    public void registrationEmptyAvatarNegativeTest() {
        new BasePageApi()
                .errorRegistrationResponseValidation(
                        RegistrData.emptyAvatarUser,
                        "avatarId",
                        "Avatar must not be empty"
                );
    }

    @Test
    public void registrationEmptyAgeNegativeTest() {
        new BasePageApi()
                .errorRegistrationResponseValidation(
                        RegistrData.emptyAgeUser,
                        "age",
                        "Age must not be empty"
                );
    }

    @Test(dataProvider = "registrationInvalidUsernameAPIFromCsv", dataProviderClass = DataProvidersAPI.class)
    public void registrationInvalidUsernameNegativeTest(RegistRequestDto invalidUsernameBody, String validationMessage) {
        new BasePageApi()
                .errorRegistrationResponseValidation(
                        invalidUsernameBody,
                        "username",
                        validationMessage
                );
    }

    @Test(dataProvider = "registrationInvalidPasswordDataProvider", dataProviderClass = DataProvidersAPI.class)
    public void registrationInvalidPasswordNegativeTest(RegistRequestDto invalidPasswordBody, String validationMessage) {
        new BasePageApi()
                .errorRegistrationResponseValidation(
                        invalidPasswordBody,
                        "password",
                        validationMessage
                );
    }

    @Test(dataProvider = "registrationInvalidAgeDataProvider", dataProviderClass = DataProvidersAPI.class)
    public void registrationInvalidAgeNegativeTest(RegistRequestDto invalidAgeBody, String validationMessage) {
        new BasePageApi()
                .errorRegistrationResponseValidation(
                        invalidAgeBody,
                        "age",
                        validationMessage
                );
    }

    @Test(dataProvider = "registrationInvalidEmailFromCsv", dataProviderClass = DataProvidersAPI.class)
    public void registrationInvalidEmailNegativeTest(RegistRequestDto invalidEmailBody) {
        new BasePageApi()
                .errorRegistrationResponseValidation(
                        invalidEmailBody,
                        "email",
                        "Email must be a valid email address with a proper domain"
                );
    }

    @Test(dataProvider = "registrationInvalidAvatarIdFromCsv", dataProviderClass = DataProvidersAPI.class)
    public void registrationInvalidAvatarIDNegativeTest(RegistRequestDto invalidAvatarBody) {
        new BasePageApi()
                .errorRegistrationResponseValidation(
                        invalidAvatarBody,
                        "avatarId",
                        "Invalid avatar ID. Must be between avatar1 and avatar6"
                );
    }

    @Test
    public void registrationExistingUserNegativeTest() {

        RegistRequestDto user = RegistrData.validBody;
        new BasePageApi().userRegistration(user);

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .log().ifValidationFails()
                .post(registrDto)
                .then()
                .assertThat().statusCode(409)
                .assertThat().body(equalTo("User with email " + user.getEmail() + " already exists"));
    }

    @Test
    public void registrationGetRequestNegativeTest() {
        given()
                .when()
                .log().ifValidationFails()
                .get(registrDto)
                .then()
                .assertThat().statusCode(405)
                .assertThat().body("error", equalTo("Method Not Allowed"))
                .assertThat().body("message", equalTo("Only POST method is allowed for this endpoint"));
    }

    @Test
    public void registrationWrongEndpointNegativeTest() {
        given()
                .contentType(ContentType.JSON)
                .body(RegistrData.validBody)
                .log().ifValidationFails()
                .post("/auth")
                .then()
                .assertThat().statusCode(404)
                .assertThat().body(equalTo("The endpoint you are trying to reach does not exist."));
    }
}
