

/*Given two strings s1 and s2, find the length of the longest common subsequence:

INTUITION:
 * DP Breakdown for Longest Common Subsequence (LCS):
 * 
 * GOAL:
 *   Find the length of the longest common subsequence between strings s1 and s2.
 * 
 * STATE:
 *   dp[i][j] = length of LCS for the first i characters of s1 and the first j characters of s2.
 *   (i.e., dp[i][j] represents the best solution for the subproblem defined by prefixes of s1 and s2)
 * 
 * CHOICES:
 *   At position (i, j):
 *     1. If s1[i-1] == s2[j-1], we can extend the LCS ending at (i-1, j-1) by 1.
 *     2. If s1[i-1] != s2[j-1], we cannot extend diagonally, so we have two options:
 *        a) Skip the current character from s1 → dp[i-1][j]
 *        b) Skip the current character from s2 → dp[i][j-1]
 *        Take the maximum of these two options.
 * 
 * RECURRENCE RELATION:
 *   if (s1[i-1] == s2[j-1]):
 *       dp[i][j] = 1 + dp[i-1][j-1]
 *   else:
 *       dp[i][j] = max(dp[i-1][j], dp[i][j-1])
 * 
 * BASE CASE:
 *   dp[0][j] = 0 for all j → LCS of empty s1 and first j characters of s2 is 0
 *   dp[i][0] = 0 for all i → LCS of first i characters of s1 and empty s2 is 0
 * 
 * KEY INSIGHTS:
 *   - Only the three positions [i-1][j-1], [i-1][j], [i][j-1] affect dp[i][j] (dependency square)
 *   - This ensures optimal substructure and overlapping subproblems, making it suitable for DP
 *   - The final answer is dp[m][n], i.e., LCS length for the full strings
 */

public class LCS {

    public static int longestCommonSubsequence(String s1, String s2){
        final int m = s1.length();
        final int n = s2.length();

        // prefill a 2D matrix with 0; in Java arrays are zero-initialized by default
        int[][] dp = new int[m+1][n+1];

        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                // increase LCS length if characters match
                if(s1.charAt(i-1) == s2.charAt(j-1)){
                    dp[i][j] = 1 + dp[i-1][j-1];   // for the dp we have 1 based indexed system
                } else {
                    // take the max if characters don't match
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args){
       String s1 = "abcde";
       String s2 = "ace";

       System.out.println(longestCommonSubsequence(s1, s2)); // Output: 3
    }
    
}
