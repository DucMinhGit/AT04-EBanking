package pages.Internal_Transfer;

import Utils.Driver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class ConfirmTransferPage {

    // Locator
    private final By confirmBtnBy = By.name("j_idt23:j_idt44");

    // Click Confirm button
    public void clickConfirm() {
        WebElement btn = Driver.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(confirmBtnBy));
        btn.click();
        log.info("Clicked Confirm successfully.");
    }
}
