

/**
 * String Interleaving – DP Breakdown
 *
 * Goal:
 *   Determine whether s3 can be formed by interleaving s1 and s2 while preserving the relative order of characters in each string.
 *
 * State:
 *   dp[i][j] = true if it is possible to form the prefix
 *              s3[0 .. i + j - 1]
 *              using the first i characters of s1
 *              and the first j characters of s2.
 *
 * Choices / Decisions:
 *   At state (i, j), decide where the LAST character of
 *   s3[i + j - 1] came from:
 *
 *   1) Take from s1
 *      - Allowed if i > 0 && s1[i - 1] == s3[i + j - 1] && Previous state: dp[i - 1][j]
 *
 *   2) Take from s2
 *      - Allowed if j > 0 &$s2[j - 1] == s3[i + j - 1] && Previous state: dp[i][j - 1]
 *
 * Recurrence:
 *   dp[i][j] =
 *     (i > 0 && dp[i - 1][j] && s1[i - 1] == s3[i + j - 1])
 *     ||
 *     (j > 0 && dp[i][j - 1] && s2[j - 1] == s3[i + j - 1])
 *
 * Base Case:
 *   dp[0][0] = true
 *     // empty s1 and empty s2 form empty s3
 *
 *   dp[i][0] = true if s1[0..i-1] == s3[0..i-1]
 *     // only characters from s1 are used
 *
 *   dp[0][j] = true if s2[0..j-1] == s3[0..j-1]
 *     // only characters from s2 are used
 *
 * Greedy Check:
 *   ❌ Cannot use greedy.
 *   No single choice (always take from s1 or always take from s2)
 *   dominates all future possibilities.
 *   The decision at each step depends on both remaining strings.
 *
 * NOTE:
 *   dp[0][*] and dp[*][0] are reserved for base cases, just like in 0/1 Knapsack.
 *   Here, dp[0][j] means “use 0 characters from s1 and j from s2”.
 *   s1[0] and s2[0] are real characters, but dp index 0 represents
 *   “using none of that string”.
 *   This intentional offset keeps the recurrence clean and uniform.
 * 
 * 
 * KEY NOTE:
 * The "Prefix" Requirement
In DP, dp[i][j] doesn't just mean "Is the current character a match?" It means "Is the entire prefix up to
 this point a valid interleave?"

If s1 is empty, then for dp[0][j] to be true, every single character in s2 from index 0 to j-1 must match
 s3 from 0 to j-1 in the exact same order.

The "Broken Link" Example
Imagine:

s1 = "" (Empty)
s2 = "ABC"
s3 = "AXC"

If you set the whole first row to true, you are saying s3 is a valid interleave of s1 and s2. But look at
 the second character:

dp[0][1] (A matches A): True
dp[0][2] (X does NOT match B): False
dp[0][3] (C matches C): ?

Even though the third character matches, the "chain" was broken at the second character. If the prefix is invalid,
 the entire path is dead.
 */

class StringInterleaving {
    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length();
        int n = s2.length();

        if (m + n != s3.length()) {
            return false;
        }

        boolean[][] dp = new boolean[m + 1][n + 1];

        dp[0][0] = true;

        // Refer to key note above for an explanation.
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
        }

        for (int j = 1; j <= n; j++) {
            dp[0][j] =dp[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // The character at s3[i+j-1] can only come from s1 if and only if i>0 (we've taken at least one character)
                // and s3[i+j-1]==s1[i-1] (due to 1 based indexing)
                boolean fromS1 =dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1);  

                // The character at s3[i+j-1] can only come from s2 if and only if j>0 (we've taken at least one character)
                // and s3[i+j-1]==s2[j-1] (due to 1 based indexing)
                boolean fromS2 =dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1);
                 // The character at s3[i+j-1] can only come from s1 or s2.
                dp[i][j] = fromS1 || fromS2;
            }
        }

        return dp[m][n];  // It means we came to the end of the string s3 and found if true, we found all the characters.
    }
}
