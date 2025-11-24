package pages.transfer.external;

import com.mailosaur.MailosaurException;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import pages.BasePage;
import pages.transfer.internal.OpenNewTabPage;
import pages.transfer.internal.YopMailPage;
import utils.MailosaurUtil;
import utils.Constants;

import java.io.IOException;

@Log4j2
public class ExternalTransferOtpPage extends BasePage {
    public ExternalTransferOtpPage() {
        super();
    }

    @Step("Get OTP from Email")
//    public String getOtpFromEmail()
//            throws IOException, MailosaurException {
//
//        log.info("get otp from Mailosaur service to get OTP...");
//
//        long receivedAfter = System.currentTimeMillis();
//        String emailAddress = Constants.OTP_RECEIVER_EMAIL;
//
//        return MailosaurUtil.getOtp(emailAddress, receivedAfter);
//    }

    public String getOtpFromEmail() {
        OpenNewTabPage opener = new OpenNewTabPage();
        String originalHandle = opener.openNewTabAndSwitch("https://yopmail.com/wm");

        YopMailPage yop = new YopMailPage();
        yop.enterInbox("ebanking");
        yop.clickGo();

        String otp = yop.getOtpCode();
        System.out.println("OTP is: " + otp);

        opener.switchToHandle(originalHandle);

        return otp;
    }

    @Step("Enter OTP code ({otp}) and click send")
    public void submitOtp(String otp) {
        log.info("Entering OTP: {}", otp);
        type(otpInput, otp);

        log.info("Click 'Transfer'...");
        click(submitButton);
    }

    @Step("Get OTP Error Message")
    public String getErrorMessage() {
        log.info("Getting OTP error message...");
        return getVisibleText(otpErrorLabel);
    }

    private final By otpInput = By.cssSelector("input[name$=':j_idt45']");
    private final By submitButton = By.cssSelector("input[name$=':j_idt47']");
    private final By otpErrorLabel = By.className("ui-growl-title");
}