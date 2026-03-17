

/**
 * PROBLEM: Max Dot Product of Two Subsequences
 *
 * Given two arrays nums1 and nums2, return the maximum dot product of non-empty
 * subsequences of nums1 and nums2 of the same length.
 *
 * STRATEGY:
 * - This is structurally similar to the Longest Common Subsequence (LCS) problem:
 *   * LCS: pick elements if str1[i-1] == str2[j-1], otherwise skip.
 *   * Max Dot Product: pick elements if including nums1[i]*nums2[j] improves total,
 *     otherwise skip one or both.
 * - State: dp[i][j] = maximum dot product using prefixes nums1[0..i] and nums2[0..j].
 * - Transition:
 *   dp[i][j] = max(
 *       nums1[i]*nums2[j] + max(0, dp[i-1][j-1]), // include this pair
 *       dp[i-1][j],                               // skip nums1[i]
 *       dp[i][j-1]                                // skip nums2[j]
 *   )
 *
 * NOTES:
 * - Explicitly handles "non-empty" subsequences by initializing dp[i][j] with the current product.
 * - Works for negative numbers and sparse subsequences.
 * - Space optimization possible (O(min(N,M))) but clarity prioritized here with O(N*M).
 */
public class DotProductSubsequences {

    

    public int maxDotProduct(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        int NEG_INF = Integer.MIN_VALUE / 2; // if we will some addition, we don'twant to risk massive overflow
        // with something+NEG_INF

        int[][] dp = new int[n + 1][m + 1];

        // Since we are maximizing we use negative infinfity
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = NEG_INF;
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int product = nums1[i - 1] * nums2[j - 1];

                dp[i][j] = Math.max(
                    Math.max(product, product + Math.max(0, dp[i - 1][j - 1])),
                    Math.max(dp[i - 1][j], dp[i][j - 1])
                );
            }
        }

        return dp[n][m];
    }

    // DRIVER FUNCTION WITH TEST CASES
    public static void main(String[] args) {
        DotProductSubsequences sol = new DotProductSubsequences();

        int[] n1 = {2, 1, -2};
        int[] n2 = {3, 0, -6};
        System.out.println("Test 1 (Expected 18): " + sol.maxDotProduct(n1, n2));

        int[] n3 = {-1, -1};
        int[] n4 = {1, 1};
        System.out.println("Test 2 (Expected -1): " + sol.maxDotProduct(n3, n4));

        int[] n5 = {3, -2};
        int[] n6 = {2, -6, 7};
        System.out.println("Test 3 (Expected 21): " + sol.maxDotProduct(n5, n6));
    }
}
