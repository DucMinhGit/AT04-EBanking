package base;

import datafactory.AccountFactory;
import io.qameta.allure.Step;
import models.Account;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.*;
import pages.admin.AdminDepositPage;
import pages.admin.AdminLoginPage;
import utils.Configs;
import utils.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import lombok.extern.log4j.Log4j2;
import java.io.File;

@Log4j2
public class TestBase {
    WebDriver driver;

    protected String baseURL = Configs.BASE_URL;
    protected String currentDepositAcctAnyTerm;
    protected String currentSavingAccount;
    protected LoginPage userLoginPage;
    protected HomePage homePage;

    protected BankAccountPage bankAccountPage;
    protected CreateAccountPage createAccountPage;
    protected AdminLoginPage adminLoginPage;
    protected AdminDepositPage adminDepositPage;
    protected AccountDetailPage accountDetailPage;

    @BeforeMethod
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--guest");
        File uBlock = new File("extensions/uBlock.crx");
        chromeOptions.addExtensions(uBlock);

        driver = new ChromeDriver(chromeOptions);
        Driver.setDriver(driver);

        Driver.getDriver().get(baseURL);

        userLoginPage = new LoginPage();
        homePage = new HomePage();

        bankAccountPage = new BankAccountPage();
        createAccountPage = new CreateAccountPage();
        adminLoginPage = new AdminLoginPage();
        adminDepositPage = new AdminDepositPage();
        accountDetailPage = new AccountDetailPage();
    }

    @Step("PRECONDITION: Create Accounts & Deposit Money")
    protected void setupUserWithBalance(Account account, double amount) {
        userLoginPage.login(account);

        homePage.goToCreateAccountPage();
        createAccountPage.createSavingAccount();
        bankAccountPage.closeDialogMessage();
        this.currentSavingAccount = bankAccountPage.getNewestAccountNumber("last()");

        homePage.goToCreateAccountPage();
        createAccountPage.createDepositAcctAnyTerm();
        bankAccountPage.closeDialogMessage();
        this.currentDepositAcctAnyTerm = bankAccountPage.getNewestAccountNumber("last()");

        homePage.logout();

        adminLoginPage.login(AccountFactory.adminDefault());
        adminDepositPage.goToDepositPage();
        adminDepositPage.depositMoney(this.currentDepositAcctAnyTerm, amount, "Cap tien test tu dong");
        adminDepositPage.logout();

        Driver.getDriver().get(baseURL);
    }

    @AfterMethod
    public void tearDown() {
        Driver.getDriver().quit();
    }
}