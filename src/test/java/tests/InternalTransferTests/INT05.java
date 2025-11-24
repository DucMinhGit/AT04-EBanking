package tests.InternalTransferTests;

import datafactory.AccountFactory;
import datafactory.InternalTransferFactory;
import models.Account;
import models.InternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import utils.Constants;
import utils.Driver;
import base.TestBase;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.HomePage;
import pages.transfer.internal.InternalTransferPage;
import utils.Messages;

import java.math.BigDecimal;

public class INT05 extends TestBase {
    InternalTransferPage internalTransferPage;
    InternalTransfer data;

    @BeforeMethod
    public void init() {
        internalTransferPage = new InternalTransferPage();
    }

    @Test
    public void INT05() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToInternalTransferPage();

        data = InternalTransferFactory.initData();
        data.setContent("");

        internalTransferPage.submitForm(data);

        Assert.assertEquals(internalTransferPage.getGeneralErrorMessage(), Messages.CONTENT_MUST_NOT_BE_EMPTY);
    }
}
