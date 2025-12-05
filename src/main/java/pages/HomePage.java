package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;

@Log4j2
public class HomePage extends BasePage {
    public void goToInternalTransferPage() {
        log.info("Clicking 'Transfer' link in side menu");
        driver.findElement(transferLink).click();
    }

    @Step("Navigate to 'Tạo tài khoản'")
    public void goToCreateAccountPage() {
        log.info("Clicking 'Open Account' link");
        driver.findElement(createAccountLink).click();
    }

    @Step("Navigate to 'Liên Ngân Hàng'")
    public void goToExternalTransferPage() {
        log.info("Click 'Lien Ngan Hang' link in side menu");
        waitForVisible(externalTransferLink);
        driver.findElement(externalTransferLink).click();
    }

    @Step("Logout")
    public void logout() {
        log.info("Clicking 'Logout' link");
        driver.findElement(logoutLink).click();
    }

    private final By transferLink = By.xpath("//div[contains(@class, 'ui-menu')]//ul[contains(@class, 'ui-menu-list')]/li[@role='menuitem'][6]/a");
    private final By externalTransferLink = By.xpath("//div[contains(@class, 'ui-menu')]//ul[contains(@class, 'ui-menu-list')]/li[@role='menuitem'][7]/a");
    private final By logoutLink = By.xpath("//div[contains(@class, 'ui-menu')]//ul/li[last()]/a");
    private final By createAccountLink = By.xpath("//div[contains(@class, 'ui-menu')]//ul[contains(@class, 'ui-menu-list')]/li[@role='menuitem'][3]/a");
}