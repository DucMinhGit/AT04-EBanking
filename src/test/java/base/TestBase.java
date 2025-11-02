package base;

import Utils.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase {
    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--guest");

        WebDriver driver = new ChromeDriver();

        Driver.setDriver(driver);

        Driver.getDriver().get("http://14.176.232.213:8080/EBankingWebsite/");

        Driver.getDriver().manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        Driver.getDriver().quit();
    }
}
