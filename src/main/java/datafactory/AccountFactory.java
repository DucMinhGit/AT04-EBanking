package datafactory;

import models.Account;

public class AccountFactory {
    public static Account adminDefault() {
        return new Account("1", "admin");
    }
    public static Account userDefault() {
        return new Account("987656789", "ZXC1234@");
    }
}