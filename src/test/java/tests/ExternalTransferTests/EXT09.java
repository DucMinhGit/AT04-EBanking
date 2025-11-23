package tests.ExternalTransferTests;

import base.TestBase;
import com.mailosaur.MailosaurException;
import lombok.extern.log4j.Log4j2;
import models.ExternalTransferModel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AccountDetailPage;
import pages.transfer.external.*;
import utils.Constants;

import java.io.IOException;

@Log4j2
public class EXT09 extends TestBase {
    String username = Constants.DEFAULT_USERNAME;
    String password = Constants.DEFAULT_PASSWORD;

    ExternalTransferPage externalTransferPage;
    ExternalTransferConfirmationPage confirmationPage;
    ExternalTransferOtpPage otpPage;
    ExternalTransferResultPage resultPage;
    AccountHistoryPage accountHistoryPage;
    AccountDetailPage accountDetailPage;

    ExternalTransferModel data;
    ExternalTransferModel previewData;
    String otp;
    String successMessage;
    double actualTransactionAmount;
    double expectedAmount;
    double currentBalance;
    double fee;
    double maxTransferable;
    double transferAmount;
    double actualEndingBalance;
    double expectedEndingBalance;

    @BeforeMethod
    public void init() {
        prepareAccountData(username, password, 5000);

        externalTransferPage = new ExternalTransferPage();
        confirmationPage = new ExternalTransferConfirmationPage();
        otpPage = new ExternalTransferOtpPage();
        resultPage = new ExternalTransferResultPage();
        accountHistoryPage = new AccountHistoryPage();
        accountDetailPage = new AccountDetailPage();
    }

    @Test
    public void EXT09() throws IOException, MailosaurException {
        userLogin.login(username, password);
        homePage.clickExternalTransfer();

        externalTransferPage.selectAccount(this.currentDepositAcctAnyTerm);

        currentBalance = externalTransferPage.getAvailableBalance();
        fee = 3300;
        maxTransferable = currentBalance - fee;
        transferAmount = Math.ceil(Math.random() * maxTransferable);

        data = ExternalTransferModel.builder()
//                .fromAccountValue(this.currentDepositAcctAnyTerm)
                .receiverAccount("10001111")
                .receiverName("Nguyen Van A")
                .bankValue("Ngân hàng Đông Á")
                .branchValue("Chi nhánh Đà Nẵng")
                .content("Test case 3: Bo trong ten nguoi nhan")
                .amount(transferAmount)
                .build();

        externalTransferPage.submitForm(data);

        previewData = confirmationPage.getConfirmationDetailsAsModel();

        Assert.assertEquals(previewData.getReceiverAccount(), data.getReceiverAccount(), "Wrong account received");
        Assert.assertEquals(previewData.getAmount(), data.getAmount(), "Wrong amount");
        Assert.assertEquals(previewData.getContent(), data.getContent(), "Wrong content");
        Assert.assertEquals(previewData.getReceiverName(), data.getReceiverName(), "Wrong name received");

        confirmationPage.clickConfirm();
        otp = otpPage.getOtpFromEmail();
        otpPage.submitOtp(otp);

        successMessage = resultPage.getSuccessMessage();

        Assert.assertEquals(successMessage, "Chuyển tiền thành công");

        resultPage.closeNotification();

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
