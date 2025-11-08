package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.components.SideMenuComponent;

@Log4j2
public class HomePage extends BasePage {

    private SideMenuComponent sideMenu;

    public HomePage() {
        super();
        this.sideMenu = new SideMenuComponent(this.driver, this.wait);
    }

    public void waitForPageLoad() {
        log.info("Waiting (Homepage) download...");
        sideMenu.waitForComponentLoad();
        log.info("Homepage successfully.");
    }

    public void clickTransfer() {
        sideMenu.clickTransfer();
    }

    public void clickTransactionHistory() {
        sideMenu.clickTransactionHistory();
    }

    public void clickLogout() {
        sideMenu.clickLogout();
    }

    public SideMenuComponent getSideMenu() {
        return this.sideMenu;
    }
}