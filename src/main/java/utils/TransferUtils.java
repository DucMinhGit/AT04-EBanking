package utils;

import java.text.DecimalFormat;

public class TransferUtils {
    public static double generateValidTransferAmount(double currentBalance, double fee) {
        double maxTransferable = currentBalance - fee;
        double amount = Math.ceil(Math.random() * maxTransferable);

        return amount;
    }

    public static double calcExpectedBalance(double currentBalance, double amount, double fee) {
        return currentBalance - amount - fee;
    }

    public String formatMoneyForTable(double amount) {
        DecimalFormat df = new DecimalFormat("#,###");
        String numberStr = df.format(Math.abs(amount));
        String prefix = (amount < 0) ? "- " : "+ ";
        return prefix + numberStr;
    }
}
