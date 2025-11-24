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

public class EXT06 extends TestBase {
    ExternalTransferPage externalTransferPage;
    ExternalTransfer data;

    @BeforeMethod
    public void init() {
        externalTransferPage = new ExternalTransferPage();
    }

    @Test
    public void EXT06() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.clickExternalTransfer();

        data = ExternalTransferFactory.initData();
        data.setReceiverAccount("100001440");

        externalTransferPage.submitForm(data);

        Assert.assertEquals(externalTransferPage.getGeneralErrorMessage(), Messages.INCORRECT_BENEFICIARY_INFORMATION);
    }
}
