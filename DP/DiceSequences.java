package DP;

import java.util.*;

public class DiceSequences {

    static final int MOD = 1_000_000_007;

    /*
    ===========================================================================
    Problem:
    ---------------------------------------------------------------------------
    Roll a 6-sided die n times.
    No number may appear more than twice consecutively.
    Count the number of valid sequences modulo 1e9+7.
    ===========================================================================
    */

    public static int countSequences(int n) {
        // dp[lastNumber][count]
        long[][] dp = new long[7][3];
        long[][] next = new long[7][3];

        // Base case: first roll
        for (int j = 1; j <= 6; j++) {
            dp[j][1] = 1;
        }

        for (int i = 1; i < n; i++) {
            // reset next
            for (int j = 1; j <= 6; j++) {
                Arrays.fill(next[j], 0);
            }

            for (int last = 1; last <= 6; last++) {
                for (int count = 1; count <= 2; count++) {
                    long ways = dp[last][count];
                    if (ways == 0) continue;

                    for (int roll = 1; roll <= 6; roll++) {
                        if (roll == last) {
                            if (count < 2) {
                                next[roll][count + 1] =
                                        (next[roll][count + 1] + ways) % MOD;
                            }
                        } else {
                            next[roll][1] =
                                    (next[roll][1] + ways) % MOD;
                        }
                    }
                }
            }

            // swap
            long[][] temp = dp;
            dp = next;
            next = temp;
        }

        long result = 0;
        for (int j = 1; j <= 6; j++) {
            for (int c = 1; c <= 2; c++) {
                result = (result + dp[j][c]) % MOD;
            }
        }

        return (int) result;
    }

    // -------------------------------------------------------------------------
    // Driver
    // -------------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println(countSequences(1)); // 6
        System.out.println(countSequences(2)); // 36
        System.out.println(countSequences(3)); // 210
        System.out.println(countSequences(4)); // 1230
    }
}