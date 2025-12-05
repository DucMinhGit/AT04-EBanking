package tests.InternalTransferTests;

import datafactory.AccountFactory;
import models.InternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import base.TestBase;
import org.testng.annotations.Test;
//import pages.AccountListingPage;
import pages.transfer.internal.InternalTransferPage;
import utils.Messages;

public class INT03 extends TestBase {
    InternalTransferPage internalTransferPage;
    InternalTransfer data = new InternalTransfer();

    @BeforeMethod
    public void init() {
        internalTransferPage = new InternalTransferPage();
    }

    @Test(description = "Show error message when 'Receive Account' fields blank")
    public void INT03() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToInternalTransferPage();

        data.setReceiverAccount("");

        internalTransferPage.submitTransferInfo(data);

        Assert.assertEquals(internalTransferPage.getGeneralErrorMessage(), Messages.INVALID_ACCOUNT);
    }
}
