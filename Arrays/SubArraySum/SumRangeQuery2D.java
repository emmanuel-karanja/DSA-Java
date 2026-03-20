package Arrays;
/**
 * Problem:
 * Given a 2D matrix `matrix`, efficiently calculate the sum of elements 
 * inside a rectangle defined by its upper-left (row1, col1) and 
 * lower-right (row2, col2) corners multiple times.
 *
 * Reasoning:
 * By creating a prefix sum matrix with extra padding (size (m+1)x(n+1)), 
 * we can compute the sum of any submatrix in O(1) without worrying about 
 * boundaries. Each cell (i+1,j+1) stores the sum of all elements from 
 * (0,0) to (i,j) in the original matrix. Then we use inclusion-exclusion:
 * 
 * sumRegion(row1,col1,row2,col2) = prefixSum[row2+1][col2+1] 
 *                                  - prefixSum[row1][col2+1] 
 *                                  - prefixSum[row2+1][col1] 
 *                                  + prefixSum[row1][col1]
 */
public class SumRangeQuery2D {
    private int[][] prefixSum;

    public SumRangeQuery2D(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        prefixSum = new int[m + 1][n + 1]; // extra padding row & column

        // Compute prefix sums with padding
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                prefixSum[i + 1][j + 1] = matrix[i][j] 
                                        + prefixSum[i][j + 1] 
                                        + prefixSum[i + 1][j] 
                                        - prefixSum[i][j];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return prefixSum[row2 + 1][col2 + 1] 
             - prefixSum[row1][col2 + 1] 
             - prefixSum[row2 + 1][col1] 
             + prefixSum[row1][col1];
    }

    // Driver main function
    public static void main(String[] args) {
        int[][] matrix = {
            {3, 0, 1, 4, 2},
            {5, 6, 3, 2, 1},
            {1, 2, 0, 1, 5},
            {4, 1, 0, 1, 7},
            {1, 0, 3, 0, 5}
        };

        SumRangeQuery2D numMatrix = new SumRangeQuery2D(matrix);
        System.out.println("Sum of region (2,1) to (4,3): " + numMatrix.sumRegion(2, 1, 4, 3)); // 8
        System.out.println("Sum of region (1,1) to (2,2): " + numMatrix.sumRegion(1, 1, 2, 2)); // 11
        System.out.println("Sum of region (1,2) to (2,4): " + numMatrix.sumRegion(1, 2, 2, 4)); // 12
    }
}
