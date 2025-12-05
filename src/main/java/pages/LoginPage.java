package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import models.Account;
import org.openqa.selenium.By;

@Log4j2
public class LoginPage extends BasePage {
    @Step("Login page")
    public void login(Account account) {
        enterUsername(account.getUsername());
        enterPassword(account.getPassword());
        clickLoginButton();
    }

    @Step("1. Enter Username: {username}")
    public void enterUsername(String username) {
        waitForVisible(usernameInput);
        type(usernameInput, username);
    }

    @Step("2. Enter Password: {********}")
    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    @Step("3. Click Login Button")
    public void clickLoginButton() {
        click(loginButton);
    }

    private final By usernameInput = By.name("j_idt10:j_idt12");
    private final By passwordInput = By.name("j_idt10:j_idt14");
    private final By loginButton = By.name("j_idt10:j_idt16");
}