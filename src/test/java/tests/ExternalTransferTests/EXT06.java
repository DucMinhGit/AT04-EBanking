package tests.ExternalTransferTests;

import base.TestBase;
import models.ExternalTransferModel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.transfer.external.ExternalTransferPage;
import utils.Constants;

public class EXT06 extends TestBase {
    String username = Constants.DEFAULT_USERNAME;
    String password = Constants.DEFAULT_PASSWORD;

    ExternalTransferPage externalTransferPage;
    ExternalTransferModel data;

    @BeforeMethod
    public void init() {
        externalTransferPage = new ExternalTransferPage();
    }

    @Test
    public void EXT06() {
        data = ExternalTransferModel.builder()
                .fromAccountValue("100001440")
                .receiverAccount("10001112")
                .receiverName("Nguyen Van A")
                .bankValue("Ngân hàng Đông Á")
                .branchValue("Chi nhánh Đà Nẵng")
                .content("Test case 3: Bo trong ten nguoi nhan")
                .amount(50000)
                .build();

        String expectedErrorMessage = "Thông tin người hưởng không đúng";

        userLogin.login(username, password);
        homePage.clickExternalTransfer();
        externalTransferPage.submitForm(data);

        String actualErrorMessage = externalTransferPage.getGeneralErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
}
