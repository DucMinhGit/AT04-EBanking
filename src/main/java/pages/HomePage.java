package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import pages.components.SideMenuComponent;

@Log4j2
public class HomePage extends BasePage {

    private SideMenuComponent sideMenu;

    public HomePage() {
        super();
        this.sideMenu = new SideMenuComponent(this.driver, this.wait);
    }

    @Step("Navigate to 'Liên Ngân Hàng'")
    public void clickExternalTransfer() {
        sideMenu.clickExternalTransfer();
    }

    public SideMenuComponent getSideMenu() {
        return this.sideMenu;
    }
}