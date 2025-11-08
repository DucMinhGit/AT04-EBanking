package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;

@Log4j2
public class LoginPage extends BasePage {
    public void waitForPageLoad() {
        log.info("Waiting for LoginPage to load...");
        waitForVisible(usernameInput);
        log.info("LoginPage loaded successfully.");
    }

    public void enterUsername(String username) {
        log.info("Entering username: {}", username);
        type(usernameInput, username);
    }

    public void enterPassword(String password) {
        log.info("Entering password: {}", maskPassword(password));
        type(passwordInput, password);
    }

    public void clickLoginButton() {
        log.info("Clicking Login button");
        click(loginButton);
    }

    public HomePage login(String username, String password) {
        log.info("Starting login process with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        log.info("Login button clicked, waiting for HomePage...");
        return new HomePage();
    }

    public String getErrorMessage() {
        log.info("Checking for login error message");
        String message = getVisibleText(errorMessageText);
        log.info("Error message displayed: {}", message);
        return message;
    }

    private String maskPassword(String password) {
        if (password == null) return "null";
        return "*".repeat(Math.min(password.length(), 8));
    }

    private final By usernameInput = By.xpath("//input[@type='text' and @class='contact_filed']");
    private final By passwordInput = By.xpath("//input[@type='password' and @class='contact_filed']");
    private final By loginButton = By.xpath("//form[contains(@action, 'index.xhtml')]//input[@type='submit']");
    private final By errorMessageText = By.cssSelector("div.ui-growl-message > p");
}