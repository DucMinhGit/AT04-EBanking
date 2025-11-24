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
    double currentBalance;
    double transferAmount;
    double expectedEndingBalance;

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

        internalTransferPage.selectAccount(this.currentDepositAcctAnyTerm);

        // Lấy số dư hiện tại của tài khoản đó (Lưu lại để tính toán đối chiếu sau này)
        currentBalance = internalTransferPage.getAvailableBalance();

        // Gọi hàm TransferUtils để sinh ra một số tiền chuyển ngẫu nhiên nhưng hợp lệ
        // (Số tiền này đảm bảo nhỏ hơn: Số dư hiện tại - Phí chuyển khoản)
        transferAmount = TransferUtils.generateValidTransferAmount(currentBalance, Constants.INT_FEE);

        data = InternalTransferFactory.initData();
        data.setFromAccountValue("");// Để trống vì đã chọn tài khoản ở trên rồi
        data.setReceiverAccount(this.currentSavingAccount);// Set tài khoản người nhận
        data.setAmount(transferAmount);// Set số tiền cần chuyển vừa tính được

        internalTransferPage.submitForm(data);

        confirmPage.clickConfirm();

        otp = confirmPage.getOtpFromEmail();

        otpPage.enterOtp(otp);

        otpPage.clickTransfer();

        Assert.assertEquals(bankAccountPage.getSuccessMessage(), Messages.MONEY_TRANSFER_SUCCESSFUL);

        bankAccountPage.closeDialogMessage();

        Assert.assertEquals(bankAccountPage.getLatestTransactionAmount(), data.getAmount());

        bankAccountPage.clickDetailAccount(this.currentDepositAcctAnyTerm);

        // Tính toán số dư mong đợi theo công thức chuẩn:
        // Số dư mong đợi = Số dư ban đầu - Số tiền đã chuyển - Phí dịch vụ
        expectedEndingBalance = TransferUtils.calcExpectedBalance(currentBalance, data.getAmount(), Constants.INT_FEE);

        // 3. Kiểm tra số dư thực tế trên web (accountDetailPage) có khớp với số dư mong đợi đã tính toán không
        Assert.assertEquals(accountDetailPage.getBalance(), expectedEndingBalance);
    }
}
