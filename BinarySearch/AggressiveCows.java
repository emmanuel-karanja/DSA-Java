package BinarySearch;

import java.util.Arrays;

/**You are given:

An array of integers stalls[] representing positions of n stalls along a straight line.
An integer c representing the number of cows to place in the stalls.
Goal: Place all c cows in the stalls such that the minimum distance between any two cows is maximized.



Return the largest minimum distance possible. 

e.g. Input: stalls = [1, 2, 4, 8, 9], c = 3
Output: 3

Explanation: Place cows at positions 1, 4, 8. Minimum distance = 3.

INTUITION:

Maximize the min: 'Space out as much as possible.'

*Space them out as much as possible meaning when we find a value x that works, we continue searching the right
 side space.

This is a question of placing the cows from min to max. Min is 1 since the smallest distance is 1

We'll search the best placement from min to max using binary search until we find one that fits.

To find if cows can fit, all we really need to do isplace the first cow in the first stall
and then place all the others at lastPos+minDist and then check if lastPos+minDist > stalls.length-1.

But: Stalls are not evenly spaced so, we need to check if stall[i]-lastPos >=minDist and set
lastPos=stall[i] if successful.

And in the end,check if we have placed all the cows.

*/
public class AggressiveCows {
    
    public static int maxDistanceBetweenCows(int[] stalls ,int cows){
    
       Arrays.sort(stalls); // Sort stalls O(nlogn)

        int left = 1; // Minimum possible distance
        int right = stalls[stalls.length - 1] - stalls[0]; // Maximum possible distance
        int maxDistance = 0;
    

        while(left<= right){

            int mid=left+(right-left)/2;

            if(canPlaceCows(stalls,cows,mid)){
                //find a larger distance
                maxDistance=mid;
                left=mid+1;
            }else{
                //find a smaller distance
                right=mid-1;

            }
        }

        return maxDistance;
    }

   private static boolean canPlaceCows(int[] stalls, int cows, int minDist) {
        int placedCows = 1;            // Place first cow in the first stall
        int lastPos = stalls[0];  // Last placed cow

        for (int i = 1; i < stalls.length; i++) {
            // MaxMin we have to check if the lastPos and current position is at least as big as minDist
            // Note: we cannot invent new stall positions along the path. Stalls are fixed at those locations.
            if (stalls[i] - lastPos >= minDist) {
                placedCows++;
                lastPos = stalls[i];
                if (placedCows == cows) return true; // Successfully placed all cows
            }
        }

        return false; // Not enough cows could be placed
    }

    public static void main(String[] args) {
        int[] stalls = {1, 2, 4, 8, 9};
        int cows = 3;
        System.out.println("Max minimum distance: " + maxDistanceBetweenCows(stalls, cows));
        // Output: 3
    }
}
