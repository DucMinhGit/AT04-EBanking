package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;

@Log4j2
public class HomePage extends BasePage {
    @Step("Navigate to 'Chuyển khoản'")
    public void goToInternalTransferPage() {
        clickSideMenu(INTERNAL_TRANSFER);
    }

    @Step("Navigate to 'Tạo tài khoản'")
    public void goToCreateAccountPage() {
        clickSideMenu(CREATE_ACCOUNT);
    }

    @Step("Navigate to 'Liên Ngân Hàng'")
    public void goToExternalTransferPage() {
        clickSideMenu(EXTERNAL_TRANSFER);
    }

    @Step("Logout")
    public void logout() {
        clickSideMenu(LOGOUT);
    }

    public void clickSideMenu(int index) {
        By menuLocator = By.xpath(String.format(MENU_ITEM_TEMPLATE, index));

        waitForVisible(menuLocator);
        click(menuLocator);
    }

    public void clickSideMenu(String index) {
        By menuLocator = By.xpath(String.format(MENU_ITEM_TEMPLATE, index));

        waitForVisible(menuLocator);
        click(menuLocator);
    }

    private final int CREATE_ACCOUNT = 3;
    private final int INTERNAL_TRANSFER = 6;
    private final int EXTERNAL_TRANSFER = 7;
    private final int LOGOUT = 8;
    private final String MENU_ITEM_TEMPLATE = "//div[contains(@class, 'leftsidebar')]//ul/li[@role='menuitem'][%d]/a";
}