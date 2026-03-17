package Arrays;

public class MaxSubmatrixSumAtMostKPrefixSum2D {

    public static int maxSumSubmatrix(int[][] matrix, int k) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Step 1: Build 2D prefix sum array
        int[][] prefix = new int[rows + 1][cols + 1];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                prefix[i+1][j+1] = matrix[i][j]
                        + prefix[i][j+1]
                        + prefix[i+1][j]
                        - prefix[i][j];
            }
        }

        int maxSum = Integer.MIN_VALUE;

        // Step 2: Iterate all possible rectangles
        // Use any of the points as the starting point 
        for (int r1 = 0; r1 < rows; r1++) {
            for (int c1 = 0; c1 < cols; c1++) {
                // Find the inner rectangle here
                for (int r2 = r1; r2 < rows; r2++) {
                    for (int c2 = c1; c2 < cols; c2++) {
                        // Rectangle sum using prefix sum
                        int sum = prefix[r2 + 1][c2 + 1]
                                - prefix[r1][c2 + 1]
                                - prefix[r2 + 1][c1]
                                + prefix[r1][c1];
                        if (sum <= k) {
                            maxSum = Math.max(maxSum, sum);
                        }
                    }
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