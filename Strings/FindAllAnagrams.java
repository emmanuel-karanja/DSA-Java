package Strings;

import java.util.ArrayList;
import java.util.List;

/**Given a string s, and a smaller string p, find all the starting indices in s where the an anagram
 * of p begins.
 * 
 * INTUTION:
 * 
 * Sort p.
 * Set a window of maxsize p and slide it over s,  and in each step, sort the subwindow and compare for a match with
 * sorted p. Once find an anagram, add left to the array of starting indices
 * 
 * you'll encounter a klog(k) where k is p.size() so the complexity is nk(logk). 
 * 
 * HOW TO DO AWAY WITH SORTING
 * 
 * Create a 26 char window that keeps track of chars of p and their count depending on the letter of the alphabet.
 * slide a window over s and computer a window of that and compare.
 * 
 * O(n) since you do a single pass
 */

public class FindAllAnagrams {

    public static List<Integer> getAnagramStartingIndices(String s, String p) {
        if (s == null || p == null || s.length() == 0 || p.length() == 0) {
            throw new IllegalArgumentException("S or P is either null or empty.");
        }

        if (p.length() > s.length()) {
            throw new IllegalArgumentException("P cannot be longer than S.");
        }

        final int PLENGTH = p.length();
        List<Integer> result = new ArrayList<>();

        int[] pCount = new int[26];
        int[] windowCount = new int[26];

        final int aCode = (int) 'a';

        // Count chars in p
        for (int i = 0; i < PLENGTH; i++) {
            //this op gives you a number between 0 and 25
            pCount[p.charAt(i) - aCode]++;
        }

        // Initialize the first window over s
        for (int i = 0; i < PLENGTH; i++) {
            windowCount[s.charAt(i) - aCode]++;
        }

        // Check first window
        if (arraysMatch(pCount, windowCount)) result.add(0);

        // Slide the window over s
        for (int i = PLENGTH; i < s.length(); i++) {
            // Add new right-side char
            windowCount[s.charAt(i) - aCode]++;

            // Remove left-side char
            int leftCharPos = i - PLENGTH;   //exclusive think of when PLENGTH=i i.e. 0. 
            windowCount[s.charAt(leftCharPos) - aCode]--;

            // Compare
            if (arraysMatch(pCount, windowCount)) {
                result.add(leftCharPos + 1);
            }
        }

        return result;
    }

    private static boolean arraysMatch(int[] arr1, int[] arr2){
        for(int i=0;i<26;i++){
            if(arr1[i]!=arr2[i]){
                return false;
            }
        }
       return true;
    }

    public static void main(String[] args){
        String s="cbazabczbabaczd";
        String p="abcz";

        for(Integer i: getAnagramStartingIndices(s, p)){
            System.out.println(i);
        }
    }
}
