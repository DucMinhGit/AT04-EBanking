package tests.ExternalTransferTests;

import base.TestBase;
import com.github.javafaker.Faker;
import datafactory.AccountFactory;
import datafactory.ExternalTransferFactory;
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

public class EXT08 extends TestBase {
    ExternalTransferPage externalTransferPage;
    ExternalTransferConfirmationPage confirmationPage;
    ExternalTransferOtpPage otpPage;

    ExternalTransfer data = ExternalTransferFactory.initData();

    String otp;
    String wrongOtp;
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
    }

    @Test(description = "Wrong OTP blocks the External transfer")
    public void EXT08() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToExternalTransferPage();

        data.setFromAccountValue(this.currentDepositAccAnyTerm);
        data.setAmount(amountTransfer);
        data.setContent(content);

        externalTransferPage.submitTransferInfo(data);
        confirmationPage.confirm();

        otp = otpPage.getOtpFromEmail();

        wrongOtp = TransferUtils.generateInvalidOtp(otp);

        otpPage.enterOtp(wrongOtp);

        otpPage.submitTransfer();

        Assert.assertEquals(otpPage.getErrorMessage(), Messages.WRONG_OTP);
    }
}
