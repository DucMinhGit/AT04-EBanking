package tests;

import base.TestBase;
import data.ExternalTransferData;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.transfer.external.ExternalTransferPage;
import utils.DataProviders;

@Log4j2
public class ExternalTransferTests extends TestBase {
    private ExternalTransferPage externalTransferPage;

    @BeforeMethod
    public void init() {
        externalTransferPage = new ExternalTransferPage();
    }

    @Test(dataProvider = "externalTransferErrorDataFromJson", dataProviderClass = DataProviders.class)
    public void verifyExternalTransferFieldValidation(ExternalTransferData data) {
        log.info("Running test: {}", data.getCaseId());
        homePage.clickExternalTransfer();
        externalTransferPage.fillAndSubmitTransferForm(data);
        String actualErrorMessage = externalTransferPage.getGeneralErrorMessage();
        Assert.assertEquals(actualErrorMessage, data.getExpectedErrorMessage(),
                "error : " + data.getContent());
    }
}
