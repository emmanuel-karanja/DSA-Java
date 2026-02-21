package Arrays;

import java.util.ArrayDeque;
import java.util.Deque;
/**Treat each row from the top to the bottom as th ground.
 * Extrude the histogram from the top of the matrix and find the area at each level. */
public class MaxAreaBinaryMatrix {
    public int maximalRectangle(char[][] matrix) {

        if (matrix.length == 0) return 0;

        int cols = matrix[0].length;
        int[] heights = new int[cols];
        int maxArea = 0;

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < cols; col++) {
                // Update the state: consecutive 1s or reset to 0
                if (matrix[row][col] == '1') {
                    heights[col] += 1;
                } else {
                    heights[col] = 0;
                }
            }
            // Apply the "One-Way Death" Monotonic Stack logic to this row
            maxArea = Math.max(maxArea, calculateHistogramArea(heights));
        }
        return maxArea;
    }

    private int calculateHistogramArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int max = 0;
        // The i <= heights.length trick adds a virtual 0 at the end to flush the stack
        for (int i = 0; i <= heights.length; i++) {

            int h = (i == heights.length) ? 0 : heights[i];
            
            while (!stack.isEmpty() && h < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                max = Math.max(max, height * width);
            }
            stack.push(i);
        }
        return max;
    }
}
