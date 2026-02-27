package DP;

import java.util.*;

/*
===============================================================================
Problem:
-------------------------------------------------------------------------------
Given a binary matrix, find the area of the largest square containing only 1s.

Example:
Input:
1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0

Output:
4

===============================================================================
Approach (Dynamic Programming):
-------------------------------------------------------------------------------
dp[i][j] = size of the largest square with bottom-right corner at (i, j)

If matrix[i][j] == '1':
dp[i][j] = 1 + min(top, left, top-left)

Otherwise:
dp[i][j] = 0

Track the maximum dp value seen.

===============================================================================
*/

public class MaximalSquare {

    public static int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;

        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] dp = new int[rows][cols];
        int maxSide = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {  // either we are expanding an existing square or we are starting a new one
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1; // first row or column
                    } else {
                        dp[i][j] = 1 + Math.min(
                                dp[i - 1][j],
                                Math.min(dp[i][j - 1], dp[i - 1][j - 1])
                        );
                    }
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }

        return maxSide * maxSide;
    }

    // -------------------------------------------------------------------------
    // Driver
    // -------------------------------------------------------------------------
    public static void main(String[] args) {
        char[][] matrix = {
                {'1','0','1','0','0'},
                {'1','0','1','1','1'},
                {'1','1','1','1','1'},
                {'1','0','0','1','0'}
        };

        System.out.println(maximalSquare(matrix)); // 4
    }
}