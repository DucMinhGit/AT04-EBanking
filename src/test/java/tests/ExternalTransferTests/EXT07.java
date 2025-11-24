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

public class EXT07 extends TestBase {
    ExternalTransferPage externalTransferPage;
    ExternalTransfer data;

    double amountToTransfer;
    double currentBalance;

    @BeforeMethod
    public void init() {
        setupUserWithBalance(AccountFactory.userDefault(), Constants.STANDARD_TRANSFER_AMOUNT);
        externalTransferPage = new ExternalTransferPage();
    }

    @Test
    public void EXT07() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.clickExternalTransfer();

        externalTransferPage.selectAccount(this.currentDepositAcctAnyTerm);

        currentBalance = externalTransferPage.getAvailableBalance();
        amountToTransfer = currentBalance + 1;

        data = ExternalTransferFactory.initData();
        data.setFromAccountValue("");
        data.setAmount(amountToTransfer);

        externalTransferPage.fillFormDetails(data);

        externalTransferPage.clickSubmit();

        Assert.assertEquals(externalTransferPage.getGeneralErrorMessage(), Messages.OVERDUE_AMOUNT);
    }
}
