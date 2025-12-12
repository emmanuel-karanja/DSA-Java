package Arrays;
/**Given an array of nums where each element represents the maximum jump length, at that position,
 * return the minimum number of jumps needed to reach the last index.
 * 
 * INTUTION:
 * From the current position i what's the farthest index we can reach? this is i+nums[i]
 * 
 * We have a window: start(i) to currentEnd. Scan the window i.e.  start...currentEnd and find the
 * furthest you can reach.
 * 
 * Before we leave the current window, we explore how far we can reach with one more jump from any index in this window.
   When the window is exhausted, we already know the best possible extension, so we commit one jump and form the next window.
   Each window corresponds to exactly one jump.
 * 

 */
public class JumpGameII {

    public static int getJumps(int[] nums){
        int currentWindowEnd=0;
        int currentFarthest=0;
        int jumps=0;

        for(int i=0;i<nums.length-1; i++){  //at the last index nums.length-1, we are already at the destination
            //we keep track of the furthest we can reach
            currentFarthest=Math.max(currentFarthest, i+nums[i]);  //at each index i within the window,we explore
            //to see how far we can reach and keep track of it. 

            if(i==currentWindowEnd){ //only expand the window when we reach the end of the current window
                currentWindowEnd=currentFarthest;
                jumps++;
            }
        }

        return jumps;
    }
    
}
