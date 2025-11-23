package pages;

import Utils.Driver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class AccountListingPage {

    // Locator
    private final By rowsBy = By.xpath("//div[@id='j_idt25_content']//tbody[@id='j_idt27_data']/tr");

    /**
     * Tách toàn bộ số tài khoản từ bảng
     * - Bỏ qua header, footer, row template
     * - Chỉ lấy chuỗi số >= 6 ký tự
     */
    private List<String> extractAllAccounts() {
        Driver.getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowsBy));

        List<WebElement> rows = Driver.getDriver().findElements(rowsBy);
        List<String> accounts = new ArrayList<>();

        for (WebElement row : rows) {
            String text = row.getText().trim();

            // lấy toàn bộ digits
            String digits = text.replaceAll("\\D+", "");

            // account sẽ >= 6 số
            if (digits.length() >= 6) {
                accounts.add(digits);
            }
        }

        log.info("All accounts extracted: " + accounts);
        return accounts;
    }

    //Account A = tài khoản thứ N-2 trong danh sách
    public String getAccountA() {
        List<String> accounts = extractAllAccounts();

        if (accounts.size() < 2) {
            throw new RuntimeException("Không đủ số tài khoản để lấy Account A.");
        }

        String accA = accounts.get(accounts.size() - 2);
        log.info("Account A (second-last) = [" + accA + "]");
        return accA;
    }

    // Account B = tài khoản thứ cuối cùng (N-1)
    public String getAccountB() {
        List<String> accounts = extractAllAccounts();

        if (accounts.isEmpty()) {
            throw new RuntimeException("Không tìm thấy tài khoản nào để lấy Account B.");
        }

        String accB = accounts.get(accounts.size() - 1);
        log.info("Account B (last) = [" + accB + "]");
        return accB;
    }
}
