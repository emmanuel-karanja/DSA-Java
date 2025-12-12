package Arrays.MonotonicStack;

import java.util.ArrayDeque;

/**Given an array of positive integers that represent heights, find the largest area histogram using O(n) time.
 * 
 * INTUTION:
 * 
 * The area is bounded by the element with the smallest height fora given section.
 * Use a monotonically increasing stack
 */
public class MaxAreaHistogramStack {

    public static int getMaxArea(int[] heights){
        if(heights==null || heights.length==0){
            throw new IllegalArgumentException("Heights are empty or null.");
        }

        final int n=heights.length;

        //store indices
        ArrayDeque<Integer> stack=new ArrayDeque<>();

        int maxArea=0;

        //we usually pad the end with a height of length 0, why?
        for(int i=0;i<=n;i++){
            int h=(i==n)?0:heights[i]; 

            
            while(!stack.isEmpty() && h < heights[stack.peek()]){
                //calculate area
                int height=heights[stack.pop()]; //height at the current top
                int right=i; //current i is the right

                int left= stack.isEmpty()? -1 : stack.peek(); //note this

                //For each bar, find how far it can extend to the left and right before hitting a smaller bar.
                //Width = right index - left index - 1. Why the -1? Because the left and right indices are the bars 
                //smaller than the current bar, not the bar itself.
                int width=right-left-1;

                maxArea=Math.max(maxArea,width*height);
            }

            stack.push(i);
        }
        
        return maxArea;
    }

    public static void main(String[] args) {
        int[] histogram = {2, 1, 5, 6, 2, 3};
        int maxArea = getMaxArea(histogram);

        System.out.println("Max area of histogram: " + maxArea); // Output should be 10
    }
}
