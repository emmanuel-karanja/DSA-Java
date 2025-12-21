package Strings;

import java.util.HashMap;
import java.util.Map;

/**Given a string s and an integer k, return the length of the longest substring that can be obtained by replacing
 * at most k characters to make all characters in the substring the sam.
 * 
 * INTUTION:
 * 
 * You are being asked to find windowSize - mostFreqCharacter > k
 * But you don't know what that character is and you don't need to.
 * 
 * 1. Use a two-pointer where left and right start at 0.
 * 2. Keep track of the most frequent count and awindow size from right left i.e. right-left+1 (inclusive)
 * 3. While windowsize - maxFreq > k  try to reduce the size of the window by moving left.
 * 
 * ALL two-pointer PROBLEMS have one main question you must answer: When do we move any of these pointers?
 */

class MCRResult{
    public int maxLength;
    public int startIndex;

    public MCRResult(int maxLength, int startIndex){
        this.maxLength=maxLength;
        this.startIndex=startIndex;
    }
}
public class MaxCharacterReplacement {
    
    public static MCRResult  getMaxCharReplacement(String s, int k){

        if(s==null || s.isEmpty()){
            throw new IllegalArgumentException("String is null or empty.");
        }

        Map<Character, Integer> freqMap=new HashMap<>();

        int left=0;
        

        int maxFreq=0;
        int maxLength=0;

        int start=0;

        for(int right=0;right < s.length();right++){
            char rightChar=s.charAt(right);
            freqMap.put(rightChar,freqMap.getOrDefault(rightChar,0)+1);  //increment count

            maxFreq=Math.max(maxFreq,freqMap.get(rightChar));  //we'll keep track of the most common character
            //even if we don't know which one it's.
            

            // shrink window until valid, i.e. don't compute windowsize before
            while ((right - left + 1) - maxFreq > k) {   // 
                char leftChar = s.charAt(left);
                freqMap.put(leftChar, freqMap.get(leftChar) - 1);
                left++;
            }

            int windowSize = right - left + 1;

            if (windowSize > maxLength) {
                maxLength = windowSize;
                start = left;
            }

        }

        return new MCRResult(maxLength,start);
    }
}
