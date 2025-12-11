package tests.InternalTransferTests;

import base.TestBase;
import datafactory.AccountFactory;
import lombok.extern.slf4j.Slf4j;
import models.InternalTransfer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
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
    InternalTransfer data = new InternalTransfer();

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

        // Open the Internal Transfer interface
        homePage.goToInternalTransferPage();

        // Fill data into Source Account, Receiving Account Number, Amount, and Payment Content
        data.setFromAccountValue(this.currentDepositAccAnyTerm);
        data.setReceiverAccount(currentSavingAccount);
        data.setAmount(amountTransfer);
        data.setContent(content);

        // Click on the "Confirm" button to navigate to the "Confirm" screen
        internalTransferPage.submitTransferInfo(data);

        // Click on the "Confirm" button to navigate to the "Enter OTP" screen
        confirmPage.confirm();

        // Receive OTP in account email
        otp = confirmPage.getOtpFromEmail();

        // Enter OTP into the Transaction Code (Mã giao dịch) field
        otpPage.enterOtp(otp);

        // Click on "Transfer Money"
        otpPage.clickTransfer();

        // Popup shows "Transfer successful" (Chuyển tiền thành công).
        sa.assertEquals(bankAccountPage.getSuccessMessage(), Messages.MONEY_TRANSFER_SUCCESSFUL);

        bankAccountPage.closeDialogMessage();

        // Sender/recipient balances updated
        sa.assertEquals(bankAccountPage.getLatestTransactionAmount(1), data.getAmount());

        bankAccountPage.viewAccountDetail(this.currentDepositAccAnyTerm);

        expectedEndingBalance = TransferUtils.calcExpectedBalanceInternal(currentBalance, data.getAmount());

        sa.assertEquals(accountDetailPage.getBalance(), expectedEndingBalance);
        sa.assertAll();
    }
}
