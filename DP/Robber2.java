package DP;

import java.util.Arrays;

/**Houses are arranged in a row in a street. At house[i] we are representing the amount of loot you can
 * rob from house i, you cannot rob two consecutive houses. What's the maximum amount of loot you can rob?
 * Now houses are in a culdesac (i.e. arranged circularly)
 * 
 * INTUTION:
 * Use the linear one and either start at the house at index 0 or house at index 1
 */
public class Robber2 {

    //Does it in O(n) space ,take advantage of the fact that we only need to keep track of the last 2 
    // loots. i.e. prev2 and prev1
    public static int getMaxLootLinear(int[] houses){
        if(houses.length==0) return 0;

        if(houses.length==1){
            return houses[0];
        }

       
        int prev2=houses[0];
        int prev1=Math.max(prev2,houses[1]);
        for(int i=2;i<houses.length;i++){
            // either don'trob the current house or rob it and the loot for two houses down
           int current=Math.max(prev1,houses[i]+prev2);

           prev2=prev1;
           prev1=current;
        }

        return prev1;
    }
    
    public static int robCuldesac(int[] houses){
        //skip last house and skip first house

        if (houses.length == 0) return 0;
        if (houses.length == 1) return houses[0];
        if (houses.length == 2) return Math.max(houses[0], houses[1]);

        int maxLoot=0;
        //copyOfRange is exclusive of the last index.
        int[] slice1=Arrays.copyOfRange(houses,0,houses.length-1); //  0 to n-2
        int[] slice2=Arrays.copyOfRange(houses,1,houses.length); //1 to n-1

        maxLoot=Math.max(getMaxLootLinear(slice1),getMaxLootLinear(slice2));

        return maxLoot;
    }
}
