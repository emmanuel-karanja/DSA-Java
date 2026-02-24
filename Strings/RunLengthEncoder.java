package Strings;

/**
 * PROBLEM:
 * Encode a string using run-length encoding with brackets:
 *
 *   k[substring] means substring repeated k times
 *
 * The encoder should only encode when it shortens the string.
 *
 * Examples:
 *   "aaabcbc"    -> "3[a]bcbc"
 *   "abababab"  -> "4[ab]"
 *   "aaaaaaaa"  -> "8[a]"
 *   "abc"       -> "abc"
 *
 * ------------------------------------------------------------
 * REASONING:
 *
 * We scan the string left-to-right.
 * At each position, we try to find the smallest substring that
 * repeats consecutively.
 *
 * If encoding shortens the string, we emit k[substring].
 * Otherwise, we emit the raw characters.
 *
 * This keeps the solution:
 * - deterministic
 * - readable
 * - decodable by the standard decoder
 *
 * ------------------------------------------------------------
 * TIME COMPLEXITY:
 *   O(N^2) worst case (substring matching)
 *
 * SPACE COMPLEXITY:
 *   O(N) for the output
 */

public class RunLengthEncoder {

    public static String encode(String s) {
        StringBuilder result = new StringBuilder();
        int n = s.length();
        int i = 0;

        while (i < n) {
            String bestEncoding = null;

            // Try all possible unit lengths starting at i
            for (int len = 1; i + len <= n; len++) {
                String unit = s.substring(i, i + len);
                int count = 1;

                int j = i + len;
                while (j + len <= n && s.substring(j, j + len).equals(unit)) {
                    count++;
                    j += len;
                }

                if (count > 1) {
                    String encoded = count + "[" + unit + "]";
                    String raw = s.substring(i, i + count * len);

                    if (encoded.length() < raw.length()) {
                        bestEncoding = encoded;
                        break; // smallest unit is best
                    }
                }
            }

            if (bestEncoding != null) {
                result.append(bestEncoding);
                // Advance past encoded region
                int unitLen = bestEncoding.substring(bestEncoding.indexOf('[') + 1,
                                                     bestEncoding.indexOf(']')).length();
                int repeat = Integer.parseInt(
                        bestEncoding.substring(0, bestEncoding.indexOf('['))
                );
                i += unitLen * repeat;
            } else {
                // No useful encoding
                result.append(s.charAt(i));
                i++;
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(encode("aaabcbc"));     // 3[a]bcbc
        System.out.println(encode("abababab"));    // 4[ab]
        System.out.println(encode("aaaaaaaaaa"));  // 10[a]
        System.out.println(encode("abcde"));       // abcde
    }
}