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
    public void submitTransferInfo(InternalTransfer data) {
        selectAccount(data.getFromAccountValue());
        fillFormDetails(data);
        clickSubmit();
    }

    @Step("Fill data and submit form transfer")
    public void fillFormDetails(InternalTransfer data) {
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
    private final By transferAmountInput = By.name("j_idt23:j_idt40");
    private final By contentInput = By.name("j_idt23:j_idt42");
    private final By submitButton = By.name("j_idt23:j_idt44");
    private final By errorTitle = By.className("ui-growl-title");
    private final By toAccountBy = By.name("j_idt23:j_idt35");
}
