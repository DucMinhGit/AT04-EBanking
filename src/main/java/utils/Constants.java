package utils;

public class Constants {
    public static final String DEFAULT_USERNAME = "at04test";
    public static final String DEFAULT_PASSWORD = "12345678";
    public static final String DEFAULT_USERNAME_ADMIN = "admin";
    public static final String DEFAULT_PASSWORD_ADMIN = "123";
    public static final float EXT_FEE = 3300;
    public static final float INT_FEE = 1100;
    public static final double STANDARD_TRANSFER_AMOUNT = 10000;

    public enum AccountType {
        DEFAULT(1),
        DEPOSIT_ANY_TERM(2),
        SAVING(3);

        public final int index;
        AccountType(int index) { this.index = index; }
    }

    private Constants() {
    }
}
