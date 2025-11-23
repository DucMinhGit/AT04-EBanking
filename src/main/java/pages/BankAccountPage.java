package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.Driver;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class BankAccountPage extends BasePage {
    private String getCellValue(String columnName, String rowLogic) {
        waitForVisible(headerTable);

        List<WebElement> columns = Driver.getDriver().findElements(headerTable);

        List<String> columnTexts = columns.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());

        int colIdx = columnTexts.indexOf(columnName.trim()) + 1;

        if (colIdx == 0) {
            throw new RuntimeException("Column not found: " + columnName + " | Headers: " + columnTexts);
        }

        String cellXpath = "//div[@id='j_idt27']//table/tbody/tr[" + rowLogic + "]/td[" + colIdx + "]";

        WebElement cell = Driver.getDriver().findElement(By.xpath(cellXpath));
        return cell.getText().trim();
    }

    @Step("Get Account Number from the list")
    public String getAccountNumber(String columnName, String row) {
        return getCellValue(columnName, row);
    }

    @Step("Get Account Number from the list")
    public String getAccountNumber(String columnName, int row) {
        return getCellValue(columnName, String.valueOf(row));
    }

    @Step("Close notification")
    public void closeNotification() {
        waitForVisible(closeButton);
        click(closeButton);
    }

    @Step("Click on account number {accountNumber} to view details")
    public void clickDetailAccount(String accountNumber) {
        log.info("Opening detail for account: {}", accountNumber);
        // XPath tìm thẻ <a> có text trùng với số tài khoản
        By accountLink = By.xpath("//div[@id='j_idt27']//table//tbody//tr//td//a[normalize-space(text())='" + accountNumber + "']");

        waitForVisible(accountLink);
        click(accountLink);
    }

    private final By accountTableBody = By.id("j_idt27_data");
    private final By closeButton = By.className("ui-icon-closethick");
    private final By lastestAccountNumberLink = By.xpath("//div[(@id='j_idt25_content')] //div[(@id='j_idt27')]  //tr[last()]//td[1]//a");
    private final By latestTransactionAmount = By.xpath("//tbody[@id='j_idt37_data']/tr[1]/td[3]");
    private final By latestTransactionDate = By.xpath("//tbody[@id='j_idt37_data']/tr[1]/td[1]");
    private final By headerTable = By.xpath("//div[@id='j_idt27']//table/thead//th");
}