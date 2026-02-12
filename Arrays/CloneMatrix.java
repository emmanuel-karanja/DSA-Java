package Arrays;

import java.util.Arrays;
/**Clone a matrix:
 * 
 *  INTUITION:
 * 
 *  1. Just scan the matrix and clone one row at a time.
 * 
 *  This examples shows that in Java you don't need to specify the second dimension.
 *  And the example also allows you to clone uneven length rows i.e. they could be staggered.
 */
public class CloneMatrix {

    int[][] cloneMatrix(int[][] graph) {
        int n = graph.length;
        //1. Key 1
        int[][] clone = new int[n][];
        for (int i = 0; i < n; i++) {
             // 2. Key 2
            clone[i] = Arrays.copyOf(graph[i], graph[i].length);
        }
        return clone;
    }
}
