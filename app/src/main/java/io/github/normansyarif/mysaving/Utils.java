package io.github.normansyarif.mysaving;

class Utils {
    static String numberToMoney(int number) {
        return "Rp. " + String.format("%,d", number * 1000);
    }

    static int moneyToNumber(String money) {
        return Integer.parseInt(money.replace("Rp. ", "").replace(",", "").replace(".", "")) / 1000;
    }
}
