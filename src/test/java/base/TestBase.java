package base;

import io.qameta.allure.Step;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.*;
import pages.admin.AdminDepositPage;
import pages.admin.AdminLoginPage;
import utils.BrowserFactory;
import utils.Constants;
import utils.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TestBase {
    WebDriver driver;

    protected String baseURL = Constants.BASE_URL;
    protected String currentDepositAcctAnyTerm;
    protected String currentSavingAccount;
    protected LoginPage userLogin;
    protected HomePage homePage;

    protected BankAccountPage bankPage;
    protected CreateAccountPage createPage;
    protected AdminLoginPage adminLogin;
    protected AdminDepositPage adminDeposit;
    protected AccountDetailPage accountDetailPage;

    @BeforeMethod
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--guest");
        driver = new ChromeDriver(chromeOptions);
        Driver.setDriver(driver);

        Driver.getDriver().get(baseURL);

        userLogin = new LoginPage();
        homePage = new HomePage();

        bankPage = new BankAccountPage();
        createPage = new CreateAccountPage();
        adminLogin = new AdminLoginPage();
        adminDeposit = new AdminDepositPage();
        accountDetailPage = new AccountDetailPage();
    }

    @Step("PRECONDITION: Create Accounts & Deposit Money")
    protected void prepareAccountData(String username, String password, double amount) {
        userLogin.login(username, password);

        homePage.clickCreateAccount();
        createPage.createSavingAccount();
        bankPage.closeNotification();

        homePage.clickCreateAccount();
        createPage.createDepositAcctAnyTerm();
        bankPage.closeNotification();

        this.currentDepositAcctAnyTerm = bankPage.getAccountNumber("Tài Khoản", "last()");
        this.currentSavingAccount = bankPage.getAccountNumber("Tài Khoản", "last() - 1");

        homePage.clickLogout();

        adminLogin.login("1", "admin");
        adminDeposit.goToDepositPage();
        adminDeposit.depositMoney(this.currentDepositAcctAnyTerm, amount, "Cap tien test tu dong");
        adminDeposit.logout();

        Driver.getDriver().get(baseURL);
    }

    @AfterMethod
    public void tearDown() {
        Driver.getDriver().quit();
    }
}