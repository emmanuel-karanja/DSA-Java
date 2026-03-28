/**If you want to represent different symbols with upper and lower i.e. a and Z not be the same, 
 * then, you can simply ignore the case e.g. a url shortener would want to keep them as separate 
 * characters and add a few special chars to make the base something like 10+26+26 like 62 base so
 *  that with 7 symbols, you can represent 62^7 unique shortened urls or Ids*/

public class BaseConverter {
/**Use the Base 10 or Decimal bridge to turn a M x N conversion into an M+N conversion. */
   //Use a map here for O(1) lookup
    private static final String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Converts a decimal integer to a target base string.
     * Handles negative numbers and validates the base range.
     */
    public static String convertTo(long decimal, int base) {
        if (base < 2 || base > DIGITS.length()) {
            throw new IllegalArgumentException("Base must be between 2 and " + DIGITS.length());
        }
        if (decimal == 0) return "0";

        boolean isNegative = decimal < 0;

        long num = Math.abs(decimal);

        StringBuilder sb = new StringBuilder();

        while (num > 0) {
            int remainder = (int) (num % base);
            sb.append(DIGITS.charAt(remainder));
            num /= base;
        }

        if (isNegative) sb.append("-");
        return sb.reverse().toString();
    }

    /**
     * Converts a number string from one base to another via a decimal bridge.
     */
    public static String convertFromTo(String numStr, int fromBase, int toBase) {

        if (numStr == null || numStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Input number string cannot be empty.");
        }

        numStr = numStr.trim().toUpperCase();
        boolean isNegative = numStr.startsWith("-");
        String processingStr = isNegative ? numStr.substring(1) : numStr;

        long decimalValue = 0; // Use long to prevent overflow
        for (int i = 0; i < processingStr.length(); i++) {
            char ch = processingStr.charAt(i);
            int digitValue = DIGITS.indexOf(ch);

            // Validation: Digit must exist and be less than the fromBase
            if (digitValue == -1 || digitValue >= fromBase) {
                throw new IllegalArgumentException("Invalid character '" + ch + "' for base " + fromBase);
            }

            // Horner's Method: decimalValue = (decimalValue * base) + digit
            decimalValue = decimalValue * fromBase + digitValue;
        }

        // Apply original sign to the bridge value
        long finalDecimal = isNegative ? -decimalValue : decimalValue;
        return convertTo(finalDecimal, toBase);
    }

    public static void main(String[] args) {
        // Test Cases: Senior Level
        System.out.println("Negative Hex: " + convertFromTo("-171", 10, 16)); // -AB
        System.out.println("Base 16 to 2: " + convertFromTo("AB", 16, 2));   // 10101011
        
        try {
            convertFromTo("19", 8, 10); // Should fail: 9 isn't in Octal
        } catch (IllegalArgumentException e) {
            System.out.println("Caught Validation: " + e.getMessage());
        }
    }
}