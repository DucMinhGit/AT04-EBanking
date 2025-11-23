package tests.Internal_Transfer;

import Utils.Driver;
import base.TestBase;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.HomePage;
import pages.AccountListingPage;
import pages.Internal_Transfer.InternalTransferPage;

import java.math.BigDecimal;

public class INT02 extends TestBase {

    @Test
    public void int02() {

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
        // Not select transferAccount to trigger validation
        transferPage.enterToAccount(receiveAccount);
        transferPage.enterAmount(new BigDecimal("1000"));
        transferPage.enterDescription("Internal Transfer");
        transferPage.clickConfirmButton();

        String errorMessage = transferPage.getTransferAccountErrorMessage();
        System.out.println("Show error message: " + errorMessage);
    }
}
