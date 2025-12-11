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

public class EXT02 extends TestBase {
    ExternalTransferPage externalTransferPage;

    ExternalTransfer data = ExternalTransferFactory.initData();
    double currentBalance = Constants.STANDARD_TRANSFER_AMOUNT;
    double amountTransfer = (currentBalance - Constants.EXT_FEE) - 1;

    Faker faker = new Faker();
    String content = faker.lorem().sentence(10);

    @BeforeMethod
    public void init() {
        setupUserWithBalance(AccountFactory.userDefault(), Constants.STANDARD_TRANSFER_AMOUNT);

        externalTransferPage = new ExternalTransferPage();
    }

    @Test(description = "Show error message when 'Receiving Account Number' fields blank")
    public void EXT02() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToExternalTransferPage();

        data.setFromAccountValue(this.currentDepositAccAnyTerm);
        data.setReceiverAccount("");
        data.setAmount(amountTransfer);
        data.setContent(content);

        externalTransferPage.submitTransferInfo(data);

        Assert.assertEquals(externalTransferPage.getGeneralErrorMessage(), Messages.MIN_ACCOUNT_NUMBER);
    }
}
