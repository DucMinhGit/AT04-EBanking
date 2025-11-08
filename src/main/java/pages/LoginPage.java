package pages;

import Utils.Driver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Log4j2
public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By usernameInput = By.xpath("//input[@type='text' and @class='contact_filed']");
    private final By passwordInput = By.xpath("//input[@type='password' and @class='contact_filed']");
    private final By loginButton = By.xpath("//form[contains(@action, 'index.xhtml')]//input[@type='submit']");

    private final By errorMessageText = By.cssSelector("div.ui-growl-message > p");

    public LoginPage() {
        this.driver = Driver.getDriver();
        this.wait = Driver.getWebDriverWait();
    }

    public void enterUsername(String username) {
        log.info("Enter username: {}", username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).sendKeys(username);
    }

    public void enterPassword(String password) {
        log.info("Enter password: ***");
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);
    }

    public void clickLoginButton() {
        log.info("Click Login Btn");
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public HomePage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return new HomePage();
    }

    public String getErrorMessage() {
        String message = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageText)).getText();
        log.warn("Lấy được thông báo lỗi: {}", message);
        return message;
    }
}