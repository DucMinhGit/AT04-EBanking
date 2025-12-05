package tests.ExternalTransferTests;

import base.TestBase;
import com.github.javafaker.Faker;
import datafactory.AccountFactory;
import datafactory.ExternalTransferFactory;
import lombok.extern.log4j.Log4j2;
import models.ExternalTransfer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AccountDetailPage;
import pages.transfer.external.*;
import utils.Constants;
import utils.Messages;
import utils.TransferUtils;

@Log4j2
public class EXT09 extends TestBase {
    ExternalTransferPage externalTransferPage;
    ExternalTransferConfirmationPage confirmationPage;
    ExternalTransferOtpPage otpPage;
    AccountDetailPage accountDetailPage;

    ExternalTransfer previewData;

    String otp;
    double actualEndingBalance;
    double expectedEndingBalance;

    ExternalTransfer data = ExternalTransferFactory.initData();
    double currentBalance = Constants.STANDARD_TRANSFER_AMOUNT;
    double amountTransfer = (currentBalance - Constants.EXT_FEE) - 1;

    Faker faker = new Faker();
    String content = faker.lorem().sentence(10);

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

        data.setFromAccountValue(this.currentDepositAcctAnyTerm);
        data.setAmount(amountTransfer);
        data.setContent(content);

        externalTransferPage.submitTransferInfo(data);

        previewData = confirmationPage.getConfirmationDetailsAsModel();

        SoftAssert sa = new SoftAssert();
        sa.assertEquals(previewData.getReceiverAccount(), data.getReceiverAccount());
        sa.assertEquals(previewData.getAmount(), data.getAmount());
        sa.assertEquals(previewData.getContent(), data.getContent());
        sa.assertEquals(previewData.getReceiverName(), data.getReceiverName());

        confirmationPage.confirm();

        otp = otpPage.getOtpFromEmail();

        otpPage.enterOtp(otp);

        otpPage.submitTransfer();

        sa.assertEquals(bankAccountPage.getSuccessMessage(), Messages.MONEY_TRANSFER_SUCCESSFUL);

        bankAccountPage.closeDialogMessage();

        sa.assertEquals(bankAccountPage.getLatestTransactionAmount(1), data.getAmount());

        bankAccountPage.viewAccountDetail(this.currentDepositAcctAnyTerm);

        actualEndingBalance = accountDetailPage.getBalance();
        expectedEndingBalance = TransferUtils.calcExpectedBalanceExternal(currentBalance, data.getAmount());

        sa.assertEquals(actualEndingBalance, expectedEndingBalance);
        sa.assertAll();
    }
}
