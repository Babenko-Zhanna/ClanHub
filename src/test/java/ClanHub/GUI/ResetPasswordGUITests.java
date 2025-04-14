package ClanHub.GUI;

import ClanHub.core.TestBase;
import ClanHub.pages.HomePage;
import ClanHub.pages.LoginPage;
import ClanHub.pages.ResetPasswordPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ResetPasswordGUITests extends TestBase {
    @BeforeMethod
    public void precondition() {
        new HomePage(app.driver, app.wait).goToLoginPage()
                .clickForgotPassword();
    }

    @Test
    public void resetPasswordPositiveTest() {
        new ResetPasswordPage(app.driver, app.wait)
                .sentResetCode("clanHubEmail@guerrillamailblock.com")
                .enterResetCode()
                .createNewPassword("Password1#")
                .verifySuccessfulMessage();
    }

    @Test
    public void resetPasswordLoginPositiveTest() {
        String newPassword = System.currentTimeMillis() + "Pa#";
        new ResetPasswordPage(app.driver, app.wait)
                .sentResetCode("clanHubEmail@guerrillamailblock.com")
                .enterResetCode()
                .createNewPassword(newPassword);

        new LoginPage(app.driver, app.wait)
                .loginWithNewPassword(newPassword)
                .verifyLogoutButton();
    }

    @Test
    public void resetPasswordEmptyEmailNegativeTest() {
        new ResetPasswordPage(app.driver, app.wait)
                .sentResetCode("")
                .verifyValidationMessage("Please enter an email address");
    }

    @Test
    public void resetPasswordUnregisteredEmailNegativeTest() {
        new ResetPasswordPage(app.driver, app.wait)
                .sentResetCode("non-existent@gmail.com")
                .verifyValidationMessage("This email is not registered in our system.");
    }

    @Test
    public void resetPasswordInvalidEmailNegativeTest() {
        new ResetPasswordPage(app.driver, app.wait)
                .sentResetCode("invalidgmail.com")
                .verifyValidationMessage("Please enter a valid email address");
    }

    @Test
    public void resetPasswordIncorrectCodeNegativeTest() {
        new ResetPasswordPage(app.driver, app.wait)
                .sentResetCode("user@test.qa")
                .enterResetCode("123456")
                .verifyValidationMessage("The verification code you entered is incorrect.");
    }

    @Test
    public void resetPasswordInvalidCodeNegativeTest() {
        new ResetPasswordPage(app.driver, app.wait)
                .sentResetCode("user@test.qa")
                .enterResetCode("-1fb3")
                .verifyValidationMessage("Please enter a valid 6-digit code");
    }

    @Test
    public void resetPasswordExpiredCodeNegativeTest() {
        new ResetPasswordPage(app.driver, app.wait)
                .sentResetCode("expired_code@pokemail.net")
                .enterResetCode("373343")
                .verifyValidationMessage("The verification code has expired. Please request a new code.");
    }

    @Test
    public void resetPasswordInvalidNewPasswordNegativeTest() {
        new ResetPasswordPage(app.driver, app.wait)
                .sentResetCode("clanHubEmail@guerrillamailblock.com")
                .enterResetCode()
                .createNewPassword("password1#")
                .verifyValidationMessage("Password must include uppercase, lowercase, number and special character");
    }

    @Test
    public void resetPasswordEmptyNewPasswordNegativeTest() {
        new ResetPasswordPage(app.driver, app.wait)
                .sentResetCode("clanHubEmail@guerrillamailblock.com")
                .enterResetCode()
                .createNewPassword("")
                .verifyValidationMessage("Password is required");
    }

    @Test
    public void resetPasswordMismatchPasswordsNegativeTest() {
        new ResetPasswordPage(app.driver, app.wait)
                .sentResetCode("clanHubEmail@guerrillamailblock.com")
                .enterResetCode()
                .enterMismatchedValidPasswords()
                .verifyValidationMessage("Passwords do not match");
    }


}
