package tests.InternalTransferTests;

import com.github.javafaker.Faker;
import datafactory.AccountFactory;
import models.InternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import pages.BankAccountPage;
import utils.Constants;
import base.TestBase;
import org.testng.annotations.Test;
import pages.transfer.internal.ConfirmTransferPage;
import pages.transfer.internal.OTPPage;
import pages.transfer.internal.InternalTransferPage;
import utils.Messages;
import utils.TransferUtils;

public class INT08 extends TestBase {
    InternalTransferPage transferPage;
    ConfirmTransferPage confirmPage;
    BankAccountPage bankAccountPage;

    InternalTransfer data = new InternalTransfer();
    String otp;
    OTPPage otpPage;
    double transferAmount;
    Faker faker;
    String wrongOtp;
    String content = "TCS 08";

    @BeforeMethod
    public void init() {
        setupUserWithBalance(AccountFactory.userDefault(), Constants.STANDARD_TRANSFER_AMOUNT);

        transferPage = new InternalTransferPage();
        confirmPage = new ConfirmTransferPage();
        bankAccountPage = new BankAccountPage();
        otpPage = new OTPPage();
        faker = new Faker();
    }

    @Test(description = "Wrong OTP blocks the Internal transfer")
    public void INT08() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToInternalTransferPage();

        transferAmount = TransferUtils.generateValidTransferAmount(Constants.STANDARD_TRANSFER_AMOUNT, Constants.INT_FEE);

        data.setFromAccountValue(this.currentDepositAccAnyTerm);
        data.setReceiverAccount(this.currentSavingAccount);
        data.setAmount(transferAmount);
        data.setContent(content);

        transferPage.submitTransferInfo(data);

        confirmPage.confirm();

        otp = confirmPage.getOtpFromEmail();

        wrongOtp = TransferUtils.generateInvalidOtp(otp);

        otpPage.enterOtp(wrongOtp);

        otpPage.clickTransfer();

        Assert.assertEquals(otpPage.getErrorMessage(), Messages.WRONG_OTP);
    }
}