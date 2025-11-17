package pages;

import utils.Driver;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.components.FooterComponent;
import pages.components.HeaderComponent;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @Getter
    protected HeaderComponent header;
    @Getter
    protected FooterComponent footer;

    public BasePage() {
        this.driver = Driver.getDriver();
        this.wait = Driver.getWebDriverWait();

        this.header = new HeaderComponent(this.driver, this.wait);
        this.footer = new FooterComponent(this.driver, this.wait);
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        Driver.getDriver().findElement(locator).sendKeys(text);
    }

    protected String getVisibleText(By locator) {
        return Driver.getDriver().findElement(locator).getText();
    }

    protected void waitForVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}