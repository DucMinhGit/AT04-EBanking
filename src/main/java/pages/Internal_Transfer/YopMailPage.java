package pages.Internal_Transfer;

import Utils.Driver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class YopMailPage {

    // Locator
    private final By addressInput = By.id("login");
    private final By goButton = By.cssSelector("#refreshbut button");
    // Locator iframe contains email content
    private final By otpFrame = By.id("ifmail");
    // Locator tag <pre> contains OTP
    private final By otpPre = By.xpath("//pre[contains(text(),'OTP')]");
    // Locator Refresh Yopmail button
    private final By refreshBtnBy = By.id("refresh");

    private final WebDriver driver = Driver.getDriver();

    // Enter inbox name in the Yopmail search box
    public void enterInbox(String name) {
        driver.findElement(addressInput).clear();
        driver.findElement(addressInput).sendKeys(name);
    }

    public void clickGo() {
        driver.findElement(goButton).click();
    }

    public String getOtpCode() {
        // Click refresh before get OTP
        WebElement ref = Driver.getWebDriverWait()
                .until(ExpectedConditions.elementToBeClickable(refreshBtnBy));
        ref.click();
        // Wait iframe appear and switch to the iframe
        Driver.getWebDriverWait()
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(otpFrame));
        // Wait <pre> tag contains the OTP appear in the iframe
        WebElement otpElement = Driver.getWebDriverWait()
                .until(ExpectedConditions.visibilityOfElementLocated(otpPre));
        // Get Text and process OTP string
        String otp = otpElement.getText().replace("OTP:", "").trim();
        // Exit iframe
        driver.switchTo().defaultContent();
        log.info("Get OTP successfully.");
        return otp;
    }
}
