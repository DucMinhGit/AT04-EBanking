package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;

@Log4j2
public class CreateAccountPage extends BasePage {
    @Step("Create new deposit account any term")
    public void createDepositAcctAnyTerm() {
        selectAccountTypeByIndex(DEPOSIT_ANY_TERM);
        click(createBtn);
    }

    @Step("Create new saving account")
    public void createSavingAccount() {
        selectAccountTypeByIndex(SAVING_ACCOUNT);
        click(createBtn);
    }

    public void selectAccountTypeByIndex(int index) {
        waitForVisible(accountTypeDropdown);
        click(accountTypeDropdown);

        By itemLocator = By.xpath(String.format("//div[(@id='j_idt23:j_idt27_panel')] //ul //li[%d]", index));

        waitForVisible(itemLocator);
        click(itemLocator);
    }

    private final int SAVING_ACCOUNT = 3;
    private final int DEPOSIT_ANY_TERM = 2;
    private final By accountTypeDropdown = By.id("j_idt23:j_idt27");
    private final By createBtn = By.name("j_idt23:j_idt31");
}