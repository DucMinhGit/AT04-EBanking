package utils;

public class TransferUtils {
    public static double generateValidTransferAmount(double currentBalance, double fee) {
        double maxTransferable = currentBalance - fee;
        double amount = Math.ceil(Math.random() * maxTransferable);

        return amount;
    }

    public static double calcExpectedBalance(double currentBalance, double amount, double fee) {
        return currentBalance - amount - fee;
    }
}
