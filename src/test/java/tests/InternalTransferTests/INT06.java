package tests.InternalTransferTests;

import datafactory.AccountFactory;
import models.InternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import base.TestBase;
import org.testng.annotations.Test;
//import pages.AccountListingPage;
import pages.transfer.internal.InternalTransferPage;
import utils.Constants;
import utils.Messages;

public class INT06 extends TestBase {
    InternalTransferPage internalTransferPage;

    InternalTransfer data = new InternalTransfer();
    double currentBalance = Constants.STANDARD_TRANSFER_AMOUNT;
    double amountTransfer = (currentBalance - Constants.INT_FEE) - 1;
    String content = "TCS INT 06";

    @BeforeMethod
    public void init() {
        setupUserWithBalance(AccountFactory.userDefault(), Constants.STANDARD_TRANSFER_AMOUNT);

        internalTransferPage = new InternalTransferPage();
    }

    @Test(description = "Reject transfer with invalid receive account details")
    public void INT06() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToInternalTransferPage();

        data.setReceiverAccount("1000099999");
        data.setFromAccountValue(this.currentDepositAccAnyTerm);
        data.setAmount(amountTransfer);
        data.setContent(content);

        internalTransferPage.submitTransferInfo(data);

        Assert.assertEquals(internalTransferPage.getGeneralErrorMessage(), Messages.INVALID_ACCOUNT);
    }
}
