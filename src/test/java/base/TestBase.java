package base;

import Utils.Driver;
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

@Log4j2
public class TestBase {
    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser, ITestResult result) {
        log.info("===== START TEST CASE: {} =====", result.getMethod().getMethodName());

        WebDriver driver;

        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (isHeadless) {
            log.warn("The browser is running in headless mode.");
        }

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--guest");
            if (isHeadless) {
                options.addArguments("--headless");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
            }
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadless) {
                options.addArguments("--headless");
            }
            driver = new FirefoxDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            if (isHeadless) {
                options.addArguments("--headless");
                options.addArguments("--disable-gpu");
            }
            driver = new EdgeDriver(options);
        } else {
            throw new IllegalArgumentException("Not found Browser: " + browser);
        }

        Driver.setDriver(driver);

        log.info("Launching the browser and accessing the URL.");
        Driver.getDriver().get("http://14.176.232.213:8080/EBankingWebsite/");
        Driver.getDriver().manage().window().maximize();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        log.info("CLOSE BROWSER!!!");
        Driver.getDriver().quit();

        if (result.getStatus() == ITestResult.SUCCESS) {
            log.info("===== TEST CASE FINISHED: {} (PASS) =====", result.getMethod().getMethodName());
        } else if (result.getStatus() == ITestResult.FAILURE) {
            log.info("===== TEST CASE FINISHED: {} (FAIL) =====", result.getMethod().getMethodName());
        } else {
            log.info("===== TEST CASE FINISHED: {} (FAIL) =====", result.getMethod().getMethodName());
        }
    }
}
