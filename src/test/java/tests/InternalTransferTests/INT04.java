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

public class INT04 extends TestBase {
    InternalTransferPage internalTransferPage;

    InternalTransfer data = new InternalTransfer();
    double currentBalance = Constants.STANDARD_TRANSFER_AMOUNT;
    String content = "TCS INT 03";

    @BeforeMethod
    public void init() {
        setupUserWithBalance(AccountFactory.userDefault(), Constants.STANDARD_TRANSFER_AMOUNT);

        internalTransferPage = new InternalTransferPage();
    }

    @Test(description = "Show error message when 'Amount' fields blank")
    public void INT04() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToInternalTransferPage();

        data.setAmount(null);
        data.setReceiverAccount(this.currentSavingAccount);
        data.setFromAccountValue(this.currentDepositAccAnyTerm);
        data.setContent(content);


        internalTransferPage.submitTransferInfo(data);

        Assert.assertEquals(internalTransferPage.getGeneralErrorMessage(), Messages.CONTENT_MUST_NOT_BE_EMPTY);
    }
}
