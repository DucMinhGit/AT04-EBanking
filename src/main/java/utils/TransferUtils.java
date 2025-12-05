package utils;

public class TransferUtils {
    public static double generateValidTransferAmount(double currentBalance, double fee) {
        double maxTransferable = currentBalance - fee;
        double amount = Math.ceil(Math.random() * maxTransferable);

        return amount;
    }

    public static double calcExpectedBalanceExternal(double currentBalance, double amount) {
        return currentBalance - amount - Constants.EXT_FEE;
    }

    public static double calcExpectedBalanceInternal(double currentBalance, double amount) {
        return currentBalance - amount - Constants.INT_FEE;
    }

    public static String generateInvalidOtp(String otp) {
        if (otp == null || otp.isEmpty()) return "000000";

        String prefix = otp.substring(0, otp.length() - 1);

        char lastChar = otp.charAt(otp.length() - 1);
        char newLastChar = (lastChar == '1') ? '2' : '1';

        return prefix + newLastChar;
    }
}
