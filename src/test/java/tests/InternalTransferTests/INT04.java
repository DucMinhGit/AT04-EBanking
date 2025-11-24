package tests.InternalTransferTests;

import datafactory.AccountFactory;
import datafactory.InternalTransferFactory;
import models.InternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import utils.Constants;
import utils.Driver;
import base.TestBase;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.HomePage;
//import pages.AccountListingPage;
import pages.transfer.internal.InternalTransferPage;
import utils.Messages;

public class INT04 extends TestBase {
    InternalTransferPage internalTransferPage;
    InternalTransfer data;

    @BeforeMethod
    public void init() {
        internalTransferPage = new InternalTransferPage();
    }

    @Test(description = "Show error message when 'Amount' fields blank")
    public void INT04() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToInternalTransferPage();

        data = InternalTransferFactory.initData();
        data.setAmount(null);

        internalTransferPage.submitForm(data);

        Assert.assertEquals(internalTransferPage.getGeneralErrorMessage(), Messages.CONTENT_MUST_NOT_BE_EMPTY);
    }
}
