import java.util.Arrays;
/** Slightly different one from before,simply calculate and mark valid subsets.
 * 
 *  The precomputation here is subset enumeration with pruning.
 */
public class MinWorkSessionsII {

    public int minSessions(int[] tasks, int sessionTime) {
    int n = tasks.length;
    int totalMasks = 1 << n;
    
    // 1. Precompute valid subsets
    boolean[] isValid = new boolean[totalMasks];
    for (int i = 1; i < totalMasks; i++) {
        int sum = 0;
        for (int j = 0; j < n; j++) {
            if ((i & (1 << j)) != 0) sum += tasks[j];
        }
        if (sum <= sessionTime) isValid[i] = true;
    }

    // 2. DP with Submask Enumeration
    int[] dp = new int[totalMasks];
    Arrays.fill(dp, n); // Max sessions is n
    dp[0] = 0;

    for (int mask = 1; mask < totalMasks; mask++) {
        // The "Senior" submask loop: iterates only through bits present in 'mask'
        for (int sub = mask; sub > 0; sub = (sub - 1) & mask) {
            if (isValid[sub]) {
                dp[mask] = Math.min(dp[mask], dp[mask ^ sub] + 1);
            }
        }
    }
    return dp[totalMasks - 1];
}
}
