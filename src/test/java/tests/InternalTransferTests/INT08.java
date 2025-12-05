package tests.InternalTransferTests;

import com.github.javafaker.Faker;
import datafactory.AccountFactory;
import datafactory.InternalTransferFactory;
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

    InternalTransfer data;
    String otp;
    OTPPage otpPage;
    double currentBalance;
    double transferAmount;
    Faker faker;
    String wrongOtp;

    @BeforeMethod
    public void init() {
        setupUserWithBalance(AccountFactory.userDefault(), Constants.STANDARD_TRANSFER_AMOUNT);

        transferPage = new InternalTransferPage();
        confirmPage = new ConfirmTransferPage();
        bankAccountPage = new BankAccountPage();
    }

    @Test(description = "Wrong OTP blocks the Internal transfer")
    public void INT08() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToInternalTransferPage();

        transferPage.selectAccount(this.currentDepositAcctAnyTerm);

        currentBalance = transferPage.getAvailableBalance();
        transferAmount = TransferUtils.generateValidTransferAmount(currentBalance, Constants.INT_FEE);

        data = InternalTransferFactory.initData();
        data.setFromAccountValue("");
        data.setAmount(transferAmount);

        transferPage.submitForm(data);

        confirmPage.confirm();

        otp = confirmPage.getOtpFromEmail();

        faker = new Faker();
        wrongOtp = otp + faker.number().digits(3);

        otpPage = new OTPPage();

        otpPage.enterOtp(wrongOtp);

        otpPage.clickTransfer();

        Assert.assertEquals(otpPage.getErrorMessage(), Messages.WRONG_OTP);
    }
}