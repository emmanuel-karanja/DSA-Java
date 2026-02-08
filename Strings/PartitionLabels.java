package Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Given a string s, of lowercase letters,partition the String into as many parts as possible so that each letter
 * appears in at most one part. Return a list of integers representing the size of these parts.
 * 
 * INTUTION:
 * 
 * If a character appears multiple times, all its occurences must be contained within a single partition.
 * 
 * We can store the last index of the occurence of a given character.
 * 
 * In each iteration, we ask; Is the current character at i still in the same partition or do we start
 * a new partition? How? check if it's ending is greater than the currentending
 * 
 * We know that a partition potentially ends there. Think of a partition as a window much in the same way we consider a window in
 * Jump Game II.
 * 
 * Each ending char in the map is a potential end of partition. So you compare it with the ending at this point.
 */
public class PartitionLabels {

    public static int[] getPartitionCount(String s){
        if(s==null || s.length()==0){
            throw new IllegalArgumentException("String is null or empty.");
        }

        Map<Character,Integer> charLastIndexMap=new HashMap<>();

        //store last index of a given character, we know a partition potentially ends here.
       for(int i=0;i<s.length();i++){
         charLastIndexMap.put(s.charAt(i),i);
       }

       List<Integer> result=new ArrayList<>();
       int start=0;
       int end=0;

      /** What we are really asking is does the character at i have an occurence further down the chain? And if so, 
       *  we extend the current window. And if we find that we are at the last index we could reach further(the furthest
       *  occurence of any of the character's we've met so far), we know we need to start a new partition.
       */
       for(int i=0;i<s.length();i++){
            int endCurrentChar=charLastIndexMap.get(s.charAt(i));
            // If we had one with a higher ending as a part of the current substring, we'd not update the end
            end=Math.max(end, endCurrentChar);
            //check if a partition potentially ends here. Think of it as a window much in the jump game 2. We've a window
            //and then we keep exploring if we find an ending that  ends here we update start.
            if(i==end){
                result.add(end-start+1);
                start=i+1;// start new partition at i+1
            }
       }

       return result.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();
    }

    public static void main(String[] args){
        String s = "ababcbacadefegdehijhklij";
        System.out.println(Arrays.toString(getPartitionCount(s)));

    }
    
}
