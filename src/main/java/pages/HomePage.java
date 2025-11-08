package pages;

import Utils.Driver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Log4j2
public class HomePage {
    public HomePage() {
        this.driver = Driver.getDriver();
        this.wait = Driver.getWebDriverWait();
    }

    public void waitForPageLoad() {
        log.info("Waitting (Homepage) dowloaded...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink));
        log.info("Homepage successfuly.");
    }

    public void clickTransfer() {
        log.info("Enter 'Bank'");
        wait.until(ExpectedConditions.elementToBeClickable(transferLink)).click();
    }

    public void clickTransactionHistory() {
        log.info("Enter'history transaction'");
        wait.until(ExpectedConditions.elementToBeClickable(transactionHistoryLink)).click();
    }

    private WebDriver driver;
    private WebDriverWait wait;
    private final By transferLink = By.xpath("//span[text()='Chuyển  khoản']");
    private final By transactionHistoryLink = By.xpath("//span[text()='Nhật kí giao dịch']/ancestor::a");
    private final By logoutLink = By.xpath("//span[text()='Đăng xuất']/ancestor::a");
}