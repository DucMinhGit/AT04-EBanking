package pages.transfer.external;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import models.ExternalTransferModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pages.BasePage;
import java.util.NoSuchElementException;

@Log4j2
public class ExternalTransferPage extends BasePage {

    public void submitForm(ExternalTransferModel data) {
        selectAccount(data.getFromAccountValue());
        fillFormDetails(data);
        clickSubmit();
    }

    @Step("Fill data and submit form transfer")
    public void fillFormDetails(ExternalTransferModel data)
    {
        enterReceiverAccount(data.getReceiverAccount());

        enterReceiverName(data.getReceiverName());

        selectBankAndBranch(data.getBankValue(), data.getBranchValue());

        enterContent(data.getContent());

        enterAmount(data.getAmount());
    }

    @Step("Select 'From Account': {accountValue}")
    public void selectAccount(String accountValue) {
        if (accountValue != null && !accountValue.isEmpty()) {
            String initialBalance = getAvailableBalance();
            selectMenu(accountDropdown, accountValue);

            wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(balanceLabel, initialBalance)));
            log.info("Account selected: {}. New balance updated.", accountValue);
        } else {
            log.warn("Skip account selection (value is null or empty).");
        }
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
            selectMenu(bankDropdown, bank);
            wait.until(ExpectedConditions.stalenessOf(oldOption));

            selectBranch(branch);
        } else {
            log.warn("Skip selection branch and bank.");
        }
    }

    @Step("Select 'Branch': {branch}")
    public void selectBranch(String branch) {
        if (branch != null && !branch.isEmpty()) {
            By targetBranchOption = getBranchOptionLocator(branch);
            wait.until(ExpectedConditions.presenceOfElementLocated(targetBranchOption));
            selectMenu(branchDropdown, branch);
        } else {
            log.warn("Skip branch.");
        }
    }

    @Step("Enter 'Content': {content}")
    public void enterContent(String content) {
        type(contentInput, content);
    }

    @Step("Enter 'Amount': {amount}")
    public void enterAmount(String amount) {
        type(transferAmountInput, amount);
    }

    @Step("Click Submit button")
    public void clickSubmit() {
        click(submitButton);
        log.info("Submitted, waiting confirm...");
    }

    private By getBranchOptionLocator(String branchValue) {
        String parentXPath = "//select[contains(@id, ':city_input')]";
        return By.xpath(parentXPath + "/option[@value='" + branchValue + "']");
    }

    @Step("Get Available Balance")
    public String getAvailableBalance() {
        return getVisibleText(balanceLabel);
    }

    public String getGeneralErrorMessage() {
        return driver.findElement(errorTitle).getText();
    }

    private void selectMenu(By hiddenSelectLocator, String value) {
        try {
            WebElement selectElement = wait.until(ExpectedConditions.presenceOfElementLocated(hiddenSelectLocator));
            Select tempSelect = new Select(selectElement);
            WebElement targetOption = tempSelect.getOptions().stream()
                    .filter(o -> o.getAttribute("value").equals(value))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Can not find option with value: " + value));

            String visibleText = targetOption.getAttribute("innerHTML");
            String hiddenSelectId = selectElement.getAttribute("id");
            String triggerId = hiddenSelectId.replace("_input", "");
            By triggerLocator = By.id(triggerId);

            click(triggerLocator);

            String panelId = triggerId + "_panel";
            By optionLocator = By.xpath("//div[@id='" + panelId + "']//li[@data-label='" + visibleText.trim() + "']");

            wait.until(ExpectedConditions.visibilityOfElementLocated(optionLocator));
            click(optionLocator);

        } catch (Exception e) {
            System.err.println("Error select '" + value + "' for locator: " + hiddenSelectLocator);
            e.printStackTrace();
            throw new RuntimeException("Can not dropdown value: " + value, e);
        }
    }

    private final By accountDropdown = By.cssSelector("select[onchange*=':amount']");
    private final By balanceLabel = By.cssSelector("label[id$=':amount']");
    private final By receiverAccountInput = By.cssSelector("input[id$=':soucre']");
    private final By receiverNameInput = By.cssSelector("input[id$=':nameSoucre']");
    private final By bankDropdown = By.cssSelector("select[id$=':country_input']");
    private final By branchDropdown = By.cssSelector("select[id$=':city_input']");
    private final By transferAmountInput = By.cssSelector("input[id$=':tranf']");
    private final By contentInput = By.xpath("//input[contains(@id, ':tranf')]/ancestor::tr/preceding-sibling::tr[1]//input");
    private final By submitButton = By.xpath("//input[contains(@id, ':tranf')]/ancestor::tr/following-sibling::tr[1]//input[@type='submit']");
    private final By errorTitle = By.cssSelector("div[id$=':msgs_container'] .ui-growl-message p");
}