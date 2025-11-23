package tests.ExternalTransferTests;

import base.TestBase;
import models.ExternalTransferModel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.transfer.external.ExternalTransferPage;
import utils.Constants;

public class EXT07 extends TestBase {
    String username = Constants.DEFAULT_USERNAME;
    String password = Constants.DEFAULT_PASSWORD;

    ExternalTransferPage externalTransferPage;
    ExternalTransferModel data;

    @BeforeMethod
    public void init() {
        prepareAccountData(username, password, 5000);

        externalTransferPage = new ExternalTransferPage();
    }

    @Test
    public void EXT07() {
        String expectedErrorMessage = "Số tiền quá qui định";

        userLogin.login(username, password);
        homePage.clickExternalTransfer();

        externalTransferPage.selectAccount(this.currentDepositAcctAnyTerm);

        double balance = externalTransferPage.getAvailableBalance();

        double amountToTransfer = balance + 3300 + 1;

        data = ExternalTransferModel.builder()
                .fromAccountValue("100001440")
                .receiverAccount("10001111")
                .receiverName("Nguyen Van A")
                .bankValue("Ngân hàng Đông Á")
                .branchValue("Chi nhánh Đà Nẵng")
                .content("Test case 3: Bo trong ten nguoi nhan")
                .amount(50000)
                .build();

        data.setAmount(amountToTransfer);

        externalTransferPage.fillFormDetails(data);
        externalTransferPage.clickSubmit();

        String actualErrorMessage = externalTransferPage.getGeneralErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

}
