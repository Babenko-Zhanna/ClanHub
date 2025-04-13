package ClanHub.pages;

import ClanHub.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @FindBy(id = "create-account-btn")
    WebElement createAccountBtn;

    public void goToRegistrationPage() {
        click(createAccountBtn);
    }

    @FindBy(id = "login-btn")
    WebElement loginBtn;

    public LoginPage goToLoginPage() {
        click(loginBtn);
        return new LoginPage(driver, wait);
    }
}
