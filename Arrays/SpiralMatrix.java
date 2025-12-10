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
            for(int i=left;i<=right;i++){
                spiralList.add(matrix[top][i]);
            }
            top++;

            //move from top to bottom on the right
            for(int j=top;j<=bottom;j++){
                spiralList.add(matrix[j][right]);
            }
            right--;

            //move right to left at the bottom
            if(top<=bottom){
                for(int i=right;i>=left;i--){
                    spiralList.add(matrix[bottom][i]);
                }
                bottom--;
            }

            //move from bottom to top on the left

            if(left<=right){
               for(int j=bottom;j>=top;j--){
                spiralList.add(matrix[j][left]);
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
