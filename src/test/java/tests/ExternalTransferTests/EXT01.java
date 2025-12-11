package tests.ExternalTransferTests;

import base.TestBase;
import com.github.javafaker.Faker;
import datafactory.AccountFactory;
import datafactory.ExternalTransferFactory;
import models.ExternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.transfer.external.ExternalTransferPage;
import utils.Constants;
import utils.Messages;

public class EXT01 extends TestBase {
    ExternalTransferPage externalTransferPage;

    ExternalTransfer data = ExternalTransferFactory.initData();
    double currentBalance = Constants.STANDARD_TRANSFER_AMOUNT;
    double amountTransfer = (currentBalance - Constants.EXT_FEE) - 1;

    Faker faker = new Faker();
    String content = faker.lorem().sentence(10);

    @BeforeMethod
    public void init() {externalTransferPage = new ExternalTransferPage();}

    @Test(description = "Show error message when 'Account' fields blank")
    public void EXT01() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToExternalTransferPage();

        data.setFromAccountValue("");
        data.setAmount(amountTransfer);
        data.setContent(content);

        externalTransferPage.submitTransferInfo(data);

        Assert.assertEquals(externalTransferPage.getGeneralErrorMessage(), Messages.ACCOUNT_MUST_NOT_BE_EMPTY);
    }
}
