package tests.InternalTransferTests;

import datafactory.AccountFactory;
import datafactory.InternalTransferFactory;
import models.InternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import base.TestBase;
import org.testng.annotations.Test;
import pages.transfer.internal.InternalTransferPage;
import utils.Messages;

public class INT02 extends TestBase {
    InternalTransferPage internalTransferPage;
    InternalTransfer data;

    @BeforeMethod
    public void init() {
        internalTransferPage = new InternalTransferPage();
    }

    @Test
    public void INT02() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToInternalTransferPage();

        data = InternalTransferFactory.initData();
        data.setFromAccountValue("");

        internalTransferPage.submitForm(data);

        Assert.assertEquals(internalTransferPage.getGeneralErrorMessage(), Messages.PLEASE_CHOOSE_AN_ACCOUNT);
    }
}
