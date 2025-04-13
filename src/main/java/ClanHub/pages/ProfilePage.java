package ClanHub.pages;

import ClanHub.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ProfilePage extends BasePage {
    public ProfilePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @FindBy(className = "_logoutButton_68u4i_52")
    WebElement logoutButton;

    public void verifyLogoutButton() {
        assert logoutButton.getText().trim().equalsIgnoreCase("Log out");
    }

    @FindBy(css = "div._inputDisabled_68u4i_26:nth-child(1)")
    WebElement usernameProfile;

    @FindBy(css = "div._inputDisabled_68u4i_26:nth-child(2)")
    WebElement userAgeProfile;

    @FindBy(css = "img[alt='Avatar']")
    WebElement userAvatarProfile;

    public void verifyUserName(String userName) {
        String actualUserName = usernameProfile.getText().trim();
        Assert.assertEquals(actualUserName, userName);
    }

    public void verifyUserAge(String age) {
        String actualAge = userAgeProfile.getText().trim();
        Assert.assertEquals(actualAge, age);
    }

    public void verifyUserAvatar(String avatar) {
        wait.until(ExpectedConditions.visibilityOf(userAvatarProfile));
        String actualAvatar = userAvatarProfile.getAttribute("src");
        Assert.assertTrue(actualAvatar.contains(avatar));
    }

    public void verifyValidationMessage(String message) {
        Assert.assertTrue(isElementPresent(driver.findElement(By.xpath("//span[contains(text(),'" + message + "')]"))));
    }

    @FindBy(css = "[class*='rror_g4gwr']")
    WebElement errorMessage;

    public void verifyErrorMessage(String validationMessage) {
        Assert.assertTrue(errorMessage.getText().contains(validationMessage));
    }

    @FindBy(css = "[class*='yesButton']")
    WebElement yesButton;

    public void clickLogoutButton() {
        click(logoutButton);
        click(yesButton);
    }
}
