package tests.ExternalTransferTests;

import base.TestBase;
import models.ExternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.transfer.external.ExternalTransferPage;
import utils.Constants;

public class EXT01 extends TestBase {
    String username = Constants.DEFAULT_USERNAME;
    String password = Constants.DEFAULT_PASSWORD;

    ExternalTransferPage externalTransferPage;
    ExternalTransfer data;

    @BeforeMethod
    public void init() {
        externalTransferPage = new ExternalTransferPage();
    }

    @Test
    public void EXT01() {
        data = ExternalTransfer.builder()
                .fromAccountValue("")
                .receiverAccount("10001111")
                .receiverName("Nguyen Van A")
                .bankValue("Ngân hàng Đông Á")
                .branchValue("Chi nhánh Đà Nẵng")
                .content("Test case 3: Bo trong ten nguoi nhan")
                .amount(50000.0)
                .build();

        String expectedErrorMessage = "Chọn tài khoản";

        userLogin.login(username, password);
        homePage.clickExternalTransfer();
        externalTransferPage.submitForm(data);

        String actualErrorMessage = externalTransferPage.getGeneralErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
}
