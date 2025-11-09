package pages.components;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Log4j2
public class SideMenuComponent {
    public SideMenuComponent(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void waitForComponentLoad() {
        log.info("Waiting for Side Menu component to load...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(transferLink));
        log.info("Side Menu component loaded successfully.");
    }

    public void clickTransfer() {
        log.info("Clicking 'Transfer' link in side menu");
        wait.until(ExpectedConditions.elementToBeClickable(transferLink)).click();
    }

    public void clickExternalTransfer() {
        log.info("Click 'Liên Ngân Hàng' link in side menu");
        wait.until(ExpectedConditions.elementToBeClickable(externalTransferLink)).click();
    }

    public void clickTransactionHistory() {
        log.info("Clicking 'Transaction History' link in side menu");
        wait.until(ExpectedConditions.elementToBeClickable(transactionHistoryLink)).click();
    }

    public void clickLogout() {
        log.info("Clicking 'Logout' link");
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

    protected WebDriver driver;
    protected WebDriverWait wait;

    private final By transferLink = By.xpath("//a[@href='/EBankingWebsite/faces/transfer.xhtml']");
    private final By externalTransferLink = By.xpath("//a[@href='/EBankingWebsite/faces/banktransfer.xhtml']");
    private final By transactionHistoryLink = By.xpath("//a[@href='/EBankingWebsite/faces/transaction.xhtml']");
    private final By logoutLink = By.xpath("//div[contains(@class, 'ui-menu')]//ul/li[last()]/a");

}