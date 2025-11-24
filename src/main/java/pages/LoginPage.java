package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import models.Account;
import org.openqa.selenium.By;

@Log4j2
public class LoginPage extends BasePage {
    @Step("Go to login page")
    public void login(Account account) {
        log.info("Starting login process with username: {}", account.getUsername());
        enterUsername(account.getUsername());
        enterPassword(account.getPassword());
        clickLoginButton();
    }

    @Step("1. Enter Username: {username}")
    public void enterUsername(String username) {
        log.info("Entering username: {}", username);
        waitForVisible(usernameInput);
        type(usernameInput, username);
    }

    @Step("2. Enter Password: ********")
    public void enterPassword(String password) {
        log.info("Entering password: {}", maskPassword(password));
        type(passwordInput, password);
    }

    @Step("3. Click Login Button")
    public void clickLoginButton() {
        log.info("Clicking Login button");
        click(loginButton);
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

    private final By usernameInput = By.name("j_idt10:j_idt12");
    private final By passwordInput = By.name("j_idt10:j_idt14");
    private final By loginButton = By.name("j_idt10:j_idt16");
    private final By errorMessageText = By.cssSelector("div.ui-growl-message > p");
}