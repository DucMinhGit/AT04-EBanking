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

import java.math.BigDecimal;

public class INT06 extends TestBase {
    String username = Constants.DEFAULT_USERNAME;
    String password = Constants.DEFAULT_PASSWORD;

    InternalTransferPage internalTransferPage;
    InternalTransfer data;

    @BeforeMethod
    public void init() {
        internalTransferPage = new InternalTransferPage();
    }

    @Test
    public void INT06() {
        data = InternalTransfer.builder()
                .fromAccountValue("100001440")
                .receiverAccount("100001440")
                .content("valid")
                .amount(500.0)
                .build();

        String expectedErrorMessage = "Tài khoản không hợp lệ, quý khách vui lòng kiểm tra lại.";

        userLogin.login(username, password);
        homePage.clickTransfer();
        internalTransferPage.submitForm(data);

        String actualErrorMessage = internalTransferPage.getGeneralErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
}
