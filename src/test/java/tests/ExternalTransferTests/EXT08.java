package tests.ExternalTransferTests;

import base.TestBase;
import com.github.javafaker.Faker;
import com.mailosaur.MailosaurException;
import models.ExternalTransferModel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.transfer.external.ExternalTransferConfirmationPage;
import pages.transfer.external.ExternalTransferOtpPage;
import pages.transfer.external.ExternalTransferPage;
import utils.Constants;

import java.io.IOException;

public class EXT08 extends TestBase {
    String username = Constants.DEFAULT_USERNAME;
    String password = Constants.DEFAULT_PASSWORD;

    ExternalTransferPage externalTransferPage;
    ExternalTransferConfirmationPage confirmationPage;
    ExternalTransferOtpPage otpPage;

    ExternalTransferModel data;
    String otp;
    String wrongOtp;
    String actualErrorMessage;
    Faker faker;
    String expectedErrorMessage;
    double currentBalance;
    double fee;
    double maxTransferable;
    double transferAmount;

    @BeforeMethod
    public void init() {
        prepareAccountData(username, password, 5000);

        externalTransferPage = new ExternalTransferPage();
        confirmationPage = new ExternalTransferConfirmationPage();
        otpPage = new ExternalTransferOtpPage();
    }

    @Test
    public void EXT08() throws IOException, MailosaurException {
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
        confirmationPage.clickConfirm();

        faker = new Faker();
        expectedErrorMessage = "Sai mã OTP";

        otp = otpPage.getOtpFromEmail();
        wrongOtp = otp + faker.number().digits(3);
        otpPage.submitOtp(wrongOtp);

        actualErrorMessage = otpPage.getErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
}
