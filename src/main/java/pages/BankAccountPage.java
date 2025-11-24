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
    private int getColumnIndex(String columnName) {
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

    @Step("Get Newest Account Number (Max Value) for Type: {accountType}")
    public String getNewestAccountNumber(String accountType) {
        int typeColIdx = getColumnIndex(COL_HEADER_TYPE);
        int targetColIdx = getColumnIndex(COL_HEADER_ACCOUNT_NO);

        String xpath = String.format(
                "//div[@id='j_idt27']//table/tbody/tr[td[%d][contains(normalize-space(), '%s')]]/td[%d]",
                typeColIdx, accountType, targetColIdx
        );

        log.info("Scanning for accounts type '{}' to find the newest...", accountType);
        List<WebElement> accountCells = Driver.getDriver().findElements(By.xpath(xpath));

        if (accountCells.isEmpty()) {
            throw new RuntimeException("Not found account of: " + accountType);
        }

        long maxAccountNumber = accountCells.stream()
                .map(cell -> cell.getText().trim())
                .map(text -> Long.parseLong(text.replaceAll("[^0-9]", "")))
                .max(Long::compare)
                .orElse(0L);

        String result = String.valueOf(maxAccountNumber);
        log.info("Found Newest Account: {} (Type: {})", result, accountType);
        return result;
    }

    @Step("Close notification")
    public void closeDialogMessage() {
        waitForVisible(closeButton);
        click(closeButton);
    }

    @Step("Click on account number {accountNumber} to view details")
    public void clickDetailAccount(String accountNumber) {
        log.info("Opening detail for account: {}", accountNumber);
        By accountLink = By.xpath("//div[@id='j_idt27']//table//tbody//tr//td//a[normalize-space(text())='" + accountNumber + "']");

        waitForVisible(accountLink);
        click(accountLink);
    }

    @Step("Verify Latest Transaction Amount")
    public double getLatestTransactionAmount() {
        waitForVisible(latestTransactionRow);
        String rawText = getVisibleText(latestAmountCell);

        String cleanText = rawText.replaceAll("[^0-9.-]", "");

        return Math.abs(Double.parseDouble(cleanText));
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

    private final By closeButton = By.className("ui-icon-closethick");
    private final By headerTable = By.xpath("//div[@id='j_idt27']//table/thead//th");
    private final By successMessageText = By.cssSelector("#primefacesmessagedlg .ui-dialog-content");
    private final By latestTransactionRow = By.xpath("//tbody[@id='j_idt37_data']/tr[1]");
    private final By latestAmountCell = By.xpath("//tbody[@id='j_idt37_data']/tr[1]/td[3]");
}