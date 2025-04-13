package ClanHub.pages;

import ClanHub.core.BasePage;
import ClanHub.data.RegistrData;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationPage extends BasePage {

    Logger logger = LoggerFactory.getLogger(RegistrationPage.class);

    public RegistrationPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @FindBy(id = "username-input")
    WebElement username;

    @FindBy(id = "age-input")
    WebElement age;

    @FindBy(id="avatar-selector")
    WebElement avatar;

    @FindBy(id="email-input")
    WebElement email;

    @FindBy(id = "password-input")
    WebElement password;

    public RegistrationPage enterPersonalDataForRegistration(String userName, String userAge, String userAvatar, String userEmail, String userPassword) {
        type(username, userName);
        type(age, userAge);
        click(avatar);
        selectAvatar(userAvatar);
        type(email, userEmail);
        type(password, userPassword);
        //logger.info("\nTyping user_name: " + userName + "\nTyping age: " + userAge + "\nChosen avatar: " + userAvatar + "\nTyping email: " + userEmail + "\nTyping password: " + userPassword);
        return this;
    }

    public RegistrationPage enterPersonalDataWithoutAvatar(String userName, String userAge, String userEmail, String userPassword) {
        type(username, userName);
        type(age, userAge);
        type(email, userEmail);
        type(password, userPassword);
        return this;
    }

    public void selectAvatar(String avatarId) {
        try {
            WebElement avatarLocator = driver.findElement(By.cssSelector("img[alt='" + avatarId + "']"));
            click(avatarLocator);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("⛔ Avatar element is not found: [" + avatarId + "], " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("❌ Error selecting avatar: [" + avatarId + "]. " + e);
        }
    }


    @FindBy(id = "register-submit-btn")
    WebElement registerButton;

    public ProfilePage clickOnCreateAccountButton() {
        click(registerButton);
        return new ProfilePage(driver, wait);
    }

    public ProfilePage validUserRegistrationUI() {
        enterPersonalDataForRegistration(
                RegistrData.VALID_USERNAME,
                String.valueOf(RegistrData.VALID_AGE),
                RegistrData.VALID_AVATARID,
                RegistrData.generateRandomEmail(),
                RegistrData.VALID_PASSWORD
        );
        clickOnCreateAccountButton();
        return new ProfilePage(driver, wait);
    }

    public ProfilePage userRegistrationUI(String username, int userAge, String userAvatar, String userEmail, String userPassword) {
        enterPersonalDataForRegistration(
                username,
                String.valueOf(userAge),
                userAvatar,
                userEmail,
                userPassword
        );
        clickOnCreateAccountButton();
        return new ProfilePage(driver, wait);
    }
}
