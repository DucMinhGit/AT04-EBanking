package tests.Internal_Transfer;

import Utils.Driver;
import base.TestBase;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.HomePage;
import pages.AccountListingPage;
import pages.Internal_Transfer.InternalTransferPage;

public class INT04 extends TestBase {

    @Test
    public void int04() {

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
        // Not input Amount to trigger validation
        transferPage.enterDescription("Internal Transfer");
        transferPage.clickConfirmButton();

        // Read and show error message via page object
        String errorMessage = transferPage.getAmountErrorMessage();
        System.out.println("Show error message: " + errorMessage);
    }
}
