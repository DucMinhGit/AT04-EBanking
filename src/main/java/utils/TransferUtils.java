package utils;

import java.util.Random;

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
        if (otp == null || otp.isEmpty()) return "INVALID";

        char[] chars = otp.toCharArray();
        Random rand = new Random();

        int pos = rand.nextInt(chars.length);
        char oldChar = chars[pos];
        char newChar = oldChar;

        while (newChar == oldChar) {
            newChar = (char) (rand.nextInt(26) + 'A');
        }

        chars[pos] = newChar;
        return new String(chars);
    }
}
