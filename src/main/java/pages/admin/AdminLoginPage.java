package pages.admin;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import models.Account;
import org.openqa.selenium.By;
import pages.BasePage;

@Log4j2
public class AdminLoginPage extends BasePage {
    @Step("Admin Login với User: {userId}")
    public void login(Account account) {
        log.info("Admin login start: {}", account.getUsername());

        driver.get("http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml");

        type(userIdInput, account.getUsername());
        type(passwordInput, account.getPassword());
        log.info("Admin login Submit");
        click(loginBtn);
        log.info("Admin login submitted");
    }

    private final By userIdInput = By.id("j_idt9:id1");
    private final By passwordInput = By.id("j_idt9:pwd1");
    private final By loginBtn = By.id("j_idt9:j_idt15");
}