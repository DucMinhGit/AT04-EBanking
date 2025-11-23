package pages;

import Utils.Driver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class CreateAccountPage {

    // Locator
    private final By dropdownBy = By.id("j_idt23:j_idt27");
    private final By createBtnBy = By.name("j_idt23:j_idt31");
    private final By modalCloseBy = By.cssSelector(".ui-dialog-titlebar-close");

    public void openAccountTypeDropdown() {
        WebElement dd = Driver.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(dropdownBy));
        dd.click();
        log.info("Opened CreateAccount dropdown.");
    }

    public void selectAccountTypeByLabel(String label) {
        WebElement opt = Driver.getWebDriverWait().until(
                ExpectedConditions.elementToBeClickable(By.xpath(String.format("//div[@id='j_idt23:j_idt27_panel']//li[@data-label=\"%s\"]", label))));
        opt.click();
        log.info("Selected account type: " + label);
    }

    public void clickCreateAccount() {
        WebElement btn = Driver.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(createBtnBy));
        btn.click();
        log.info("Clicked 'Tạo tài khoản'.");
    }

    public void closeModal() {
        WebElement close = Driver.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(modalCloseBy));
        close.click();
        log.info("Closed modal.");
    }
}
