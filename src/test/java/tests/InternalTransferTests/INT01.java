package tests.InternalTransferTests;

import base.TestBase;
import lombok.extern.slf4j.Slf4j;
import models.InternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import pages.transfer.external.AccountHistoryPage;
import pages.transfer.internal.*;

import java.math.BigDecimal;

@Slf4j
public class INT01 extends TestBase {
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

    @BeforeMethod
    public void init() {
        prepareAccountData("987656789", "ZXC1234@", 5000);

        transferPage = new InternalTransferPage();
        confirmPage = new ConfirmTransferPage();
        bankAccountPage = new BankAccountPage();
        accountHistoryPage = new AccountHistoryPage();
    }

    @Test
    public void INT01() {
        userLogin.login("987656789", "ZXC1234@");
        homePage.clickTransfer();

        transferPage.selectAccount(this.currentDepositAcctAnyTerm);

        currentBalance = transferPage.getAvailableBalance();
        log.info("balance: {}", currentBalance);
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

        otpPage = new OTPPage();
        otpPage.enterOtp(otp);
        otpPage.clickTransfer();

        successMessage = bankAccountPage.getSuccessMessage();

        Assert.assertEquals(successMessage, "Chuyển tiền thành công");

        bankAccountPage.closeNotification();

        actualTransactionAmount = accountHistoryPage.getLatestTransactionAmount();
        expectedAmount = data.getAmount();

        Assert.assertEquals(actualTransactionAmount, expectedAmount);

        homePage.clickBankAccount();
        bankPage.clickDetailAccount(this.currentDepositAcctAnyTerm);

        actualEndingBalance = accountDetailPage.getBalance();
        expectedEndingBalance = currentBalance - data.getAmount() - fee;

        Assert.assertEquals(actualEndingBalance, expectedEndingBalance);
    }
}
