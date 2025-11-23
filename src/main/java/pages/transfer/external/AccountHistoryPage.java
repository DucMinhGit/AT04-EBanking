package pages.transfer.external;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import pages.BasePage;

@Log4j2
public class AccountHistoryPage extends BasePage {
    @Step("Verify Latest Transaction Amount")
    public double getLatestTransactionAmount() {
        waitForVisible(latestTransactionRow);
        String rawText = getVisibleText(latestAmountCell);

        String cleanText = rawText.replaceAll("[^0-9.-]", "");

        return Math.abs(Double.parseDouble(cleanText));
    }

    private final By latestTransactionRow = By.xpath("//tbody[@id='j_idt37_data']/tr[1]");
    private final By latestAmountCell = By.xpath("//tbody[@id='j_idt37_data']/tr[1]/td[3]");
}