package tests.ExternalTransferTests;

import base.TestBase;
import com.github.javafaker.Faker;
import com.mailosaur.MailosaurException;
import datafactory.AccountFactory;
import datafactory.ExternalTransferFactory;
import models.Account;
import models.ExternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.transfer.external.ExternalTransferConfirmationPage;
import pages.transfer.external.ExternalTransferOtpPage;
import pages.transfer.external.ExternalTransferPage;
import utils.Constants;
import utils.Messages;
import utils.TransferUtils;

import javax.swing.text.Utilities;
import java.io.IOException;

public class EXT08 extends TestBase {
    ExternalTransferPage externalTransferPage;
    ExternalTransferConfirmationPage confirmationPage;
    ExternalTransferOtpPage otpPage;

    ExternalTransfer data;

    String otp;
    String wrongOtp;
    Faker faker;
    double currentBalance;
    double transferAmount;

    @BeforeMethod
    public void init() {
        setupUserWithBalance(AccountFactory.userDefault(), Constants.STANDARD_TRANSFER_AMOUNT);

        externalTransferPage = new ExternalTransferPage();
        confirmationPage = new ExternalTransferConfirmationPage();
        otpPage = new ExternalTransferOtpPage();
        faker = new Faker();
    }

    @Test(description = "Wrong OTP blocks the External transfer")
    public void EXT08() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToExternalTransferPage();

        externalTransferPage.selectAccount(this.currentDepositAcctAnyTerm);

        currentBalance = externalTransferPage.getAvailableBalance();
        transferAmount = TransferUtils.generateValidTransferAmount(currentBalance, Constants.EXT_FEE);

        data = ExternalTransferFactory.initData();
        data.setFromAccountValue("");
        data.setAmount(transferAmount);

        externalTransferPage.submitForm(data);
        confirmationPage.clickConfirm();

        otp = otpPage.getOtpFromEmail();

        wrongOtp = otp + faker.number().digits(3);

        otpPage.submitOtp(wrongOtp);

        Assert.assertEquals(otpPage.getErrorMessage(), Messages.WRONG_OTP);
    }
}
