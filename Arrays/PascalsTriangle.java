package Arrays;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**Generate the pascal's triangle for n.
 * 
 * INTUTION
 * 
 * Generate each row and add it to the triangle.
 */
public class PascalsTriangle {
    public static List<int[]> generateTriangles(int n){
        if(n<=0){
            throw new IllegalArgumentException("N must be positive.");
        }

        //generate the outermost triangle
        List<int[]> triangle=new ArrayList<>();

        for(int i=0;i<n;i++){
            //generate current row
            int size=i+1; 
            int[] currentRow=new int[size];
            Arrays.fill(currentRow,1);  // This is how you initialize an array

            //generate inner values
            for(int j=1;j<i;j++){
                currentRow[j]=triangle.get(i-1)[j-1]+triangle.get(i-1)[j];
            }
            triangle.add(currentRow); // Rows are generated in order so just add
        }

        return triangle;
    }

    //this is better it uses List<Integer> instead of int[]
    public static List<List<Integer>> generateTriangle(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be positive.");
        }

        List<List<Integer>> triangle = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            List<Integer> row = new ArrayList<>();
            // First element is always 1
            row.add(1);

            // Inner elements: sum of two elements from previous row
            if (i > 0) {
                List<Integer> prevRow = triangle.get(i - 1);
                for (int j = 1; j < i; j++) {
                    row.add(prevRow.get(j - 1) + prevRow.get(j));
                }
                // Last element is always 1
                row.add(1);
            }

            triangle.add(row);
        }

        return triangle;
    }
    public static void main(String[] args){
        List<int[]> triangle=generateTriangles(6);

        for(int[] row: triangle){
            System.out.println(Arrays.toString(row));
        }
    }
    
}
