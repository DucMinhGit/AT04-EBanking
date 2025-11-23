package pages.Internal_Transfer;

import Utils.Driver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.util.List;

@Log4j2
public class InternalTransferPage {

    // Locators
    private final By dropdownlist = By.id("j_idt23:j_idt28");
    private final By toAccountBy = By.name("j_idt23:j_idt35");
    private final By amountBy = By.name("j_idt23:j_idt40");
    private final By descBy = By.name("j_idt23:j_idt42");
    private final By confirmBy = By.name("j_idt23:j_idt44");

    private final WebDriverWait wait = Driver.getWebDriverWait();

    public void selectSourceAccount(String account) {
        WebDriver driver = Driver.getDriver();
        WebDriverWait w = Driver.getWebDriverWait();

        // chờ trang load xong
        w.until(ExpectedConditions.elementToBeClickable(dropdownlist));

        // mở dropdown
        w.until(ExpectedConditions.elementToBeClickable(dropdownlist)).click();

        // chờ panel dropdown xuất hiện
        WebElement panel = w.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("j_idt23:j_idt28_panel")
        ));

        // lấy toàn bộ li trong dropdown
        List<WebElement> options = panel.findElements(By.tagName("li"));

        for (WebElement li : options) {
            if (account.equals(li.getText().trim())) {
                Actions actions = new Actions(driver);
                actions.moveToElement(li).pause(200).click().perform();
                return;
            }
        }

        throw new RuntimeException("Account not found in dropdown: " + account);
    }


    public void enterToAccount(String toAccount) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(toAccountBy));
        input.clear();
        input.sendKeys(toAccount);
    }

    public void enterAmount(BigDecimal amount) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(amountBy));
        input.clear();
        input.sendKeys(amount.toPlainString());
    }

    public void enterDescription(String description) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(descBy));
        input.clear();
        input.sendKeys(description);
    }

    public void clickConfirmButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(confirmBy));
        btn.click();
        log.info("Clicked Confirm button");
    }

    //
    public void showSuccessModal() {

        WebDriver driver = Driver.getDriver();
        WebDriverWait wait = Driver.getWebDriverWait();

        By dialogBy = By.id("primefacesmessagedlg");

        // Chờ modal hiển thị
        WebElement dialog = wait.until(ExpectedConditions.visibilityOfElementLocated(dialogBy));

        // Lấy nội dung modal
        WebElement content = dialog.findElement(By.cssSelector(".ui-dialog-content"));
        String rawText = content.getText().trim();

        log.info("Success modal displayed with message: " + rawText);

        // Đóng modal
        By closeBtn = By.cssSelector("#primefacesmessagedlg .ui-dialog-titlebar-close");
        wait.until(ExpectedConditions.elementToBeClickable(closeBtn)).click();
    }

    // INT02
    public String getTransferAccountErrorMessage() {
        By messageBy = By.xpath("//*[contains(text(),'Mời chọn tài khoản')]");
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(messageBy));
        log.info("Read error message successfully.");
        return el.getText().trim();
    }

    // INT03
    public String getReceiveAccountErrorMessage() {
        By messageBy = By.xpath("//*[contains(text(),'Tài khoản không hợp lệ, quý khách vui lòng kiểm tra lại.')]");
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(messageBy));
        log.info("Read error message successfully.");
        return el.getText().trim();
    }

    // INT04
    public String getAmountErrorMessage() {
        By messageBy = By.xpath("//*[contains(text(),'Nhập nội dung')]");
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(messageBy));
        log.info("Read error message successfully.");
        return el.getText().trim();
    }

    // INT05
    public String getDescErrorMessage() {
        By messageBy = By.xpath("//*[contains(text(),'Nhập nội dung')]");
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(messageBy));
        log.info("Read error message successfully.");
        return el.getText().trim();
    }

    // INT06
    public String getInvalidReceiveAccountMessage() {
        By messageBy = By.xpath("//*[contains(text(),'Tài khoản không hợp lệ, quý khách vui lòng kiểm tra lại.')]");
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(messageBy));
        log.info("Read invalid account message successfully.");
        return el.getText().trim();
    }

    // INT07
    public BigDecimal getAvailableBalance() {
        WebDriver driver = Driver.getDriver();
        By balanceBy = By.id("j_idt23:amount");

        // wait until element visible and has non-empty text
        WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(balanceBy));
        wait.until(d -> !driver.findElement(balanceBy).getText().trim().isEmpty());

        String text = driver.findElement(balanceBy).getText().trim();
        // remove all non-digits (commas, spaces, VNĐ, etc.)
        String digits = text.replaceAll("[^\\d]", "");
        if (digits.isEmpty()) {
            throw new RuntimeException("Balance label found but contains no digits: [" + text + "]");
        }
        return new BigDecimal(digits);
    }

    public String getInvalidAmountMessage() {
        By messageBy = By.xpath("//*[contains(text(),'Số tiền vượt mức')]");
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(messageBy));
        log.info("Read invalid message successfully.");
        return el.getText().trim();
    }
}
