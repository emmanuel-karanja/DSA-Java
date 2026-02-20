package Arrays;

import java.util.TreeSet;

/**
 * PROBLEM:
 * Given an integer matrix `matrix[m][n]` (can contain negative numbers) and an integer `k`,
 * find the maximum sum of any rectangle (submatrix) such that the sum ≤ k.
 *
 * Example:
 * matrix = [[1, 0, 1],
 *           [0, -2, 3]]
 * k = 2
 * Maximum rectangle sum ≤ 2 is 2 (from rectangle [[0,-2],[1,3]]).
 * Output: 2
 *
 * REASONING:
 * 1. Reduce 2D → 1D using row sums:
 *    - Fix two rows `top` and `bottom`.
 *    - For each column c, compute sum of elements in that column between these rows:
 *      colSum[c] = sum(matrix[top..bottom][c])
 *    - This converts the 2D problem into a 1D array problem: find max subarray sum ≤ k in colSum.
 *
 * 2. Solve 1D max subarray sum ≤ k using prefix sums + TreeSet:
 *    - Maintain prefixSum[i] = sum of first i elements in colSum.
 *    - For current prefixSum[j], find smallest prefixSum[i] ≥ prefixSum[j] - k in TreeSet.
 *      Then prefixSum[j] - prefixSum[i] ≤ k, update max.
 *
 * 3. Iterate all pairs of rows (top, bottom):
 *    - For each row pair, compute colSum array and apply 1D max subarray sum ≤ k.
 *
 * 4. Time Complexity:
 *    - O(m² * n * log n), where m = #rows, n = #columns
 *    - TreeSet lookup and insert: O(log n) for each column sum.
 */
public class MaxSubmatrixSumAtMostK {
    public static int maxSumSubmatrix(int[][] matrix, int k) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int maxSum = Integer.MIN_VALUE;

        // Iterate over all pairs of rows
        for (int top = 0; top < rows; top++) {
            int[] colSum = new int[cols]; // column sums between top and bottom rows
            for (int bottom = top; bottom < rows; bottom++) {
                // Update column sums for this bottom row
                for (int c = 0; c < cols; c++) {
                    colSum[c] += matrix[bottom][c];
                }

                // Find max subarray sum ≤ k in colSum
                TreeSet<Integer> prefixSet = new TreeSet<>();
                prefixSet.add(0); // padding for subarray starting at index 0
                int prefixSum = 0;

                for (int val : colSum) {
                    prefixSum += val;
                    // Find smallest prefix >= prefixSum - k
                    Integer target = prefixSet.ceiling(prefixSum - k);
                    if (target != null) {
                        maxSum = Math.max(maxSum, prefixSum - target);
                    }
                    prefixSet.add(prefixSum);
                }
            }
        }

        return maxSum;
    }

    // Driver main
    public static void main(String[] args) {
        int[][] matrix1 = {
            {1, 0, 1},
            {0, -2, 3}
        };
        int k1 = 2;
        System.out.println(maxSumSubmatrix(matrix1, k1)); // Output: 2

        int[][] matrix2 = {
            {2, 2, -1},
            {3, 1, 1},
            {1, -1, 2}
        };
        int k2 = 4;
        System.out.println(maxSumSubmatrix(matrix2, k2)); // Output: 4

        int[][] matrix3 = {
            {5, -4, -3, 4},
            {-3, -4, 4, 5},
            {5, 1, 5, -4}
        };
        int k3 = 8;
        System.out.println(maxSumSubmatrix(matrix3, k3)); // Output: 8
    }
}
