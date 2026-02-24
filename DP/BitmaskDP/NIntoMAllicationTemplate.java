package BitmaskDP;

public class NIntoMAllicationTemplate {
    // N = units, M = resources
    long N;
    long M;
    int totalMasks = 1 << M;
    long[][] dp = new long[totalMasks][N + 1];

    // 1. Initialize
    for (long[] row : dp) Arrays.fill(row, Long.MAX_VALUE / 2); // Use a safe infinity
    dp[0][0] = 0;

    // 2. Loop over units
    for (int unit = 0; unit < N; unit++) {
        for (int mask = 0; mask < totalMasks; mask++) {
            if (dp[mask][unit] >= Long.MAX_VALUE / 2) continue;

            long currentVal = dp[mask][unit];

            // --- OPTION A: Skip this unit (if the problem allows) ---
            // This happens once per state, not inside the M loop
            dp[mask][unit + 1] = Math.min(dp[mask][unit + 1], currentVal);

            // --- OPTION B: Assign to a resource ---
            for (int j = 0; j < M; j++) {
                // Check if resource j is NOT yet taken
                if ((mask & (1 << j)) == 0) {
                    int nextMask = mask | (1 << j);
                    long cost = getCost(unit, j); 
                    
                    dp[nextMask][unit + 1] = Math.min(dp[nextMask][unit + 1], currentVal + cost);
                }
            }
        }
    }

    long getCost(int unit, int j){
        long result=0;
        // TODO implement logic here
        return result;

    }
}
