package tests.InternalTransferTests;

import base.TestBase;
import datafactory.AccountFactory;
import datafactory.InternalTransferFactory;
import lombok.extern.slf4j.Slf4j;
import models.InternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import pages.transfer.internal.*;
import utils.TransferUtils;
import utils.Constants;
import utils.Messages;

@Slf4j
public class INT01 extends TestBase {
    InternalTransferPage internalTransferPage;
    ConfirmTransferPage confirmPage;
    BankAccountPage bankAccountPage;
    InternalTransfer data;

    String otp;
    OTPPage otpPage;
    double expectedEndingBalance;
    double currentBalance = Constants.STANDARD_TRANSFER_AMOUNT;
    double amountTransfer = (currentBalance - Constants.INT_FEE) - 1;
    String content = "TCS INT 08";

    @BeforeMethod
    public void init() {
        setupUserWithBalance(AccountFactory.userDefault(), Constants.STANDARD_TRANSFER_AMOUNT);

        internalTransferPage = new InternalTransferPage();
        confirmPage = new ConfirmTransferPage();
        bankAccountPage = new BankAccountPage();
        otpPage = new OTPPage();
    }

    @Test(description = "Successful Internal transfer")
    public void INT01() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToInternalTransferPage();

        data = InternalTransfer.builder()
                .fromAccountValue(this.currentDepositAcctAnyTerm)
                .receiverAccount(currentSavingAccount)
                .amount(amountTransfer)
                .content(content)
                .build();

        internalTransferPage.submitForm(data);

        confirmPage.clickConfirm();

        otp = confirmPage.getOtpFromEmail();

        otpPage.enterOtp(otp);

        otpPage.clickTransfer();

        Assert.assertEquals(bankAccountPage.getSuccessMessage(), Messages.MONEY_TRANSFER_SUCCESSFUL);

        bankAccountPage.closeDialogMessage();

        Assert.assertEquals(bankAccountPage.getLatestTransactionAmount(1), data.getAmount());

        bankAccountPage.viewAccountDetail(this.currentDepositAcctAnyTerm);

        expectedEndingBalance = TransferUtils.calcExpectedBalanceInternal(currentBalance, data.getAmount());

        Assert.assertEquals(accountDetailPage.getBalance(), expectedEndingBalance);
    }
}
