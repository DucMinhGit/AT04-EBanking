package tests;

import base.TestBase;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import models.ExternalTransferModel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.transfer.external.ExternalTransferPage;
import pages.transfer.external.ExternalTransferConfirmationPage;
import pages.transfer.external.ExternalTransferOtpPage;
import pages.transfer.external.ExternalTransferResultPage;
import testdata.ValidationTestCase;
import utils.DataProviders;
import com.mailosaur.MailosaurException;
import java.io.IOException;

@Log4j2
public class ExternalTransferTests extends TestBase
{
    ExternalTransferPage externalTransferPage;
    ExternalTransferConfirmationPage confirmationPage;
    ExternalTransferOtpPage otpPage;
    ExternalTransferResultPage resultPage;
    LoginPage loginPage;
    HomePage homePage;

    @BeforeMethod
    public void init() {
        loginPage = new LoginPage();
        homePage = new HomePage();
        externalTransferPage = new ExternalTransferPage();
        confirmationPage = new ExternalTransferConfirmationPage();
        otpPage = new ExternalTransferOtpPage();
        resultPage = new ExternalTransferResultPage();
    }

    @Test
    public void EXT01() {
        ExternalTransferModel data = ExternalTransferModel.builder()
                .fromAccountValue("")
                .receiverAccount("987654321")
                .receiverName("NGUYEN VAN A")
                .bankValue("100001")
                .branchValue("1")
                .content("Test case 1: Bo trong TK nguon")
                .amount("50000")
                .build();

        String expectedErrorMessage = "Chọn tài khoản";

        loginPage.login(config.getProperty("default.username"), config.getProperty("default.password"));
        homePage.clickExternalTransfer();
        externalTransferPage.submitForm(data);

        String actualErrorMessage = externalTransferPage.getGeneralErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void EXT02() {
        ExternalTransferModel data = ExternalTransferModel.builder()
                .fromAccountValue("100001440")
                .receiverAccount("0")
                .receiverName("Nguyen Van A")
                .bankValue("100001")
                .branchValue("1")
                .content("Test case 3: Bo trong ten nguoi nhan")
                .amount("50000")
                .build();

        String expectedErrorMessage = "Số tài khoản nhận : Validation Error: Length is less than allowable minimum of '5'";

        loginPage.login(config.getProperty("default.username"), config.getProperty("default.password"));
        homePage.clickExternalTransfer();
        externalTransferPage.submitForm(data);

        String actualErrorMessage = externalTransferPage.getGeneralErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void EXT03() {
        ExternalTransferModel data = ExternalTransferModel.builder()
                .fromAccountValue("100001440")
                .receiverAccount("10001111")
                .receiverName("")
                .bankValue("100001")
                .branchValue("1")
                .content("Test case 3: Bo trong ten nguoi nhan")
                .amount("50000")
                .build();

        String expectedErrorMessage = "Nhập tên người nhận";

        loginPage.login(config.getProperty("default.username"), config.getProperty("default.password"));
        homePage.clickExternalTransfer();
        externalTransferPage.submitForm(data);

        String actualErrorMessage = externalTransferPage.getGeneralErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void EXT04() {
        ExternalTransferModel data = ExternalTransferModel.builder()
                .fromAccountValue("100001440")
                .receiverAccount("10001111")
                .receiverName("NGUYEN VAN A")
                .bankValue("")
                .branchValue("1")
                .content("Test case 4: Bo trong ngan hang")
                .amount("50000")
                .build();

        String expectedErrorMessage = "Mời chọn Ngân hàng";

        loginPage.login(config.getProperty("default.username"), config.getProperty("default.password"));
        homePage.clickExternalTransfer();
        externalTransferPage.submitForm(data);

        String actualErrorMessage = externalTransferPage.getGeneralErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void EXT05() {
        ExternalTransferModel data = ExternalTransferModel.builder()
                .fromAccountValue("100001440")
                .receiverAccount("10001111")
                .receiverName("NGUYEN VAN A")
                .bankValue("100001")
                .branchValue("") // TRƯỜNG CẦN TEST
                .content("Test case 5: Bo trong chi nhanh")
                .amount("50000")
                .build();

        String expectedErrorMessage = "Mời chọn chi nhánh";

        loginPage.login(config.getProperty("default.username"), config.getProperty("default.password"));
        homePage.clickExternalTransfer();
        externalTransferPage.submitForm(data);

        String actualErrorMessage = externalTransferPage.getGeneralErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

//    @Test
//    public void EXT06() {
//        ExternalTransferModel data = ExternalTransferModel.builder()
//                .fromAccountValue("100001440")
//                .receiverAccount("100001440") // TRƯỜNG CẦN TEST
//                .receiverName("NGUYEN VAN A")
//                .bankValue("100001")
//                .branchValue("1")
//                .content("Test case 7: Trung tai khoan")
//                .amount("1000")
//                .build();
//
//        String expectedErrorMessage = "Thông tin người hưởng không đúng";
//
//        loginPage.login(config.getProperty("default.username"), config.getProperty("default.password"));
//        homePage.clickExternalTransfer();
//        externalTransferPage.submitForm(data);
//
//        String actualErrorMessage = externalTransferPage.getGeneralErrorMessage();
//        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
//    }

    @Test
    public void EXT07() {
        String fromAccountValue = "100001440";
        String expectedErrorMessage = "Số tiền quá qui định";

        loginPage.login(config.getProperty("default.username"), config.getProperty("default.password"));
        homePage.clickExternalTransfer();

        externalTransferPage.selectAccount(fromAccountValue);

        String balanceStr = externalTransferPage.getAvailableBalance();
        log.info("Số dư khả dụng tìm thấy: {}", balanceStr);

        String parsedBalance = balanceStr.replaceAll("[^0-9]", "");
        long currentBalance = Long.parseLong(parsedBalance);
        long amountToTransfer = currentBalance + 10000;

        ExternalTransferModel testData = ExternalTransferModel.builder()
//                 .fromAccountValue("")
                .receiverAccount("987654321")
                .receiverName("NGUYEN VAN B")
                .bankValue("100001")
                .branchValue("1")
                .content("Test khong du so du")
                .amount(String.valueOf(amountToTransfer))
                .build();

        externalTransferPage.fillFormDetails(testData);
        externalTransferPage.clickSubmit();

        String actualErrorMessage = externalTransferPage.getGeneralErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void EXT08() {
        Faker faker = new Faker();
        String wrongOtp = faker.number().digits(6);
        String expectedErrorMessage = "Sai mã OTP";

        ExternalTransferModel data = ExternalTransferModel.builder()
                .fromAccountValue("100001440")
                .receiverAccount("10001111")
                .receiverName("NGUYEN VAN A")
                .bankValue("100001")
                .branchValue("1")
                .content("Test sai OTP")
                .amount("50000")
                .build();

        loginPage.login(config.getProperty("default.username"), config.getProperty("default.password"));
        homePage.clickExternalTransfer();
        externalTransferPage.submitForm(data);
        confirmationPage.clickConfirm();
        otpPage.submitOtp(wrongOtp);

        String actualErrorMessage = otpPage.getErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test(dataProvider = "externalTransferDataFromJson", dataProviderClass = DataProviders.class)
    @Step("Test: ExternalTransfer Successfully")
    public void EXT09(ExternalTransferModel inputData) throws IOException, MailosaurException {
        loginPage.login(config.getProperty("default.username"), config.getProperty("default.password"));
        homePage.clickExternalTransfer();
        externalTransferPage.submitForm(inputData);

        ExternalTransferModel previewData = confirmationPage.getConfirmationDetailsAsModel();

        Assert.assertEquals(previewData.getReceiverAccount(), inputData.getReceiverAccount(), "Wrong account received");
        Assert.assertEquals(previewData.getAmount(), inputData.getAmount(), "Wrong amount");
        Assert.assertEquals(previewData.getContent(), inputData.getContent(), "Wrong content");
        Assert.assertEquals(previewData.getReceiverName(), inputData.getReceiverName(), "Wrong name received");

        confirmationPage.clickConfirm();

        long searchSince = System.currentTimeMillis();
        String otpReceiverEmail = config.getProperty("otp.receiver.email");
        otpPage.submitOtpFromEmail(otpReceiverEmail, searchSince);

        String successMessage = resultPage.getSuccessMessage();
        Assert.assertEquals(successMessage,"Chuyển tiền thành công");
    }
}