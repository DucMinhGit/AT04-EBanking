package pages.admin;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import pages.BasePage;
import utils.Constants;

@Log4j2
public class AdminDepositPage extends BasePage {
    @Step("Navigate to Deposit Page")
    public void goToDepositPage() {
        waitForVisible(depositMenuLink);
        click(depositMenuLink);
    }

    @Step("Admin deposit {amount} to account {accountNo}")
    public void depositMoney(String accountNo, double amount, String content) {
        log.info("Depositing {} to account {}", amount, accountNo);

        type(accountInput, accountNo);
        type(amountInput, String.valueOf(amount));
        type(contentInput, content);
        click(submitBtn);
    }

    @Step("Admin Logout")
    public void logout() {
        click(logoutBtn);
        waitForVisible(adminLoginInput);
    }

    private final By depositMenuLink = By.xpath("//a[contains(@href, 'deposit.xhtml')]");
    private final By accountInput = By.name("j_idt23:j_idt27");
    private final By amountInput = By.name("j_idt23:j_idt29");
    private final By contentInput = By.name("j_idt23:j_idt31");
    private final By submitBtn = By.name("j_idt23:j_idt33");
    private final By logoutBtn = By.name("j_idt9:j_idt19");

    private final By adminLoginInput = By.id("j_idt9:id1");
}