package base;

import utils.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Parameters;
import org.testng.annotations.Optional;
import org.slf4j.MDC;
import pages.HomePage;
import pages.LoginPage;
import java.io.FileReader;
import java.util.Properties;
import java.util.UUID;

@Log4j2
public class TestBase {
    protected Properties config = new Properties();
    protected String baseURL;
    protected HomePage homePage;

    public TestBase() {
        try {
            config.load(new FileReader("src/test/resources/config.properties"));
            baseURL = config.getProperty("base.url");
        } catch (Exception e) {
            log.error("Can not read file config.properties", e);
        }
    }

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser, ITestResult result) {
        String sessionId = UUID.randomUUID().toString().substring(0, 8);
        MDC.put("sessionId", sessionId);
        MDC.put("browser", browser);
        MDC.put("testName", result.getMethod().getMethodName());

        log.info("===== START TEST CASE: {} =====", result.getMethod().getMethodName());
        log.info("Session [{}] - Browser [{}]", sessionId, browser);

        WebDriver driver;

        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        if (isHeadless) {
            log.warn("The browser is running in headless mode.");
        }

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--guest");
            if (isHeadless) {
                options.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080", "--start-maximized");
            }
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadless) options.addArguments("--headless");
            driver = new FirefoxDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            if (isHeadless) options.addArguments("--headless", "--disable-gpu");
            driver = new EdgeDriver(options);
        } else {
            throw new IllegalArgumentException("Not found Browser: " + browser);
        }

        Driver.setDriver(driver);

        log.info("Launching the browser and accessing the URL: {}", baseURL);
        Driver.getDriver().get(baseURL);

        log.info("Starting automatic login execution...");
        LoginPage loginPage = new LoginPage();

        loginPage.waitForPageLoad();

        this.homePage = loginPage.login(
                config.getProperty("default.username"),
                config.getProperty("default.password")
        );

        this.homePage.waitForPageLoad();
        log.info("Login successfully!!!");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        log.info("CLOSE BROWSER!!!");
        Driver.getDriver().quit();

        String testName = MDC.get("testName");
        String status;
        if (result.getStatus() == ITestResult.SUCCESS) {
            status = "PASS";
        } else if (result.getStatus() == ITestResult.FAILURE) {
            status = "FAIL";
        } else {
            status = "SKIP";
        }

        log.info("===== TEST CASE FINISHED: {} ({}) =====", testName, status);

        MDC.clear();
    }
}
