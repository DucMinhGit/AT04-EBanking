package pages.transfer.external;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import models.ExternalTransfer;
import org.openqa.selenium.By;
import pages.BasePage;

@Log4j2
public class ExternalTransferConfirmationPage extends BasePage {
    public ExternalTransferConfirmationPage() {
        super();
    }

    @Step("Get data for verify (Preview Data)")
    public ExternalTransfer getConfirmationDetailsAsModel() {
        String rawAmount = getVisibleText(amountLabel).trim();
        String parsedAmount = rawAmount.replaceAll("[^0-9]", "");
        return ExternalTransfer.builder()
                .receiverAccount(getVisibleText(receiverAccountLabel).trim())
                .amount(Double.parseDouble(parsedAmount))
                .content(getVisibleText(contentLabel).trim())
                .receiverName(getVisibleText(receiverNameLabel).trim())
                .build();
    }

    @Step("Click 'Xac nhan' and open OTP page")
    public void clickConfirm() {
        waitForVisible(confirmButton);
        click(confirmButton);
    }

    private final By confirmButton = By.name("j_idt23:j_idt44");
    private final String tableBodyXPath = "//input[contains(@name, ':j_idt44')]/ancestor::tbody[1]";
    private final By receiverAccountLabel = By.xpath(tableBodyXPath + "/tr[3]/td[2]/label");
    private final By amountLabel = By.xpath(tableBodyXPath + "/tr[4]/td[2]/label");
    private final By contentLabel = By.xpath(tableBodyXPath + "/tr[6]/td[2]/label");
    private final By receiverNameLabel = By.xpath(tableBodyXPath + "/tr[7]/td[2]/label");
}