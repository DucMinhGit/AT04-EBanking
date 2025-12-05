package tests.ExternalTransferTests;

import base.TestBase;
import datafactory.AccountFactory;
import datafactory.ExternalTransferFactory;
import models.ExternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.transfer.external.ExternalTransferPage;
import utils.Constants;
import utils.Messages;

public class EXT03 extends TestBase {
    ExternalTransferPage externalTransferPage;
    ExternalTransfer data;

    @BeforeMethod
    public void init() {
        externalTransferPage = new ExternalTransferPage();
    }

    @Test(description = "Show error message when 'Recipient Account Name' fields blank")
    public void EXT03() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToExternalTransferPage();

        data = ExternalTransferFactory.initData();
        data.setReceiverName("");

        externalTransferPage.submitTransferInfo(data);

        Assert.assertEquals(externalTransferPage.getGeneralErrorMessage(), Messages.RECIVER_NAME_MUST_NOT_BE_EMPTY);
    }
}
