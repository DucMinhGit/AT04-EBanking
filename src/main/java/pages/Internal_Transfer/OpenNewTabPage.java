package pages.Internal_Transfer;

import Utils.Driver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class OpenNewTabPage {

    private final WebDriver driver = Driver.getDriver();

    public String openNewTabAndSwitch(String url) {
        // lưu handle hiện tại
        String previousHandle = driver.getWindowHandle();

        // mở tab mới và switch sang tab mới
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", url);

        // lấy handle mới (loại bỏ handle cũ)
        Set<String> handles = driver.getWindowHandles();
        handles.remove(previousHandle);
        String newHandle = handles.iterator().next();

        // switch sang tab mới
        driver.switchTo().window(newHandle);

        return previousHandle;
    }

    // Chuyển về  handle đã lưu (userHandle/originalHandle).
    public void switchToHandle(String handle) {
        driver.switchTo().window(handle);
    }
}
