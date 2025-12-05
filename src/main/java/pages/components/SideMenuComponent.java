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

    public void clickTransfer() {
        log.info("Clicking 'Transfer' link in side menu");
        driver.findElement(transferLink).click();
    }

    public void clickBankAccount() {
        log.info("Clicking 'Bank Account' link in side menu");
        driver.findElement(bankAccountLink).click();
    }

    public void clickExternalTransfer() {
        log.info("Click 'Lien Ngan Hang' link in side menu");
        driver.findElement(externalTransferLink).click();
    }

    public void clickTransactionHistory() {
        log.info("Clicking 'Transaction History' link in side menu");
        driver.findElement(transactionHistoryLink).click();
    }

    public void clickLogout() {
        log.info("Clicking 'Logout' link");
        driver.findElement(logoutLink).click();
    }

    public void clickCreateAccount() {
        log.info("Clicking 'Open Account' link");
        driver.findElement(createAccountLink).click();
    }

    protected WebDriver driver;
    protected WebDriverWait wait;

    private final By transferLink = By.xpath("//div[contains(@class, 'ui-menu')]//ul[contains(@class, 'ui-menu-list')]/li[@role='menuitem'][6]/a");
    private final By externalTransferLink = By.xpath("//div[contains(@class, 'ui-menu')]//ul[contains(@class, 'ui-menu-list')]/li[@role='menuitem'][7]/a");
    private final By transactionHistoryLink = By.xpath("//div[contains(@class, 'ui-menu')]//ul[contains(@class, 'ui-menu-list')]/li[@role='menuitem'][5]/a");
    private final By logoutLink = By.xpath("//div[contains(@class, 'ui-menu')]//ul/li[last()]/a");
    private final By createAccountLink = By.xpath("//div[contains(@class, 'ui-menu')]//ul[contains(@class, 'ui-menu-list')]/li[@role='menuitem'][3]/a");
    private final By bankAccountLink = By.xpath("//div[contains(@class, 'ui-menu')]//ul[contains(@class, 'ui-menu-list')]/li[@role='menuitem'][2]/a");
}
