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
//import pages.AccountListingPage;
import pages.transfer.internal.InternalTransferPage;

import java.math.BigDecimal;

public class INT03 extends TestBase {
    String username = Constants.DEFAULT_USERNAME;
    String password = Constants.DEFAULT_PASSWORD;

    InternalTransferPage internalTransferPage;
    InternalTransfer data;

    @BeforeMethod
    public void init() {
        internalTransferPage = new InternalTransferPage();
    }

    @Test
    public void INT03() {
        data = InternalTransfer.builder()
                .fromAccountValue("100001440")
                .receiverAccount("")
                .content("valid")
                .amount(5000.0)
                .build();

        String expectedErrorMessage = "Tài khoản không hợp lệ, quý khách vui lòng kiểm tra lại.";

        userLogin.login(username, password);
        homePage.clickTransfer();
        internalTransferPage.submitForm(data);

        String actualErrorMessage = internalTransferPage.getGeneralErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
}
