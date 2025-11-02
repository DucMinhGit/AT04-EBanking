package Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Driver {
    private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    public static void setDriver(WebDriver webDriver) {
        drivers.set(webDriver);
    }

    public static WebDriver getDriver() {
        return drivers.get();
    }

    public static WebDriverWait getWebDriverWait() {
        return new WebDriverWait(
                getDriver(),
                Duration.ofSeconds(10)
        );
    }
}
