package tests.InternalTransferTests;

import datafactory.AccountFactory;
import datafactory.InternalTransferFactory;
import models.ExternalTransfer;
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

import java.math.BigDecimal;

public class INT03 extends TestBase {
    InternalTransferPage internalTransferPage;
    InternalTransfer data;

    @BeforeMethod
    public void init() {
        internalTransferPage = new InternalTransferPage();
    }

    @Test(description = "Show error message when 'Receive Account' fields blank")
    public void INT03() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToInternalTransferPage();

        data = InternalTransferFactory.initData();
        data.setReceiverAccount("");

        internalTransferPage.submitTransferInfo(data);

        Assert.assertEquals(internalTransferPage.getGeneralErrorMessage(), Messages.INVALID_ACCOUNT);
    }
}
