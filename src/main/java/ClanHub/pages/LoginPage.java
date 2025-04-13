package ClanHub.pages;

import ClanHub.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @FindBy(id = "email-input")
    WebElement email;

    @FindBy(id = "password-input")
    WebElement password;

    @FindBy(id = "login-btn")
    WebElement loginBtn;

    public DashBoardPage loginUser() {
        type(email, "clanHubEmail@grr.la");
        type(password, "Password1#");
        click(loginBtn);
        return new DashBoardPage(driver, wait);
    }
}
