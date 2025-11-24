package pages.transfer.external;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import models.ExternalTransfer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

@Log4j2
public class ExternalTransferPage extends BasePage {
    public void submitForm(ExternalTransfer data) {
        selectAccount(data.getFromAccountValue());
        fillFormDetails(data);
        clickSubmit();
    }

    @Step("Fill data and submit form transfer")
    public void fillFormDetails(ExternalTransfer data)
    {
        enterReceiverAccount(data.getReceiverAccount());

        enterReceiverName(data.getReceiverName());

        selectBankAndBranch(data.getBankValue(), data.getBranchValue());

        enterContent(data.getContent());

        enterAmount(data.getAmount());
    }

    @Step("Select 'From Account': {accountValue}")
    public void selectAccount(String accountValue) {
        log.info("account", accountValue);
        if (accountValue != null && !accountValue.isEmpty()) {
            selectDropdownByText(accountDropdown, accountValue);

            WebElement balanceLabelElement = driver.findElement(balanceLabel);
            wait.until(ExpectedConditions.stalenessOf(balanceLabelElement));

            log.info("Account selected: {}. New balance updated.", accountValue);
        }

        log.warn("Skip account selection (value is null or empty).");
    }

    @Step("Select 'Receiver Account': {receiverAccount}")
    public void enterReceiverAccount(String receiverAccount) {
        type(receiverAccountInput, receiverAccount);
    }

    @Step("Enter 'Receiver Name': {receiverName}")
    public void enterReceiverName(String receiverName) {
        type(receiverNameInput, receiverName);
    }

    @Step("Select 'Bank': {bank}")
    public void selectBankAndBranch(String bank, String branch) {
        if (bank != null && !bank.isEmpty()) {
            WebElement oldOption = driver.findElement(getBranchOptionLocator(""));
            selectDropdownByText(bankDropdown, bank);
            wait.until(ExpectedConditions.stalenessOf(oldOption));

            selectBranch(branch);
        }
        log.warn("Skip selection branch and bank.");
    }

    @Step("Select 'Branch': {branch}")
    public void selectBranch(String branch) {
        if (branch != null && !branch.isEmpty()) {
            By targetBranchOption = getBranchOptionLocator(branch);
            wait.until(ExpectedConditions.presenceOfElementLocated(targetBranchOption));
            selectDropdownByText(branchDropdown, branch);
        }
        log.warn("Skip branch.");
    }

    @Step("Enter 'Content': {content}")
    public void enterContent(String content) {
        type(contentInput, content);
    }

    @Step("Enter 'Amount': {amount}")
    public void enterAmount(double amount) {
        type(transferAmountInput, String.valueOf(amount));
    }

    @Step("Click Submit button")
    public void clickSubmit() {
        click(submitButton);
    }

    private By getBranchOptionLocator(String branchValue) {
        return By.id("j_idt23:city");
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
    private final By balanceLabel = By.cssSelector("label[id$=':amount']");
    private final By receiverAccountInput = By.cssSelector("input[id$=':soucre']");
    private final By receiverNameInput = By.cssSelector("input[id$=':nameSoucre']");
    private final By bankDropdown = By.id("j_idt23:country");
    private final By branchDropdown = By.id("j_idt23:city");
    private final By transferAmountInput = By.cssSelector("input[id$=':tranf']");
    private final By contentInput = By.xpath("//input[contains(@id, ':tranf')]/ancestor::tr/preceding-sibling::tr[1]//input");
    private final By submitButton = By.xpath("//input[contains(@id, ':tranf')]/ancestor::tr/following-sibling::tr[1]//input[@type='submit']");
    private final By errorTitle = By.cssSelector("div[id$=':msgs_container'] .ui-growl-message p");
}