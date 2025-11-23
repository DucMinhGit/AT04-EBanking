package pages.components;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Log4j2
public class HeaderComponent {

    protected WebDriver driver;
    protected WebDriverWait wait;

    private final By logoImage = By.cssSelector("p.logo_pad img[src*='logo30.png']");
    private final By homeLink = By.xpath("//div[contains(@class, 'nav_wrap')]//a[contains(@href, 'ebankingweb.ddns.net')]");
    private final By userTextLink = By.xpath("//div[contains(@class, 'nav_wrap')]//ul/li[last()]/a");

    public HeaderComponent(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void clickLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(logoImage)).click();
    }

    public String getUserName() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(userTextLink)).getText();
        return text.trim();
    }

    public void waitForComponentLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoImage));
    }
}