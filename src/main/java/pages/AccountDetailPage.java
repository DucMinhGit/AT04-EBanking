package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;

@Log4j2
public class AccountDetailPage extends BasePage {

    @Step("Get current balance from Account Detail page")
    public double getBalance() {
        waitForVisible(balanceLabel);
        String rawText = getVisibleText(balanceLabel);
        String cleanText = rawText.replaceAll("[^0-9]", "");

        log.info("Current Balance text: {} -> parsed: {}", rawText, cleanText);
        return Double.parseDouble(cleanText);
    }

    private final By balanceLabel = By.xpath("//div[@id='j_idt29_content']//tr[6]//td[2]//label");
}