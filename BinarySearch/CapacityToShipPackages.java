package BinarySearch;
/**You’re given:

  weights[] → packages in order (cannot reorder)
   D → number of days

Each day:
   You load packages in order
   Total weight per day ≤ ship capacity

Goal: find the minimum ship capacity so all packages are shipped in D days.

INTUTION:

Lower Bound==>Ship must be able to carry the heaviest package
    lowerBound=max(weights);


Upper Bound==>Ship can carry the sum of all pckages weights
   upperBound=sum(weights)

The capacity to carry in D days lies  between lowerBound and upperBound

Search the space between [lowerBound...upperBound]

*/
public class CapacityToShipPackages {

    public static int getShipCapacity(int[] weights, int days){
        if(weights==null || weights.length==0){
            throw new IllegalArgumentException("Weights empty or null.");
        }

        if(days==0){
            throw new IllegalArgumentException("Days is zero");

        }
        
        //get upperbound
        int lowerBound=0;
        for(int i=0;i<weights.length;i++){
            lowerBound=Math.max(lowerBound,weights[i]);
        }

        int upperBound=0;
        for(int i=0;i<weights.length;i++){
            upperBound+=weights[i];
        }

        //do a binarysearch

       int answer=upperBound;
       while(lowerBound <=upperBound){
         int mid=lowerBound+(upperBound-lowerBound)/2;

           if(canShip(weights,mid,days)){
               answer=mid;
               upperBound=mid-1;
           }else{
               lowerBound=mid+1;
           }
       }

       return answer;
    }

    private static boolean canShip(int[] weights, int maxCapacity, int days) {
        int daysUsed = 1;
        int currentLoad = 0;

        for (int w : weights) {
            //if we exceed capacity even by 1 kg, we need a fresh day
            if (currentLoad + w > maxCapacity) {
                daysUsed++;
                currentLoad = 0;
            }
            //we are still within capacity
            currentLoad += w;

            //check if we exceed required days
            if (daysUsed > days) {
                return false;
            }
        }

        return true;
    }
    
    public static void main(String[] args) {
        int[] packages = {1,2,3,4,5,6,7,8,9,10};
        int D = 5;
        int result = getShipCapacity(packages, D);
        System.out.println("Minimum ship capacity = " + result); // Expected: 15
    }
}
