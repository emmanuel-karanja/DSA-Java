

/**
 * Longest Palindromic Subsequence (LPS) - DP Breakdown
 *
 * Goal:
 *   Find the maximum length of a palindromic subsequence in a string.
 *
 * State:
 *   dp[i][j] = length of LPS in substring s[i..j]   
 * 
 * *This is where I used to bite the dust. Because I didn't know the meaning
 * of the state.
 *
 * Choices / Decisions:
 *   If s[i] == s[j]: include both ends -> 2 + dp[i+1][j-1]
 *   Else: drop one end -> max(dp[i+1][j], dp[i][j-1])
 *
 * Recurrence:
 *   dp[i][j] = (s[i] == s[j]) ? 2 + dp[i+1][j-1] : max(dp[i+1][j], dp[i][j-1])
 *
 * Base State:
 *   dp[i][i] = 1  // single character palindrome
 *
 * Greedy Check:
 *   ❌ Cannot use greedy because choosing one end does not guarantee optimal solution.
 */
public class LPSPure {

    public static int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];

        // Base state: single character palindromes
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        // Fill the DP table for substrings of length 2..n
        for (int i = n - 2; i >= 0; i--) {       // start from second last char
            for (int j = i + 1; j < n; j++) {    // end index always ahead of start
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = 2 + dp[i + 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[0][n - 1]; // LPS length for the whole string lets forget what dp means here; it's i to j 
    }

    public static void main(String[] args) {
        String s = "bbab";
        System.out.println("Longest Palindromic Subsequence length: " +
                            longestPalindromeSubseq(s)); // Output: 3 ("bab" or "bbb")
    }
}
