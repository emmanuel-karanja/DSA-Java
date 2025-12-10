
/**Given an integer mxn matrix where each row is sorted in ascending order and so are the columns, find an efficient way to establish
 *  if a target integer is in the matrix.
 * 
 * INTUITION:
 * 
 * Use binary search where if we can't find an element with the row, we simply go to the next two
 * and if it's within,we reduce the column.
 * 
 * KEY: Start the top-right corner. i.e [0,n-1]
 */
public class MatrixSearch {
    
    public static boolean isInMatrix(int[][] matrix,int target){

        if(matrix==null || matrix.length==0) {
            throw new IllegalArgumentException("Matrix is null or empty.");
        }
        int row=0; //start row
        int col=matrix[0].length-1;

        while(row<matrix.length && col>=0){
            //we found it
            if(matrix[row][col]==target){
                // to return the position of the element it's basically [row,col]
                return true;
            }else if(target<matrix[row][col]){
                //move left
                col--;
            }else{
                //move down
                row++;
            }
        }

        return false;
    }
}
