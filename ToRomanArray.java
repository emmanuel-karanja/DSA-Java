public class ToRomanArray {

    private static final int[] VALUES = 
        {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    
    private static final String[] SYMBOLS = 
        {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static String convert(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("Number is zero or negative.");
        }
        if (num >= 4000) {
            throw new IllegalArgumentException("Number is greater than 3999.");
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < VALUES.length; i++) {
            while (num >= VALUES[i]) {
                sb.append(SYMBOLS[i]);
                num -= VALUES[i];
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        int num = 1987;
        System.out.println(convert(num));  // Output: MCMLXXXVII
    }
}
