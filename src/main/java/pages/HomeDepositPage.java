package pages;

import Utils.Driver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.math.BigDecimal;

@Log4j2
public class HomeDepositPage {

    // Locator
    private final By depositTabBy = By.xpath("//a[@href='/EBankingWebsite/faces/admin/deposit.xhtml']");
    private final By accountInputBy = By.name("j_idt23:j_idt27");
    private final By amountInputBy = By.name("j_idt23:j_idt29");
    private final By descInputBy = By.name("j_idt23:j_idt31");
    private final By confirmBtnBy = By.name("j_idt23:j_idt33");

    public void clickDepositTab() {
        WebElement tab = Driver.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(depositTabBy));
        tab.click();
        log.info("Clicked 'Nộp Tiền' tab.");
    }

    public void depositToAccount(String account, BigDecimal amount, String description) {
        WebElement acc = Driver.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(accountInputBy));
        acc.clear();
        acc.sendKeys(account);

        WebElement amt = Driver.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(amountInputBy));
        amt.clear();
        // dùng toPlainString để tránh 1E6 style
        amt.sendKeys(amount.toPlainString());

        WebElement desc = Driver.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(descInputBy));
        desc.clear();
        desc.sendKeys(description);

        WebElement confirm = Driver.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(confirmBtnBy));
        confirm.click();
        log.info("Deposit submitted for account " + account + " amount=" + amount.toPlainString());
    }
}
