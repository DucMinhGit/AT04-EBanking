package pages.transfer.internal;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import utils.Driver;

import java.util.Set;

public class OpenNewTabPage {

    private final WebDriver driver = utils.Driver.getDriver();

    public String openNewTabAndSwitch(String url) {
        String previousHandle = driver.getWindowHandle();
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", url);

        Set<String> handles = driver.getWindowHandles();
        handles.remove(previousHandle);
        String newHandle = handles.iterator().next();

        driver.switchTo().window(newHandle);

        return previousHandle;
    }

    public void switchToHandle(String handle) {
        driver.switchTo().window(handle);
    }
}
