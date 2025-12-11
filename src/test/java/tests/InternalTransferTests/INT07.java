package tests.InternalTransferTests;

import datafactory.AccountFactory;
import models.InternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import utils.Constants;
import base.TestBase;
import org.testng.annotations.Test;
import pages.transfer.internal.InternalTransferPage;
import utils.Messages;

public class INT07 extends TestBase {
    InternalTransferPage internalTransferPage;
    InternalTransfer data = new InternalTransfer();

    double amountToTransfer;
    String content = "TSC 07";

    @BeforeMethod
    public void init() {
        setupUserWithBalance(AccountFactory.userDefault(), Constants.STANDARD_TRANSFER_AMOUNT);
        internalTransferPage = new InternalTransferPage();
    }

    @Test(description = "Reject transfers with insufficient funds")
    public void INT07() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToInternalTransferPage();

        amountToTransfer = Constants.STANDARD_TRANSFER_AMOUNT +  1;

        data.setFromAccountValue(this.currentDepositAccAnyTerm);
        data.setReceiverAccount(this.currentSavingAccount);
        data.setAmount(amountToTransfer);
        data.setContent(content);

        internalTransferPage.submitTransferInfo(data);

        Assert.assertEquals(internalTransferPage.getGeneralErrorMessage(), Messages.EXCESS_AMOUNT);
    }
}
