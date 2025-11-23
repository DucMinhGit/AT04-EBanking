package tests.InternalTransferTests;

import com.github.javafaker.Faker;
import models.InternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import pages.BankAccountPage;
import pages.transfer.external.AccountHistoryPage;
import utils.Driver;
import base.TestBase;
import org.testng.annotations.Test;
import pages.transfer.internal.ConfirmTransferPage;
import pages.transfer.internal.OTPPage;
import pages.LoginPage;
import pages.HomePage;
import pages.transfer.internal.InternalTransferPage;

import java.math.BigDecimal;

public class INT08 extends TestBase {
    InternalTransferPage transferPage;
    ConfirmTransferPage confirmPage;
    BankAccountPage bankAccountPage;
    AccountHistoryPage accountHistoryPage;


    InternalTransfer data;
    String otp;
    String successMessage;
    OTPPage otpPage;
    double currentBalance;
    double fee;
    double maxTransferable;
    double transferAmount;
    double actualEndingBalance;
    double expectedEndingBalance;
    double actualTransactionAmount;
    double expectedAmount;
    Faker faker;
    String expectedErrorMessage;
    String wrongOtp;
    String actualErrorMessage;

    @BeforeMethod
    public void init() {
        prepareAccountData("987656789", "ZXC1234@", 5000);

        transferPage = new InternalTransferPage();
        confirmPage = new ConfirmTransferPage();
        bankAccountPage = new BankAccountPage();
        accountHistoryPage = new AccountHistoryPage();
    }

    @Test
    public void INT08() {
        userLogin.login("987656789", "ZXC1234@");
        homePage.clickTransfer();

        transferPage.selectAccount(this.currentDepositAcctAnyTerm);

        currentBalance = transferPage.getAvailableBalance();
        fee = 1100;
        maxTransferable = currentBalance - fee;
        transferAmount = Math.ceil(Math.random() * maxTransferable);

        data = InternalTransfer.builder()
//                .fromAccountValue(this.currentDepositAcctAnyTerm)
                .receiverAccount(this.currentSavingAccount)
                .content("ck")
                .amount(transferAmount)
                .build();

        transferPage.submitForm(data);

        confirmPage.clickConfirm();

        otp = confirmPage.getOtpFromEmail();
        faker = new Faker();
        wrongOtp = otp + faker.number().digits(3);

        otpPage = new OTPPage();
        otpPage.enterOtp(wrongOtp);

        otpPage.clickTransfer();

        actualErrorMessage = otpPage.getErrorMessage();
        expectedErrorMessage = "Sai mã OTP";

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
}