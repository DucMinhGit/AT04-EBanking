package tests.Internal_Transfer;

import Utils.Driver;
import base.TestBase;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.HomePage;
import pages.AccountListingPage;
import pages.Internal_Transfer.InternalTransferPage;

import java.math.BigDecimal;

public class INT07 extends TestBase {

    @Test
    public void int07() {

        Driver.getDriver().get("http://14.176.232.213:8080/EBankingWebsite/");

        LoginPage loginPage = new LoginPage();
        loginPage.login("987656789", "ZXC1234@");

        // read last accounts from listing
        AccountListingPage listing = new AccountListingPage();
        String transferAccount = listing.getAccountA();
        String receiveAccount = listing.getAccountB();

        // navigate to transfer page
        HomePage homePage = new HomePage();
        InternalTransferPage transferPage = homePage.clickTransferTab();

        // Select transferAccount so the available balance is shown on the page
        transferPage.selectSourceAccount(transferAccount);

        // enter receiveAccount
        transferPage.enterToAccount(receiveAccount);

        // read actual balance shown on page
        BigDecimal balance = transferPage.getAvailableBalance();

        // fee = 1500, ensure amount > balance + fee
        BigDecimal fee = new BigDecimal("1500");
        BigDecimal amountToTransfer = balance.add(fee).add(BigDecimal.ONE);

        // enter computed amount
        transferPage.enterAmount(amountToTransfer);

        // enter description and confirm
        transferPage.enterDescription("Internal Transfer");
        transferPage.clickConfirmButton();

        // Capture error message and print to console
        String invalidMessage = transferPage.getInvalidAmountMessage();
        System.out.println("Show error message: " + invalidMessage);
    }
}
