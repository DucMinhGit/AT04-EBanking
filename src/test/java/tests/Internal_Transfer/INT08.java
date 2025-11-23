package tests.Internal_Transfer;

import Utils.Driver;
import base.TestBase;
import org.testng.annotations.Test;
import pages.Internal_Transfer.ConfirmTransferPage;
import pages.Internal_Transfer.OTPPage;
import pages.LoginPage;
import pages.HomePage;
import pages.AccountListingPage;
import pages.Internal_Transfer.InternalTransferPage;

import java.math.BigDecimal;

public class INT08 extends TestBase {

    @Test
    public void int08() {

        Driver.getDriver().get("http://14.176.232.213:8080/EBankingWebsite/");

        LoginPage loginPage = new LoginPage();
        loginPage.login("987656789", "ZXC1234@");

        // read last accounts from listing
        AccountListingPage listing = new AccountListingPage();
        String transferAccount = listing.getAccountA();
        String receiveAccount = listing.getAccountB();

        HomePage homePage = new HomePage();
        InternalTransferPage transferPage = homePage.clickTransferTab();

        // Fill remaining fields and confirm
        transferPage.selectSourceAccount(transferAccount);
        transferPage.enterToAccount(receiveAccount);
        transferPage.enterAmount(new BigDecimal("1000"));
        transferPage.enterDescription("Internal Transfer");
        transferPage.clickConfirmButton();

        // Confirm transfer
        ConfirmTransferPage confirmPage = new ConfirmTransferPage();
        confirmPage.clickConfirm();

        // On OTPPage, enter Invalid OTP and submit
        OTPPage otpPage = new OTPPage();
        otpPage.enterOtp("ABCD1234");
        otpPage.clickTransfer();

        // Capture error message and print to console
        String invalidMessage = otpPage.invalidOTP();
        System.out.println("Show error message: " + invalidMessage);
    }
}