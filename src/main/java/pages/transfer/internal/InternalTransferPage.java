package pages.transfer.internal;

import io.qameta.allure.Step;
import models.ExternalTransfer;
import models.InternalTransfer;
import pages.BasePage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class InternalTransferPage extends BasePage {
    public void submitForm(InternalTransfer data) {
        selectAccount(data.getFromAccountValue());
        fillFormDetails(data);
        clickSubmit();
    }

    @Step("Fill data and submit form transfer")
    public void fillFormDetails(InternalTransfer data)
    {
        enterReceiverAccount(data.getReceiverAccount());

        enterContent(data.getContent());

        enterAmount(data.getAmount());
    }

    @Step("Select 'From Account': {accountValue}")
    public void selectAccount(String accountValue) {
        if (accountValue != null && !accountValue.isEmpty()) {
            selectDropdownByText(accountDropdown, accountValue);

            WebElement balanceLabelElement = driver.findElement(balanceLabel);
            wait.until(ExpectedConditions.stalenessOf(balanceLabelElement));

            log.info("Account selected: {}. New balance updated.", accountValue);
        } else {
            log.warn("Skip account selection (value is null or empty).");
        }
    }

    @Step("Enter 'Receiver Account': {receiverAccount}")
    public void enterReceiverAccount(String receiverAccount) {
        log.info("Account to selected: {}. New balance updated.", receiverAccount);
        type(toAccountBy, receiverAccount);
    }

    @Step("Enter 'Content': {content}")
    public void enterContent(String content) {
        type(contentInput, content);
    }

    @Step("Enter 'Amount': {amount}")
    public void enterAmount(Double amount) {
        String inputToSend;

        if (amount == null) {
            inputToSend = "";
            log.warn("Sending empty string to amount field (testing null/empty case).");
        } else {
            inputToSend = String.valueOf(amount);
        }

        type(transferAmountInput, inputToSend);
    }

    @Step("Click Submit button")
    public void clickSubmit() {
        click(submitButton);
    }

    @Step("Get Available Balance")
    public double getAvailableBalance() {
        String rawAmount = getVisibleText(balanceLabel).trim();
        String parsedAmount = rawAmount.replaceAll("[^0-9]", "");
        return Double.parseDouble(parsedAmount);
    }

    public String getGeneralErrorMessage() {
        waitForVisible(errorTitle);
        return getVisibleText(errorTitle);
    }

    private final By accountDropdown = By.id("j_idt23:j_idt28");
    private final By balanceLabel = By.id("j_idt23:amount");
    private final By receiverAccountInput = By.cssSelector("input[id$=':soucre']");
    private final By receiverNameInput = By.cssSelector("input[id$=':nameSoucre']");
    private final By bankDropdown = By.id("j_idt23:country");
    private final By branchDropdown = By.id("j_idt23:city");
    private final By transferAmountInput = By.name("j_idt23:j_idt40");
    private final By contentInput = By.name("j_idt23:j_idt42");
    private final By submitButton = By.name("j_idt23:j_idt44");
    private final By errorTitle = By.className("ui-growl-title");

    // Locators
//    private final By dropdownlist = By.id("j_idt23:j_idt28");
    private final By toAccountBy = By.name("j_idt23:j_idt35");
//    private final By amountBy = By.name("j_idt23:j_idt40");
//    private final By descBy = By.name("j_idt23:j_idt42");
//    private final By confirmBy = By.name("j_idt23:j_idt44");
//
//    private final WebDriverWait wait = Driver.getWebDriverWait();
//
//    public void selectSourceAccount(String accountValue) {
//        if (accountValue != null && !accountValue.isEmpty()) {
//            selectDropdownByText(accountDropdown, accountValue);
//
//            WebElement balanceLabelElement = driver.findElement(balanceLabel);
//            wait.until(ExpectedConditions.stalenessOf(balanceLabelElement));
//
//            log.info("Account selected: {}. New balance updated.", accountValue);
//        }
//
//    }
//
//
//    public void enterToAccount(String toAccount) {
//        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(toAccountBy));
//        input.clear();
//        input.sendKeys(toAccount);
//    }
//
//    public void enterAmount(BigDecimal amount) {
//        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(amountBy));
//        input.clear();
//        input.sendKeys(amount.toPlainString());
//    }
//
//    public void enterDescription(String description) {
//        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(descBy));
//        input.clear();
//        input.sendKeys(description);
//    }
//
//    public void clickConfirmButton() {
//        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(confirmBy));
//        btn.click();
//        log.info("Clicked Confirm button");
//    }
//
//    //
//    public void showSuccessModal() {
//
//        WebDriver driver = Driver.getDriver();
//        WebDriverWait wait = Driver.getWebDriverWait();
//
//        By dialogBy = By.id("primefacesmessagedlg");
//
//        // Chờ modal hiển thị
//        WebElement dialog = wait.until(ExpectedConditions.visibilityOfElementLocated(dialogBy));
//
//        // Lấy nội dung modal
//        WebElement content = dialog.findElement(By.cssSelector(".ui-dialog-content"));
//        String rawText = content.getText().trim();
//
//        log.info("Success modal displayed with message: " + rawText);
//
//        // Đóng modal
//        By closeBtn = By.cssSelector("#primefacesmessagedlg .ui-dialog-titlebar-close");
//        wait.until(ExpectedConditions.elementToBeClickable(closeBtn)).click();
//    }
//
//    // INT02
//    public String getTransferAccountErrorMessage() {
//        By messageBy = By.xpath("//*[contains(text(),'Mời chọn tài khoản')]");
//        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(messageBy));
//        log.info("Read error message successfully.");
//        return el.getText().trim();
//    }
//
//    // INT03
//    public String getReceiveAccountErrorMessage() {
//        By messageBy = By.xpath("//*[contains(text(),'Tài khoản không hợp lệ, quý khách vui lòng kiểm tra lại.')]");
//        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(messageBy));
//        log.info("Read error message successfully.");
//        return el.getText().trim();
//    }
//
//    // INT04
//    public String getAmountErrorMessage() {
//        By messageBy = By.xpath("//*[contains(text(),'Nhập nội dung')]");
//        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(messageBy));
//        log.info("Read error message successfully.");
//        return el.getText().trim();
//    }
//
//    // INT05
//    public String getDescErrorMessage() {
//        By messageBy = By.xpath("//*[contains(text(),'Nhập nội dung')]");
//        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(messageBy));
//        log.info("Read error message successfully.");
//        return el.getText().trim();
//    }
//
//    // INT06
//    public String getInvalidReceiveAccountMessage() {
//        By messageBy = By.xpath("//*[contains(text(),'Tài khoản không hợp lệ, quý khách vui lòng kiểm tra lại.')]");
//        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(messageBy));
//        log.info("Read invalid account message successfully.");
//        return el.getText().trim();
//    }
//
//    // INT07
//    public BigDecimal getAvailableBalance() {
//        WebDriver driver = Driver.getDriver();
//        By balanceBy = By.id("j_idt23:amount");
//
//        // wait until element visible and has non-empty text
//        WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(balanceBy));
//        wait.until(d -> !driver.findElement(balanceBy).getText().trim().isEmpty());
//
//        String text = driver.findElement(balanceBy).getText().trim();
//        // remove all non-digits (commas, spaces, VNĐ, etc.)
//        String digits = text.replaceAll("[^\\d]", "");
//        if (digits.isEmpty()) {
//            throw new RuntimeException("Balance label found but contains no digits: [" + text + "]");
//        }
//        return new BigDecimal(digits);
//    }
//
//    public String getInvalidAmountMessage() {
//        By messageBy = By.xpath("//*[contains(text(),'Số tiền vượt mức')]");
//        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(messageBy));
//        log.info("Read invalid message successfully.");
//        return el.getText().trim();
//    }
}
