package Arrays;

import java.util.Arrays;

/**Given an array of positive integers where the people[i] is the weight of the person at position i. These people
 * are trapped in a wreckage. There are rescue boats.
 * Each rescue boat can carry at most 2 people at a time with a maximum weight capacity of limit for each boat.
 * Find the minimum number of rescue boats to required to rescue these people.
 * 
 * INTUTION:
 * 
 * Pair a heavy person with a lighter person. i.e. the heaviest with the lightest provided the weight doesn't
 * exceed limit, if no lighter person is found carry the heavy one.
 */
public class RescueBoats {

    public static int getBoatCount(int[] people, int limit){
        if(people==null || people.length==0){
            throw new IllegalArgumentException("People array cannot be zero or null.");
        }

        if(limit<=0){
            throw new IllegalArgumentException("Weight limit must be positive.");
        }
        //EDGE CASE: A person exceeds the weight limit hence it's impossible to rescue all of them.
         for (int weight : people) {
            if (weight > limit) {
                throw new IllegalArgumentException(
                    "Person with weight " + weight + " exceeds the boat limit of " + limit
                );
            }
        }

        int boatCount=0;
        //sort the array to be able to do proper matching
        Arrays.sort(people);

        int left=0;
        int right=people.length-1;
        while(left <=right){
            //pair heavier and lighter
            if(people[left]+people[right]<=limit){
                left++;
            }
            //else always carry the heavier person
            right--;
            boatCount++;
        }

        return boatCount;
    }

    public static void main(String[] args){
        int[] people={3,2,2,1};
        int limit=3;

        System.out.println(getBoatCount(people, limit));
    }
    
}
