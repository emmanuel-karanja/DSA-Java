package DP;
/**Houses are arranged in a row in a street. At house[i] we are representing the amount of loot you can
 * rob from house i, you cannot rob two consecutive houses. What's the maximum amount of loot you can rob?
 */
public class RobberI {
    public static int getMaxLootN(int[] houses){
        if(houses.length==0) return 0;

        if(houses.length==1){
            return houses[0];
        }

        int[] ROB=new int[houses.length];
        ROB[0]=houses[0];
        ROB[1]=Math.max(ROB[0],houses[1]);
        for(int i=2;i<houses.length;i++){
            // either don'trob the current house or rob it and the loot for two houses down
            ROB[i]=Math.max(ROB[i-1],houses[i]+ROB[i-2]);
        }

        return ROB[ROB.length-1];
    }

    //Does it in O(n) space ,take advantage of the fact that we only need to keep track of the last 2 
    // loots. i.e. prev2 and prev1
    public static int getMaxLootOptimal(int[] houses){
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
}
