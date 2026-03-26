/**
 * PROBLEM:
 * Convert a number into a custom notation:
 *
 * - Numbers from 0 to 999,999 → standard 6-digit decimal (zero padded)
 * - Numbers ≥ 1,000,000 → split into:
 *      remainder (last 6 digits) + base-26 suffix
 *
 * Example:
 * 1,000,000 → 000000 + A → 00000A
 * 1,000,001 → 000001 + A → 00001A
 * 2,000,000 → 000000 + B → 00000B
 *
 * ------------------------------------------------------------
 *
 * APPROACH:
 * 1. remainder = num % 1_000_000
 * 2. quotient  = num / 1_000_000
 * 3. Convert quotient → Excel-style base-26
 * 4. Combine:
 *      zero-padded remainder + suffix
 */

public class ConvertToNotation {

    public String convertTo(int num) {

        int remainder = num % 1_000_000;
        int quotient  = num / 1_000_000;

        // Step 1: format remainder to 6 digits
        String numberPart = String.format("%06d", remainder);

        // Step 2: convert quotient to base-26 (Excel style)
        String suffix = toBase26(quotient);

        return numberPart + suffix;
    }

    private String toBase26(int num) {
        if (num == 0) return "";

        StringBuilder sb = new StringBuilder();

        while (num > 0) {
            num--; // adjust for 1-based indexing
            char c = (char) ('A' + (num % 26));
            sb.append(c);
            num /= 26;
        }

        return sb.reverse().toString();
    }

    // ---------------- DRIVER ----------------
    public static void main(String[] args) {
        ConvertToNotation solver = new ConvertToNotation();

        int[] testCases = {
            0,
            999_999,
            1_000_000,
            1_000_001,
            2_000_000,
            26_000_000,
            27_000_000
        };

        for (int num : testCases) {
            System.out.println(num + " --> " + solver.convertTo(num));
        }
    }
}