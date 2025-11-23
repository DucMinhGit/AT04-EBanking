package pages.Internal_Transfer;

import Utils.Driver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Log4j2
public class OTPPage {

    // Locator
    private final By otpInput = By.name("j_idt23:j_idt46");
    private final By transferBtn = By.name("j_idt23:j_idt48");

    private final WebDriverWait wait = Driver.getWebDriverWait();

    // Input OTP
    public void enterOtp(String otp) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(otpInput)).sendKeys(otp);
        log.info("Input OTP successfully.");
    }

    // Click Transfer button
    public void clickTransfer() {
        Driver.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(transferBtn)).click();
        log.info("Clicked Transfer button successfully.");
    }

    //----INT08----
    private final By getInvalidOTPMessage = By.xpath("//*[contains(text(),'Sai mã OTP')]");

    // Read the invalid message title text and return it (uses wait and trims)
    public String invalidOTP() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(getInvalidOTPMessage));
        log.info("Read OTP error message successfully.");
        return el.getText().trim();
    }
}
