package Arrays;

import java.util.ArrayList;
import java.util.List;

/**Given an mxn (2d) matrix, generate the spiral order matrix.
 * 
 * INTUTION:
 * 
 * Draw clockwise square indicating directions in the clockwise fashion,label the edges.
 * 
 * - Traverse the edges clockwise: left→right, top→bottom, right→left, bottom→top.
 * - After finishing a row/column, shrink the boundary accordingly. (use the edge label is the value to incr or decr)
 * - Each “lap” of the spiral is stored as an inner list.
 * 
 * In a matrix[i][j] the end of the first row is  0,j-1 and the end of the first column is i-1,0 and course the last
 * element is i-1,j-1 and for avoidance of doubt j increases tothe right and the i increases from top the bottom
 * 
 * KEY: Stick to this convention in all your code. Because mixing up can fuck up a perfectly good interview. 
 */
public class SpiralMatrix {

    public static int[][] getSpiralOrder(int[][] matrix){
       
        if(matrix==null || matrix.length==0){
             throw new IllegalArgumentException("Matrix must not be null or empty.");
        }
     

        int top=0;
        int bottom=matrix.length-1;
        int left=0;
        int right=matrix[0].length-1;
        
        final int m=matrix.length;
        final int n=matrix[0].length;
        List<Integer> spiralList=new ArrayList<>();

        int[][] spiralMatrix = new int[m][n];

        while(left<=right && top<=bottom){
            //move left to right at the top
            for(int j=left;j<=right;j++){
                spiralList.add(matrix[top][j]);
            }
            top++;

            //move from top to bottom on the right
            for(int i=top;i<=bottom;i++){
                spiralList.add(matrix[i][right]);
            }
            right--;

            //move right to left at the bottom
            if(top<=bottom){
                for(int j=right;j>=left;j--){
                    spiralList.add(matrix[bottom][j]);
                }
                bottom--;
            }

            //move from bottom to top on the left

            if(left<=right){
               for(int i=bottom;i>=top;i--){
                spiralList.add(matrix[i][left]);
                 }
               left++;
            }
           
        }

         // Fill new 2D matrix row by row
        int index = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                spiralMatrix[i][j] = spiralList.get(index++);
            }
        }

        return spiralMatrix;
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };

        int[][] spiral = getSpiralOrder(matrix);

        for (int[] row : spiral) {
            for (int num : row) {
                System.out.print(num + "\t");
            }
            System.out.println();
       }
    }
}
