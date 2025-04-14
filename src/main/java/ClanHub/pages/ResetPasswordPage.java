package ClanHub.pages;

import ClanHub.core.BasePage;
import ClanHub.utils.GuerrillaMailService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ResetPasswordPage extends BasePage {
    public ResetPasswordPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @FindBy(id = "email-input")
    WebElement email;

    @FindBy(id = "submit-btn")
    WebElement submitButton;

    public ResetPasswordPage sentResetCode(String userEmail) {
        type(email, userEmail);
        click(submitButton);
        return this;
    }

    @FindBy(className = "_codeInputBox_1j5nq_184")
    WebElement codeInput;

    public ResetPasswordPage enterResetCode() {
        String resetCode = GuerrillaMailService.getResetCode();
        wait.until(ExpectedConditions.visibilityOf(codeInput));
        type(codeInput, resetCode);
        click(submitButton);
        return this;
    }

    public ResetPasswordPage enterResetCode(String resetCode) {
        type(codeInput, resetCode);
        click(submitButton);
        return this;
    }

    @FindBy(id = "new-password-input")
    WebElement newPasswordInput;

    @FindBy(id = "repeat-password-input")
    WebElement repeatPasswordInput;

    @FindBy(className = "_successMessage_1j5nq_83")
    WebElement successMessage;

    public ResetPasswordPage createNewPassword(String password) {
        type(newPasswordInput, password);
        type(repeatPasswordInput, password);
        click(submitButton);
        return this;
    }

    public void verifySuccessfulMessage() {
        Assert.assertEquals(successMessage.getText(), "Password successfully reset!");
    }

    @FindBy(className = "_error_1j5nq_73")
    WebElement errorMessage;

    public void verifyValidationMessage(String message) {
        Assert.assertEquals(errorMessage.getText(), message);
    }

    public ResetPasswordPage enterMismatchedValidPasswords() {
        type(newPasswordInput, "Password1#");
        type(repeatPasswordInput, "Password2#");
        click(submitButton);
        return this;
    }
}
