package pages;

import Utils.Driver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Internal_Transfer.InternalTransferPage;

@Log4j2
public class HomePage {

    // Locator
    private final By transferTabBy = By.xpath("//a[@href='/EBankingWebsite/faces/transfer.xhtml']");
    private final By createAccountTabBy = By.xpath("//a[@href='/EBankingWebsite/faces/createaccount.xhtml']");

    // Click tab "Chuyển khoản"
    public InternalTransferPage clickTransferTab() {
        WebElement element = Driver.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(transferTabBy));
        element.click();
        log.info("Clicked 'Chuyển khoản' tab successfully.");
        return new InternalTransferPage();
    }

    // Click tab "Mở tài khoản"
    public void clickCreateAccountTab() {
        WebElement element = Driver.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(createAccountTabBy));
        element.click();
        log.info("Clicked 'Mở tài khoản' tab successfully.");
    }

    private By logoutButton = By.xpath("//span[text()='Đăng xuất']/parent::a");

    public void clickLogout() {
        WebDriverWait w = Driver.getWebDriverWait();
        w.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }
}