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
import pages.transfer.internal.InternalTransferPage;
import utils.Messages;

import java.math.BigDecimal;

public class INT07 extends TestBase {
    InternalTransferPage internalTransferPage;
    InternalTransfer data;

    double currentBalance;
    double amountToTransfer;

    @BeforeMethod
    public void init() {
        setupUserWithBalance(AccountFactory.userDefault(), Constants.STANDARD_TRANSFER_AMOUNT);
        internalTransferPage = new InternalTransferPage();
    }

    @Test
    public void INT07() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToInternalTransferPage();

        internalTransferPage.selectAccount(this.currentDepositAcctAnyTerm);

        currentBalance = internalTransferPage.getAvailableBalance();
        amountToTransfer = currentBalance +  1;

        data = InternalTransferFactory.initData();
        data.setFromAccountValue("");
        data.setReceiverAccount(this.currentSavingAccount);
        data.setAmount(amountToTransfer);

        internalTransferPage.fillFormDetails(data);
        internalTransferPage.clickSubmit();

        Assert.assertEquals(internalTransferPage.getGeneralErrorMessage(), Messages.EXCESS_AMOUNT);
    }
}
