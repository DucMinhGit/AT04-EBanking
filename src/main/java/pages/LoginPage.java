package pages;

import Utils.Driver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Log4j2
public class LoginPage {
    public LoginPage() {
        this.driver = Driver.getDriver();
        this.wait = Driver.getWebDriverWait();
    }

    public void enterUsername(String username) {
        log.info("Enter username: {}", username);
        driver.findElement(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        log.info("Enter password: ***");
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLoginButton() {
        log.info("Click Login Btn");
        driver.findElement(loginButton).click();
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

    private WebDriver driver;
    private WebDriverWait wait;

    private final By usernameInput = By.xpath("//input[contains(@name, 'j_idt12')]");
    private final By passwordInput = By.xpath("//input[contains(@name, 'j_idt14')]");
    private final By loginButton = By.xpath("//input[@type='submit']");
    private final By errorMessageText = By.cssSelector("div.ui-growl-message > p");
}