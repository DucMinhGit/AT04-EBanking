package tests.InternalTransferTests;

import models.InternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import utils.Constants;
import utils.Driver;
import base.TestBase;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.HomePage;
//import pages.AccountListingPage;
import pages.transfer.internal.InternalTransferPage;

public class INT04 extends TestBase {
    String username = Constants.DEFAULT_USERNAME;
    String password = Constants.DEFAULT_PASSWORD;

    InternalTransferPage internalTransferPage;
    InternalTransfer data;

    @BeforeMethod
    public void init() {
        internalTransferPage = new InternalTransferPage();
    }

    @Test
    public void INT04() {
        data = InternalTransfer.builder()
                .fromAccountValue("100001440")
                .receiverAccount("100001457")
                .content("valid")
                .amount(null)
                .build();

        String expectedErrorMessage = "Nhập nội dung";

        userLogin.login(username, password);
        homePage.clickTransfer();
        internalTransferPage.submitForm(data);

        String actualErrorMessage = internalTransferPage.getGeneralErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
}
