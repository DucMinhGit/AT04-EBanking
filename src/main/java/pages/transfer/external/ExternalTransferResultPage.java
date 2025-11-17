package pages.transfer.external;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

@Log4j2
public class ExternalTransferResultPage extends BasePage {
    public ExternalTransferResultPage() {
        super();
        log.info("Waiting result (wait dialog successfully)...");
    }

    public String getSuccessMessage() {
        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBe(successMessageText, "")
        ));
        String message = getVisibleText(successMessageText);
        log.info("Result: {}", message);
        return message;
    }

    private final By successDialog = By.id("primefacesmessagedlg");
    private final By successMessageText = By.cssSelector("#primefacesmessagedlg .ui-dialog-content");
}