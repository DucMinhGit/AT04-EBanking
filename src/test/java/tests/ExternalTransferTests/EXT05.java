package tests.ExternalTransferTests;

import base.TestBase;
import datafactory.AccountFactory;
import datafactory.ExternalTransferFactory;
import models.ExternalTransfer;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.transfer.external.ExternalTransferPage;
import utils.Constants;
import utils.Messages;

public class EXT05 extends TestBase {
    ExternalTransferPage externalTransferPage;
    ExternalTransfer data;

    @BeforeMethod
    public void init() {
        externalTransferPage = new ExternalTransferPage();
    }

    @Test(description = "Show error message when 'Branch' fields blank")
    public void EXT05() {
        userLoginPage.login(AccountFactory.userDefault());

        homePage.goToExternalTransferPage();

        data = ExternalTransferFactory.initData();
        data.setBranchValue("");

        externalTransferPage.submitTransferInfo(data);

        Assert.assertEquals(externalTransferPage.getGeneralErrorMessage(), Messages.BRANCH_MUST_NOT_BE_EMPTY);
    }
}
