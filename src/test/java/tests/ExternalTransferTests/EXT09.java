package tests.ExternalTransferTests;

import base.TestBase;
import com.mailosaur.MailosaurException;
import datafactory.AccountFactory;
import datafactory.ExternalTransferFactory;
import lombok.extern.log4j.Log4j2;
import models.ExternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AccountDetailPage;
import pages.transfer.external.*;
import utils.Configs;
import utils.Constants;
import utils.Messages;
import utils.TransferUtils;

import java.io.IOException;

@Log4j2
public class EXT09 extends TestBase {
    ExternalTransferPage externalTransferPage;
    ExternalTransferConfirmationPage confirmationPage;
    ExternalTransferOtpPage otpPage;
    AccountDetailPage accountDetailPage;

    ExternalTransfer data;
    ExternalTransfer previewData;

    String otp;
    double actualEndingBalance;
    double expectedEndingBalance;

    String receiverAccount = "10001111";
    String bank = "Ngân hàng Đông Á";
    String branch = "Chi nhánh Đà Nẵng";
    String receiverName = "Nguyen Van A";
    double currentBalance = Constants.STANDARD_TRANSFER_AMOUNT;
    double amountTransfer = (currentBalance - Constants.EXT_FEE) - 1;
    String content = "TCS 09";

    @BeforeMethod
    public void init() {
        setupUserWithBalance(AccountFactory.userDefault(), Constants.STANDARD_TRANSFER_AMOUNT);

        externalTransferPage = new ExternalTransferPage();
        confirmationPage = new ExternalTransferConfirmationPage();
        otpPage = new ExternalTransferOtpPage();
        accountDetailPage = new AccountDetailPage();
    }

    @Test(description = "Successful External transfer")
    public void EXT09() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToExternalTransferPage();

        data = ExternalTransfer.builder()
                .fromAccountValue(this.currentDepositAcctAnyTerm)
                .receiverAccount(receiverAccount)
                .receiverName(receiverName)
                .bankValue(bank)
                .branchValue(branch)
                .amount(amountTransfer)
                .content(content)
                .build();

        externalTransferPage.submitForm(data);

        previewData = confirmationPage.getConfirmationDetailsAsModel();

        Assert.assertEquals(previewData.getReceiverAccount(), data.getReceiverAccount());
        Assert.assertEquals(previewData.getAmount(), data.getAmount());
        Assert.assertEquals(previewData.getContent(), data.getContent());
        Assert.assertEquals(previewData.getReceiverName(), data.getReceiverName());

        confirmationPage.clickConfirm();

        otp = otpPage.getOtpFromEmail();

        otpPage.submitOtp(otp);

        Assert.assertEquals(bankAccountPage.getSuccessMessage(), Messages.MONEY_TRANSFER_SUCCESSFUL);

        bankAccountPage.closeDialogMessage();

        Assert.assertEquals(bankAccountPage.getLatestTransactionAmount(1), data.getAmount());

        bankAccountPage.viewAccountDetail(this.currentDepositAcctAnyTerm);

        actualEndingBalance = accountDetailPage.getBalance();
        expectedEndingBalance = TransferUtils.calcExpectedBalanceExternal(currentBalance, data.getAmount());

        Assert.assertEquals(actualEndingBalance, expectedEndingBalance);
    }
}
