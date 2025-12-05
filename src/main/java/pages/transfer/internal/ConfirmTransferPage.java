package pages.transfer.internal;

import pages.BasePage;
import utils.Driver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class ConfirmTransferPage extends BasePage {

    // Locator nút xác nhận chuyển tiền
    private final By confirmBtnBy = By.name("j_idt23:j_idt44");

    // Khởi tạo helper mở tab
    private final OpenNewTabPage tabHelper = new OpenNewTabPage();

    public void confirm() {
        waitForVisible(confirmBtnBy);
        click(confirmBtnBy);
        log.info("Clicked Confirm successfully.");
    }

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
}