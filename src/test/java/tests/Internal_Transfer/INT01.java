package tests.Internal_Transfer;

import Utils.Driver;
import base.TestBase;
import org.testng.annotations.Test;
import pages.*;
import pages.Internal_Transfer.*;

import java.math.BigDecimal;

public class INT01 extends TestBase {

    @Test
    public void int01() {

        // Step 1 – Navigate to web
        Driver.getDriver().get("http://14.176.232.213:8080/EBankingWebsite/");

        // Step 2 - Login Account User
        LoginPage loginPage = new LoginPage();
        loginPage.login("987656789", "ZXC1234@");

        // Step 3 – Click “Mở tài khoản”
        HomePage homePage = new HomePage();
        homePage.clickCreateAccountTab();

        // Step 4 – Create account (first)
        CreateAccountPage createPage = new CreateAccountPage();
        createPage.openAccountTypeDropdown();
        createPage.selectAccountTypeByLabel("Tài Khoản kỳ gửi không kỳ hạn");
        createPage.clickCreateAccount();
        createPage.closeModal();

        // Step 5 – Create account (second)
        homePage.clickCreateAccountTab();
        createPage.openAccountTypeDropdown();
        createPage.selectAccountTypeByLabel("Tài Khoản kỳ gửi không kỳ hạn");
        createPage.clickCreateAccount();
        createPage.closeModal();

        // Step 6 – Read last two accounts
        AccountListingPage listing = new AccountListingPage();
        String transferAccount = listing.getAccountA();
        String receiveAccount = listing.getAccountB();

        // Step 7 – Open admin page in new tab
        OpenNewTabPage opener = new OpenNewTabPage();
        String userHandle = opener.openNewTabAndSwitch("http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml");

        // Step 8 – Login Account Admin
        LoginDepositPage adminLogin = new LoginDepositPage();
        adminLogin.login("1", "admin");

        // Step 9 – Deposit money to Account A
        HomeDepositPage depositPage = new HomeDepositPage();
        depositPage.clickDepositTab();
        depositPage.depositToAccount(transferAccount, new BigDecimal("1000000000"), "Deposit Account");

        // Step 10 - Close Tab
        Driver.getDriver().close();

        // Step 11 – Back to user tab
        opener.switchToHandle(userHandle);

        // Step 12 – Log out
        homePage.clickLogout();

        // Step 13 – Login again with same credentials as Step 2
        LoginPage loginAgain = new LoginPage();
        loginAgain.login("987656789", "ZXC1234@");

        // Step 14 – Click “Chuyển khoản”
        InternalTransferPage transferPage = homePage.clickTransferTab();

        // Step 15 – elect the source account in dropdown by account number (Account A)
        transferPage.selectSourceAccount(transferAccount);

        // Step 16 – Enter account B
        transferPage.enterToAccount(receiveAccount);

        // Step 17 – Amount
        transferPage.enterAmount(new BigDecimal("1000"));

        // Step 18 – Description
        transferPage.enterDescription("Internal Transfer");
        transferPage.clickConfirmButton();

        // Step 19 - Click confirm on ConfirmTransferPage
        ConfirmTransferPage confirmPage = new ConfirmTransferPage();
        confirmPage.clickConfirm();

        // Step 20 - Open a new tab and navigate to YOPMail
        String originalHandle = opener.openNewTabAndSwitch("https://yopmail.com/wm");

        // Step 21 - Enter inbox and click go
        YopMailPage yop = new YopMailPage();
        yop.enterInbox("ebanking");
        yop.clickGo();

        // Step 22 - Get OTP
        String otp = yop.getOtpCode();
        System.out.println("OTP is: " + otp);

        // Step 23 - Back to user tab
        opener.switchToHandle(originalHandle);

        // Step 24 - Input and submit
        OTPPage otpPage = new OTPPage();
        otpPage.enterOtp(otp);
        otpPage.clickTransfer();

        // Step 25 - Show success modal
        transferPage.showSuccessModal();
    }
}
