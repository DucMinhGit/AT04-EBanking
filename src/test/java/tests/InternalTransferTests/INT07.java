package tests.InternalTransferTests;

import models.ExternalTransfer;
import models.InternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import utils.Constants;
import utils.Driver;
import base.TestBase;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.HomePage;
import pages.transfer.internal.InternalTransferPage;

import java.math.BigDecimal;

public class INT07 extends TestBase {
    String username = Constants.DEFAULT_USERNAME;
    String password = Constants.DEFAULT_PASSWORD;

    InternalTransferPage internalTransferPage;
    InternalTransfer data;
    String expectedErrorMessage;

    @BeforeMethod
    public void init() {
        prepareAccountData(username, password, 5000);

        internalTransferPage = new InternalTransferPage();
    }

    @Test
    public void INT07() {
        userLogin.login(username, password);
        homePage.clickTransfer();
        internalTransferPage.selectAccount(this.currentDepositAcctAnyTerm);

        double balance = internalTransferPage.getAvailableBalance();

        double amountToTransfer = balance + 1100 + 1;

        data = ExternalTransfer.builder()
//                .fromAccountValue("100001440")
                .receiverAccount(this.currentSavingAccount)
                .content("Test valid")
                .amount(amountToTransfer)
                .build();

        data.setAmount(amountToTransfer);

        internalTransferPage.fillFormDetails(data);
        internalTransferPage.clickSubmit();

        expectedErrorMessage = "Số tiền vượt mức";
        String actualErrorMessage = internalTransferPage.getGeneralErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
}
