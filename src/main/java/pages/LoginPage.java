package pages;

import Utils.Driver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class LoginPage {

    // Locator
    private final By usernameBy = By.name("j_idt10:j_idt12");
    private final By passwordBy = By.name("j_idt10:j_idt14");
    private final By loginBtnBy = By.name("j_idt10:j_idt16");

    // Returns HomePage after successful login
    public HomePage login (String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        log.info("Login account successfully.");
        return new HomePage();
    }

    // Enter Username
    public void enterUsername(String username) {
        WebElement el = Driver.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(usernameBy));
        el.clear();
        el.sendKeys(username);
    }

    // Enter Password
    public void enterPassword(String password) {
        WebElement el = Driver.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(passwordBy));
        el.clear();
        el.sendKeys(password);
    }

    // Click Login
    public void clickLogin() {
        WebElement btn = Driver.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(loginBtnBy));
        btn.click();
    }
}