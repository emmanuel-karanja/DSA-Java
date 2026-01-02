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
            int h=(i==n)?0:heights[i];   //set a sentinel height of 0

            // The stack contains bars which are higher than the current height, we find the smaller or equal bar to the left
            // since it represents the left edge of the current bounded rectangle( bounded by the current height)
            // What this means is that we know for a given
            // interval, the area of the histogram will be bounded by the shortest bar (which is the current)
            // We find it's lower bound i.e. where we encounter a shorter or equal bar to the left. And since we'll have popped
            // to that bar by 1, we need to let left be shortestIndex+1 (we had gone beyond we reign it back in) 
            // We still calculate the areas of the bar between the current bar and the shorter or equal one to the left and find max
            
            while(!stack.isEmpty() && h < heights[stack.peek()]){
                //calculate area
                int height=heights[stack.pop()]; //height at the current top
                int right=i; //current i is the right

                // ie. 
                int left= stack.isEmpty()? -1 : stack.peek(); //note this

                //For each bar, find how far it can extend to the left and right before hitting a smaller bar.
                //Width = right index - left index - 1. Why the -1? Because the left and right indices are the bars 
                //1 beyond the limit and hence we should be subtracting 1 from bothleft and right i.e. (right-1)-(left+1)+1
                // = right-1-left-1+1= right-left-1
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
