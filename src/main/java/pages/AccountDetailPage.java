package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Log4j2
public class AccountDetailPage extends BasePage {
    @Step("Get current balance from Account Detail page")
    public double getBalance() {
        String rawText = getCellValue(accountInfoTableBody, 6, COL_VALUE);

        String cleanText = rawText.replaceAll("[^0-9.]", "");
        return cleanText.isEmpty() ? 0.0 : Double.parseDouble(cleanText);
    }

    private String getCellValue(By tableBodyLocator, int rowIndex, int colIndex) {
        waitForVisible(tableBodyLocator);

        WebElement tableBody = driver.findElement(tableBodyLocator);

        String cellXpath = String.format(".//tr[%d]/td[%d]", rowIndex, colIndex);

        WebElement cell = tableBody.findElement(By.xpath(cellXpath));
        return cell.getText().trim();
    }

    private static final int COL_LABEL = 1;
    private static final int COL_VALUE = 2;
    private final By accountInfoTableBody = By.xpath("//div[@id='main-right']//div[contains(@class,'ui-panel-content')]//table/tbody");
}