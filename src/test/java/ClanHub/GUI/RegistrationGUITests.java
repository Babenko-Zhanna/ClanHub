package ClanHub.GUI;

import ClanHub.core.TestBase;
import ClanHub.data.RegistrData;
import ClanHub.pages.HomePage;
import ClanHub.pages.RegistrationPage;
import ClanHub.utils.DataProvidersGUI;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Slf4j
public class RegistrationGUITests extends TestBase {

    @BeforeMethod
    public void precondition() {
        new HomePage(app.driver, app.wait).goToRegistrationPage();
    }

    @Test
    public void registrationPositiveTest() {
        new RegistrationPage(app.driver, app.wait)
                .validUserRegistrationUI()
                .verifyLogoutButton();
    }

    @Test
    public void registrationVerifyUsernameProfilePositiveTest() {
        new RegistrationPage(app.driver, app.wait)
                .validUserRegistrationUI()
                .verifyUserName(RegistrData.VALID_USERNAME);
    }

    @Test
    public void registrationVerifyAgeProfilePositiveTest() {
        new RegistrationPage(app.driver, app.wait)
                .validUserRegistrationUI()
                .verifyUserAge(String.valueOf(RegistrData.VALID_AGE));
    }

    @Test
    public void registrationVerifyAvatarProfilePositiveTest() {
        new RegistrationPage(app.driver, app.wait)
                .validUserRegistrationUI()
                .verifyUserAvatar(RegistrData.VALID_AVATARID);
    }

    @Test
    public void registrationEmptyUsernameNegativeTest() {
        new RegistrationPage(app.driver, app.wait)
                .userRegistrationUI(
                        "",
                        RegistrData.VALID_AGE,
                        RegistrData.VALID_AVATARID,
                        RegistrData.generateRandomEmail(),
                        RegistrData.VALID_PASSWORD)
                .verifyValidationMessage("Username is required");
    }

    @Test
    public void registrationEmptyAgeNegativeTest() {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalDataForRegistration(
                        RegistrData.VALID_USERNAME,
                        "",
                        RegistrData.VALID_AVATARID,
                        RegistrData.generateRandomEmail(),
                        RegistrData.VALID_PASSWORD
                )
                .clickOnCreateAccountButton()
                .verifyValidationMessage("Age is required");
    }

    @Test
    public void registrationEmptyAvatarNegativeTest() {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalDataWithoutAvatar(
                        RegistrData.VALID_USERNAME,
                        String.valueOf(RegistrData.VALID_AGE),
                        RegistrData.generateRandomEmail(),
                        RegistrData.VALID_PASSWORD
                )
                .clickOnCreateAccountButton()
                .verifyValidationMessage("Please choose an avatar");
    }

    @Test
    public void registrationEmptyEmailNegativeTest() {
        new RegistrationPage(app.driver, app.wait)
                .userRegistrationUI(
                        RegistrData.VALID_USERNAME,
                        RegistrData.VALID_AGE,
                        RegistrData.VALID_AVATARID,
                        "",
                        RegistrData.VALID_PASSWORD)
                .verifyValidationMessage("Email is required");
    }

    @Test
    public void registrationEmptyPasswordNegativeTest() {
        new RegistrationPage(app.driver, app.wait)
                .userRegistrationUI(
                        RegistrData.VALID_USERNAME,
                        RegistrData.VALID_AGE,
                        RegistrData.VALID_AVATARID,
                        RegistrData.generateRandomEmail(),
                        "")
                .verifyValidationMessage("Password is required");
    }

    @Test(dataProvider = "registrationInvalidUsernameFromCsv", dataProviderClass = DataProvidersGUI.class)
    public void registrationInvalidUsernameNegativeTest(String username, String validationMessage) {
        new RegistrationPage(app.driver, app.wait)
                .userRegistrationUI(
                        username,
                        RegistrData.VALID_AGE,
                        RegistrData.VALID_AVATARID,
                        RegistrData.generateRandomEmail(),
                        RegistrData.VALID_PASSWORD)
                .verifyErrorMessage(validationMessage);
    }

    @Test(dataProvider = "registrationInvalidAgeDataProvider", dataProviderClass = DataProvidersGUI.class)
    public void registrationInvalidAgeNegativeTest(String age) {
        new RegistrationPage(app.driver, app.wait)
                .enterPersonalDataForRegistration(
                        RegistrData.VALID_USERNAME,
                        age,
                        RegistrData.VALID_AVATARID,
                        RegistrData.generateRandomEmail(),
                        RegistrData.VALID_PASSWORD
                )
                .clickOnCreateAccountButton()
                .verifyErrorMessage("Age must be between 5 and 100");
    }

    @Test(dataProvider = "registrationInvalidEmailDataProvider", dataProviderClass = DataProvidersGUI.class)
    public void registrationInvalidEmailNegativeTest(String email) {
        new RegistrationPage(app.driver, app.wait)
                .userRegistrationUI(
                        RegistrData.VALID_USERNAME,
                        RegistrData.VALID_AGE,
                        RegistrData.VALID_AVATARID, email,
                        RegistrData.VALID_PASSWORD)
                .verifyErrorMessage("Please enter a valid email");
    }

    @Test(dataProvider = "registrationInvalidPasswordDataProvider", dataProviderClass = DataProvidersGUI.class)
    public void registrationInvalidPasswordNegativeTest(String password, String validationMessage) {
        new RegistrationPage(app.driver, app.wait)
                .userRegistrationUI(
                        RegistrData.VALID_USERNAME,
                        RegistrData.VALID_AGE,
                        RegistrData.VALID_AVATARID,
                        RegistrData.generateRandomEmail(),
                        password
                )
                .verifyErrorMessage(validationMessage);
    }

    @Test
    public void registrationExistingUserNegativeTest() {

        String email = RegistrData.generateRandomEmail();

        new RegistrationPage(app.driver, app.wait)
                .userRegistrationUI(
                        RegistrData.VALID_USERNAME,
                        RegistrData.VALID_AGE,
                        RegistrData.VALID_AVATARID,
                        email,
                        RegistrData.VALID_PASSWORD)
                .clickLogoutButton();

        new HomePage(app.driver, app.wait).goToRegistrationPage();

        new RegistrationPage(app.driver, app.wait)
                .userRegistrationUI(
                        RegistrData.VALID_USERNAME,
                        RegistrData.VALID_AGE,
                        RegistrData.VALID_AVATARID,
                        email,
                        RegistrData.VALID_PASSWORD)
                .verifyErrorMessage("User with this email already exists");
    }


}
