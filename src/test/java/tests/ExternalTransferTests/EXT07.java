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

public class EXT07 extends TestBase {
    ExternalTransferPage externalTransferPage;

    ExternalTransfer data = ExternalTransferFactory.initData();
    double amountToTransfer = Constants.STANDARD_TRANSFER_AMOUNT + 1;;
    Faker faker = new Faker();
    String content = faker.lorem().sentence(10);

    @BeforeMethod
    public void init() {
        setupUserWithBalance(AccountFactory.userDefault(), Constants.STANDARD_TRANSFER_AMOUNT);
        externalTransferPage = new ExternalTransferPage();
    }

    @Test(description = "Reject transfers with insufficient funds")
    public void EXT07() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToExternalTransferPage();

        data.setFromAccountValue(this.currentDepositAccAnyTerm);
        data.setAmount(amountToTransfer);
        data.setContent(content);

        externalTransferPage.submitTransferInfo(data);

        externalTransferPage.clickSubmit();

        Assert.assertEquals(externalTransferPage.getGeneralErrorMessage(), Messages.OVERDUE_AMOUNT);
    }
}
