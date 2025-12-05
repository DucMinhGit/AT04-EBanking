package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Driver;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class BankAccountPage extends BasePage {
    private int getColumnIndex(String columnName, By headerTable) {
        waitForVisible(headerTable);
        List<WebElement> columns = Driver.getDriver().findElements(headerTable);

        List<String> columnTexts = columns.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());

        int colIdx = columnTexts.indexOf(columnName.trim()) + 1;

        if (colIdx == 0) {
            log.error("Not found column: '{}'. Column show is: {}", columnName, columnTexts);
            throw new RuntimeException("Column not found: " + columnName);
        }
        return colIdx;
    }

    public String getNewestAccountNumber(String row) {
        int typeColIdx = getColumnIndex(COL_HEADER_ACCOUNT_NO, headerInfoTable);

        String cellXpath = "//div[@id='j_idt27']//table/tbody/tr[" + row + "]/td[" + typeColIdx + "]";
        WebElement cell = Driver.getDriver().findElement(By.xpath(cellXpath));
        return cell.getText().trim();
    }

    public String getNewestAccountNumber(int row) {
        int typeColIdx = getColumnIndex(COL_HEADER_ACCOUNT_NO, headerInfoTable);

        String cellXpath = "//div[@id='j_idt27']//table/tbody/tr[" + row + "]/td[" + typeColIdx + "]";
        WebElement cell = Driver.getDriver().findElement(By.xpath(cellXpath));
        return cell.getText().trim();
    }

    @Step("Close dialog message")
    public void closeDialogMessage() {
        waitForVisible(closeButton);
        click(closeButton);
    }

    @Step("Click on account number {accountNumber} to view details")
    public void viewAccountDetail(String accountNumber) {
        log.info("Opening detail for account: {}", accountNumber);
        By accountLink = By.xpath("//div[@id='j_idt27']//table//tbody//tr//td//a[normalize-space(text())='" + accountNumber + "']");

        waitForVisible(accountLink);
        click(accountLink);
    }

    @Step("Verify Latest Transaction Amount")
    public double getLatestTransactionAmount(int row) {
        int typeColIdx = getColumnIndex(COL_HEADER_AMOUNT, headerTransactionTable);

        String cellXpath = "//tbody[@id='j_idt37_data']/tr["+ row +"]/td[" + typeColIdx + "]";
        WebElement cell = Driver.getDriver().findElement(By.xpath(cellXpath));
        String textAmount = cell.getText().trim().replaceAll("[^0-9.-]", "");

        return Math.abs(Double.parseDouble(textAmount));
    }

    public String getSuccessMessage() {
        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBe(successMessageText, "")
        ));
        String message = getVisibleText(successMessageText);
        log.info("Result: {}", message);
        return message;
    }

    private static final String COL_HEADER_TYPE = "Loại tài khoản";
    private static final String COL_HEADER_ACCOUNT_NO = "Tài Khoản";
    private static final String COL_HEADER_AMOUNT = "Số tiền";

    private final By closeButton = By.className("ui-icon-closethick");
    private final By headerInfoTable = By.xpath("//div[@id='j_idt27']//table/thead//th");
    private final By headerTransactionTable = By.xpath("//div[@id='j_idt37']//table/thead//th");
    private final By successMessageText = By.cssSelector("#primefacesmessagedlg .ui-dialog-content");
}