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
        String receiverAccount = getValueByRow(3);
        String rawAmount       = getValueByRow(4);
        String content         = getValueByRow(6);
        String receiverName    = getValueByRow(7);

        String parsedAmount = rawAmount.replaceAll("[^0-9]", "");

        ExternalTransfer data = new ExternalTransfer();

        data.setReceiverAccount(receiverAccount);
        data.setAmount(Double.parseDouble(parsedAmount));
        data.setContent(content);
        data.setReceiverName(receiverName);

        return data;
    }

    private String getValueByRow(int rowNumber) {
        String dynamicXpath = String.format("%s/tr[%d]/td[2]/label", tableBodyXpath, rowNumber);

        By locator = By.xpath(dynamicXpath);
        waitForVisible(locator);
        return getVisibleText(locator).trim();
    }

    @Step("Click 'Xac nhan' and open OTP page")
    public void confirm() {
        waitForVisible(confirmButton);
        click(confirmButton);
    }

    private final String tableBodyXpath = "//div[contains(@id, 'content')]//tbody";
    private final By confirmButton = By.name("j_idt23:j_idt44");
}