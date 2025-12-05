package tests.InternalTransferTests;

import datafactory.AccountFactory;
import models.InternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import base.TestBase;
import org.testng.annotations.Test;
import pages.transfer.internal.InternalTransferPage;
import utils.Messages;

public class INT02 extends TestBase {
    InternalTransferPage internalTransferPage;
    InternalTransfer data = new InternalTransfer();

    @BeforeMethod
    public void init() {
        internalTransferPage = new InternalTransferPage();
    }

    @Test(description = "Show error message when 'Transfer Account' fields blank")
    public void INT02() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToInternalTransferPage();

        data.setFromAccountValue("");

        internalTransferPage.submitTransferInfo(data);

        Assert.assertEquals(internalTransferPage.getGeneralErrorMessage(), Messages.PLEASE_CHOOSE_AN_ACCOUNT);
    }
}
