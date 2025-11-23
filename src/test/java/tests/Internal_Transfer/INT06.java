package tests.Internal_Transfer;

import Utils.Driver;
import base.TestBase;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.HomePage;
import pages.AccountListingPage;
import pages.Internal_Transfer.InternalTransferPage;

import java.math.BigDecimal;

public class INT06 extends TestBase {

    @Test
    public void int06() {

        Driver.getDriver().get("http://14.176.232.213:8080/EBankingWebsite/");

        LoginPage loginPage = new LoginPage();
        loginPage.login("987656789", "ZXC1234@");

        // read last accounts from listing
        AccountListingPage listing = new AccountListingPage();
        String transferAccount = listing.getAccountA();
        String invalidReceiveAccount = "1234567890";

        HomePage homePage = new HomePage();
        InternalTransferPage transferPage = homePage.clickTransferTab();

        // Fill remaining fields and confirm
        transferPage.selectSourceAccount(transferAccount);
        transferPage.enterToAccount(invalidReceiveAccount);
        transferPage.enterAmount(new BigDecimal("1000"));
        transferPage.enterDescription("Internal Transfer");
        transferPage.clickConfirmButton();

        // Read and show error message via page object
        String errorMessage = transferPage.getInvalidReceiveAccountMessage();
        System.out.println("Show error message: " + errorMessage);
    }
}
