package pages.components;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Log4j2
public class FooterComponent {

    protected WebDriver driver;
    protected WebDriverWait wait;

    private final By footerRoot = By.id("bottom");
    private final By copyrightText = By.cssSelector("div.copy_wrap");

    public FooterComponent(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public String getCopyrightText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(copyrightText)).getText();
    }

    public void waitForComponentLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(footerRoot));
    }
}