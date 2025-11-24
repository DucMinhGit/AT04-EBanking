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

public class EXT01 extends TestBase {
    ExternalTransferPage externalTransferPage;
    ExternalTransfer data;

    @BeforeMethod
    public void init() {
        externalTransferPage = new ExternalTransferPage();
    }

    @Test(description = "Show error message when 'Account' fields blank")
    public void EXT01() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.clickExternalTransfer();

        data = ExternalTransferFactory.initData();
        data.setFromAccountValue("");

        externalTransferPage.submitForm(data);

        Assert.assertEquals(externalTransferPage.getGeneralErrorMessage(), Messages.ACCOUNT_MUST_NOT_BE_EMPTY);
    }
}
