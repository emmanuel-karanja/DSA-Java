package Strings;
/**Find the smallest contiguous substring in a given String s,that contains all the characters of another string t. */

import java.util.HashMap;
import java.util.Map;

/**INTUTION:
 * 
 * Ensure that t.length<=s.length first
 * 
 * Iterate over t, and for each character keep a key-val pair of char->count mapping
 * required uniqueChars will be the map.size() of that.
 * 
 * Using a two pointer approach (sliding window) expand a substring from 0 s.length-1
 * at each expansation each that the enclosed window between left and right contains all the required
 * characters i.e. uniqueChars count and the count of each unique char
 * if it does, try to reduce the size of this window by advancing the left pointer while keeping
 * the required elements
 * If the window containing is found record the right-left+1 as the length of the sub-window
 */
public class MinimumWindowSubstring {

    public static int getMinWindowSubstringLength(String s, String t){
        if(t==null || s==null || t.trim().length()==0 || s.trim().length()==0){
           throw new IllegalArgumentException("Check that s or t are neither null nor empty strings");
        }

        if(s.length() < t.length()){
            throw new IllegalArgumentException("S must be longer than t.");
        }

        //map the chars of t
        Map<Character,Integer> charMap = new HashMap<>();

        for(char c: t.toCharArray()){
            charMap.put(c,charMap.getOrDefault(c,0)+1);
        }

        final int required=charMap.size();
        int formed=0;
        int left=0;
        int right=0; //we start from the same side and expand

        Map<Character,Integer> windowMap=new HashMap<>();
        
        int minWindowSize=Integer.MAX_VALUE;

        while(right<s.length()){
            char rightChar=s.charAt(right);
            //add to window
            windowMap.put(rightChar,windowMap.getOrDefault(windowMap, 0)+1);

            //check that we have required===formed
            if(charMap.containsKey(rightChar) && 
                charMap.get(rightChar).intValue()==windowMap.get(rightChar).intValue()){
                formed++;
            }

            //check if we have gotten all of them and try to shrink the wondow
            while(formed==required){
                //we have a window
                int currentWindow=right-left+1; //inclusive so we add 1
                minWindowSize=Math.min(currentWindow,minWindowSize);

                //try to shrink the window
                char leftChar=s.charAt(left);

                //remove the char from the window
                windowMap.put(leftChar, windowMap.get(leftChar)-1); 
                //we recheck the condition, do we have leftChar in the charMap?
                if(charMap.containsKey(leftChar) &&
                   windowMap.getOrDefault(leftChar,0).intValue() < charMap.get(leftChar).intValue()){
                    formed--;
                }

                left++; //advance left
            }

            //advance right
            right++;
        }

        return minWindowSize !=Integer.MAX_VALUE? minWindowSize: -1;
    }

    public static void main(String[] args){
        String s="ADOBECODEBANC";
        String t="ABC";

        System.out.println(getMinWindowSubstringLength(s, t));
    }    
}
