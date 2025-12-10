package Strings;

import java.util.HashSet;
import java.util.Set;

/**Given a string s, find the longest non-repeating substring in it.
 * 
 * INTUTION
 * 
 * Use expanding two pointers left and right.
 * Use a set to store already encountered characters, if we find that s.charAt(right) is in set, 
 * we delete the s.charAt(left) and advance S one more step. And add the s.charAt(right)
 */
public class LongestNonRepeating {

    public static String getLongestNonRepeating(String str){
        if(str==null || str.length()==0) return "";

        Set<Character> charSet=new HashSet<>();

        int left=0;
        int right=0;

        int maxLength=0;
        int startIndex=0;

        while(right<str.length()){
            while(charSet.contains(str.charAt(right))){
                //shorten
                char leftChar=str.charAt(left);
                left++;
                charSet.remove(leftChar); //a set has only unique characters.
            }

            charSet.add(str.charAt(right));

            int windowLength=right-left+1;
            
            if(windowLength>maxLength){
                startIndex=left;
                maxLength=windowLength;
            }

            right++;
        }

        

        return str.substring(startIndex,startIndex+maxLength);
    }

    public static void main(String[] args) {

        String[] testCases = {
            "abcabcbb",
            "bbbbb",
            "pwwkew",
            "abba",
            "dvdf",
            "a",
            "",
            null
        };

        for (String input : testCases) {
            String result = LongestNonRepeating.getLongestNonRepeating(input);

            System.out.println("Input: " + input);
            System.out.println("Longest non-repeating substring: \"" + result + "\"");
            System.out.println("Length: " + result.length());
            System.out.println("--------------------------------");
        }
    }   
}
