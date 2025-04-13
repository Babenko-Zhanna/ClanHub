package ClanHub.rest_assured;

import ClanHub.core.TestBaseAPI;
import ClanHub.dto.RegistRequestDto;
import ClanHub.dto.RegistResponseDto;
import ClanHub.utils.DataProvidersAPI;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class RegistrationPositiveAPITests extends TestBaseAPI {

    SoftAssert softAssert = new SoftAssert();

    @Test(dataProvider = "registrationValidUserDataFromCsv", dataProviderClass = DataProvidersAPI.class)
    public void registrationTokenPositiveTest(RegistRequestDto validBody) {
        String token = given()
                .contentType(ContentType.JSON)
                .body(validBody)
                .log().ifValidationFails()
                .post(registrDto)
                .then()
                .assertThat()
                .statusLine("HTTP/1.1 201 Created")
                .body(containsString("token"))
                .extract().path("token");

        softAssert.assertNotNull(token, "JWT Token cannot be null");
        softAssert.assertFalse(token.isBlank(), "JWT Token cannot be blank or empty");
        softAssert.assertTrue(token.matches("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+$"), "JWT Token has wrong format");
        softAssert.assertAll();
    }

    @Test(dataProvider = "registrationValidUserDataFromCsv", dataProviderClass = DataProvidersAPI.class)
    public void registrationResponseKeysPositiveTest(RegistRequestDto validBody) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(validBody)
                .log().ifValidationFails()
                .when()
                .post(registrDto)
                .then()
                .assertThat()
                .statusCode(201)
                .extract().response();

        Map<String, Object> jsonMap = response.jsonPath().get("$");
        List<String> responseKeys = jsonMap.keySet().stream().toList();

        softAssert.assertTrue(responseKeys.size() == 4, "There are extra main keys in the response");
        softAssert.assertTrue(responseKeys.contains("user"));
        softAssert.assertTrue(responseKeys.contains("message"));
        softAssert.assertTrue(responseKeys.contains("token"));
        softAssert.assertTrue(responseKeys.contains("status"));
        softAssert.assertAll();
    }

    @Test(dataProvider = "registrationValidUserDataFromCsv", dataProviderClass = DataProvidersAPI.class)
    public void registrationResponseDtoPositiveTest(RegistRequestDto validBody) {

        RegistResponseDto responseDto = given()
                .contentType(ContentType.JSON)
                .body(validBody)
                .log().ifValidationFails()
                .post(registrDto)
                .then()
                .assertThat()
                .statusCode(201)
                .extract().response().as(RegistResponseDto.class, ObjectMapperType.GSON);

        String message = responseDto.getMessage();
        softAssert.assertEquals(message, "User successfully registered");

        String status = responseDto.getStatus();
        softAssert.assertEquals(status, "success");

        String email = responseDto.getUser().getEmail();
        softAssert.assertEquals(email, validBody.getEmail());

        int age = responseDto.getUser().getAge();
        softAssert.assertTrue(Objects.equals(age, validBody.getAge()));

        softAssert.assertAll();
    }
}
