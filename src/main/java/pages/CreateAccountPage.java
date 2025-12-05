package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;

@Log4j2
public class CreateAccountPage extends BasePage {
    @Step("Create new deposit account any term")
    public void createDepositAcctAnyTerm() {
        selectDropdown(accountTypeDropdown, depositAcctAnyTerm);
        click(createBtn);
    }

    @Step("Create new saving account")
    public void createSavingAccount() {
        selectDropdown(accountTypeDropdown, savingAccount);
        click(createBtn);
    }

    private final By accountTypeDropdown = By.id("j_idt23:j_idt27");
    private final By createBtn = By.name("j_idt23:j_idt31");
    private final By depositAcctAnyTerm= By.xpath("//div[(@id='j_idt23:j_idt27_panel')] //ul //li[2]");
    private final By savingAccount= By.xpath("//div[(@id='j_idt23:j_idt27_panel')] //ul //li[3]");

}