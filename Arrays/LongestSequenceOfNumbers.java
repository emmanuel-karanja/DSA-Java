package Arrays;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
/**Given an unsorted array of numbers, return the longest sequence of any given number in the sequence.
 * 
 * INTUTION:
 * 
 * In sequence means they follow each other e.g. 1,2,3,4, etc 
 * We need to keep track of maxStreak, which is the longest stream thus far
 * We will need a set since the array is unsorted, we will use it to determine if we should start
 * a new streak or continue an existing one.
 * 
 * KEY: At each number num[i], determine if we need to extend an existing streak or start a new one.
 */


public class LongestSequenceOfNumbers {

    public static List<Integer> getLongestSequence(int[] nums){
        if(nums==null || nums.length==0){
            throw new IllegalArgumentException("Nums is either empty or null.");
        }

        Set<Integer> numSet=new HashSet<>();
        for(int num: nums){
            numSet.add(num);
        }

        ///iterate over the set and do streaks
        
        int maxStreak=0;
        List<Integer> result=new ArrayList<>();
        for(int num: numSet){
            //check the previous number
            if(!numSet.contains(num-1)){
               int currentStreak=1; //we start a new streak
               int currentNum=num;
               List<Integer> currentSeq=new ArrayList<>();
               currentSeq.add(currentNum);
               //expand
               while(numSet.contains(currentNum+1)){
                   currentStreak++;
                   currentNum++;
                   currentSeq.add(currentNum);
               }

              if(currentStreak>maxStreak){
                maxStreak=currentStreak;
                result=currentSeq;
              }
            }
        }

        return result;
    }

    public static void main(String[] args){
        int[] numbers={100,4,200,1,3,2};
        List<Integer> result=getLongestSequence(numbers);

        //length is simply result.length
        System.out.println(Arrays.toString(result.toArray()));
    }
    
}
