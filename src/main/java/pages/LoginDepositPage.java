package pages;

import Utils.Driver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class LoginDepositPage {

    // Locator
    private final By idInputBy = By.id("j_idt9:id1");
    private final By pwdInputBy = By.id("j_idt9:pwd1");
    private final By loginBtnBy = By.id("j_idt9:j_idt15");

    public void login(String id, String password) {
        WebElement idEl = Driver.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(idInputBy));
        idEl.clear();
        idEl.sendKeys(id);

        WebElement pwdEl = Driver.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(pwdInputBy));
        pwdEl.clear();
        pwdEl.sendKeys(password);

        WebElement btn = Driver.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(loginBtnBy));
        btn.click();
        log.info("Admin logged in.");
    }
}
