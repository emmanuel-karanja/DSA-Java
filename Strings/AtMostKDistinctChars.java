package Strings;

import java.util.HashMap;
import java.util.Map;

/**Given a string S and an integer k , find the lenggth of the longest substring that contains at most  k distinct chars.
 * 
 * INTUTION:
 * 
 * We only need to keep track of the distinct characters regardless of the the count.
 * Two pointer is suggested.
 * 
 * 
 * if the map.size() > k , try to shrink the window. i.e. get leftchar decrement count in
 *  mapif count is 0 after decrement remove char,a dvnace left.
 */
public class AtMostKDistinctChars {
    public static int getLengthKDistinct(String s, int k){
        if(s==null || s.isEmpty()){
            throw new IllegalArgumentException("String is null or empty.");
        }

        Map<Character,Integer> charMap=new HashMap<>();

        int left=0;
        int maxLength=0;

        for(int right=0;right < s.length();right++ ){
            char rightChar=s.charAt(right);

            charMap.put(rightChar, charMap.getOrDefault(rightChar,0)+1);

            // At most k distinct
            while(charMap.size() > k ){
                //try to shrink
                char leftChar=s.charAt(left);
                
                charMap.put(leftChar, charMap.get(leftChar)-1);

                if(charMap.get(leftChar)==0){
                    charMap.remove(leftChar); // we remove the chars whose count falls to 0
                }
                left++;
            }

            maxLength=Math.max(maxLength,right-left+1);
        }

        return maxLength;
    }
}
