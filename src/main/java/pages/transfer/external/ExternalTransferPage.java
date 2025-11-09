package pages.transfer.external;

import data.ExternalTransferData;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pages.BasePage;

import java.util.NoSuchElementException;
@Log4j2
public class ExternalTransferPage extends BasePage {
    private final By accountDropdown = By.cssSelector("select[onchange*=':amount']");
    private final By balanceLabel = By.cssSelector("label[id$=':amount']");
    private final By receiverAccountInput = By.cssSelector("input[id$=':soucre']");
    private final By receiverNameInput = By.cssSelector("input[id$=':nameSoucre']");
    private final By bankDropdown = By.cssSelector("select[id$=':country_input']");
    private final By branchDropdown = By.cssSelector("select[id$=':city_input']");
    private final By transferAmountInput = By.cssSelector("input[id$=':tranf']");
    private final By contentInput = By.xpath("//input[contains(@id, ':tranf')]/ancestor::tr/preceding-sibling::tr[1]//input");
    private final By submitButton = By.xpath("//input[contains(@id, ':tranf')]/ancestor::tr/following-sibling::tr[1]//input[@type='submit']");
    private final By generalErrorMessage = By.cssSelector(".ui-growl-item-container .ui-growl-title");
    private final By errorTitle = By.cssSelector(".ui-growl-item-container .ui-growl-title"); // Giữ lại nếu cần

    public String getGeneralErrorMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(generalErrorMessage));
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(generalErrorMessage, "")));
        return driver.findElement(generalErrorMessage).getText();
    }

    public void fillAndSubmitTransferForm(ExternalTransferData data) {
        log.info("Bắt đầu điền form chuyển tiền...");
        if (!data.getFromAccountValue().isEmpty()) {
            String initialBalance = getAvailableBalance();
            selectPrimeFacesSelectOneMenu(accountDropdown, data.getFromAccountValue());

            wait.until(ExpectedConditions.not(
                    ExpectedConditions.textToBe(balanceLabel, initialBalance)
            ));
            log.info("Số dư đã được cập nhật, tiếp tục điền form...");
        } else {
            log.info("Bỏ qua chọn Tài khoản nguồn vì dữ liệu rỗng.");
        }

        type(receiverAccountInput, data.getReceiverAccount());
        type(receiverNameInput, data.getReceiverName());

        if (!data.getBankValue().isEmpty()) {
            WebElement oldOption = driver.findElement(getBranchOptionLocator(""));

            selectPrimeFacesSelectOneMenu(bankDropdown, data.getBankValue());

            log.info("Đã chọn Ngân hàng, chờ AJAX cập nhật Chi nhánh...");
            wait.until(ExpectedConditions.stalenessOf(oldOption));
            log.info("AJAX đã cập nhật xong Chi nhánh.");

            if (!data.getBranchValue().isEmpty()) {
                By targetBranchOption = getBranchOptionLocator(data.getBranchValue());
                wait.until(ExpectedConditions.presenceOfElementLocated(targetBranchOption));
                selectPrimeFacesSelectOneMenu(branchDropdown, data.getBranchValue());
            } else {
                log.info("Bỏ qua chọn Chi nhánh vì dữ liệu rỗng.");
            }
        } else {
            log.info("Bỏ qua chọn Ngân hàng và Chi nhánh vì dữ liệu rỗng.");
        }

        type(contentInput, data.getContent());
        type(transferAmountInput, data.getAmount());

        click(submitButton);

        log.info("Đã submit form.");
    }

    private By getBranchOptionLocator(String branchValue) {
        String parentXPath = "//select[contains(@id, ':city_input')]";
        return By.xpath(parentXPath + "/option[@value='" + branchValue + "']");
    }


    public String getAvailableBalance() {
        return getVisibleText(balanceLabel);
    }


    private void selectPrimeFacesSelectOneMenu(By hiddenSelectLocator, String value) {
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
}