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

public class EXT04 extends TestBase {
    ExternalTransferPage externalTransferPage;
    ExternalTransfer data;

    @BeforeMethod
    public void init() {
        externalTransferPage = new ExternalTransferPage();
    }

    @Test(description = "Show error message when 'Bank' fields blank")
    public void EXT04() {
        userLoginPage.login(AccountFactory.userDefault());
        homePage.clickExternalTransfer();

        data = ExternalTransferFactory.initData();
        data.setBankValue("");

        externalTransferPage.submitForm(data);

        Assert.assertEquals(externalTransferPage.getGeneralErrorMessage(), Messages.BANK_MUST_NOT_BE_EMPTY);
    }
}
