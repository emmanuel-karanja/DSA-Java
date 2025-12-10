package Arrays;

/**You are given array where the value at an index represents the height. You are asked how much water would collect after
 * rain.
 * 
 * INTUTION:
 * 
 * You need to calculate the volume of water that can be collected at the current block. 
 * Water is limited by the lower bounding column to the right or the left. And water at block is Min(boundingLeft,boundingRight)-
 * currentHeight.
 * 
 * Approach one: is to visit each block and then expand outwards and find the leftMax, and then the rightMax and find
 * the min of these two and then subtract the current height to get water at block.
 * 
 * This is O(n^2) time
 * 
 * Second approach:
 * 
 * Below, if we can establish early where the limiting maxHeight is, we only need to expand from that side.
 * e.g. if we find it's left, we just advance until we find a smaller max height and then advance that side
 * too. Using two pointers.
 * 
 * This is O(n)
 * 
 */

public class WaterAfterRain {

    public static int getCollectedWater(int[] heights){
        if(heights==null || heights.length==0){
            throw new IllegalArgumentException("Heights is empty or null");
        }

        //you need at least 3 blocks to collect water
        if(heights.length<3) return 0;

        
        //keep track of leftMax and rightMax
        int leftMax=heights[0];
        int rightMax=heights[heights.length-1];
        int left=0;
        int right=heights.length-1;
        int totalWater=0;

        while(left<right){
            //if height[left]
            if(heights[left]<heights[right]){
                //we know the left height is the limiting height
                if(heights[left]>=leftMax){
                    leftMax=heights[left]; //we don't collect water, we update the leftMax
                }else{
                    //wecollect water
                    totalWater+=leftMax-heights[left];
                }

                left++; //advance left
            }else{ //right is lower
                 if(heights[right]>=rightMax){
                    rightMax=heights[right];
                 }else{
                    totalWater+=rightMax-heights[right];
                 }
                 right--;

            }
        }

        return totalWater;
    }

    public static void main(String[] args){
        int[] testHeights={4,1,3,6,2,5};

        System.out.println(getCollectedWater(testHeights));
    }
}
