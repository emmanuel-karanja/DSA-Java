package Strings;

/**
 * PROBLEM: Longest Substring with At Least K Repeating Characters
 * 
 * Given a string s and an integer k, find the length of the longest substring 
 * such that every character in the substring appears at least k times.
 * 
 * EXAMPLE:
 * Input: s = "aaabb", k = 3
 * Output: 3
 * Explanation: The longest substring is "aaa", where 'a' appears 3 times.
 * 
 * RUBRIC-STYLE BREAKDOWN:
 * 1. GOAL: Find the length of the longest substring where all characters appear at least k times.
 * 2. CHALLENGE: "At least k" condition is not monotonic with substring length.
 * 3. INSIGHT: Fix the number of unique characters (targetUnique) in the substring.
 *    - Substrings with exactly targetUnique unique characters allow monotonic checking.
 * 4. APPROACH:
 *    - Iterate targetUnique from 1 to 26 (lowercase letters).
 *    - Use a sliding window with:
 *        a) uniqueInWindow: unique characters in window
 *        b) countAtLeastK: number of characters appearing ≥ k
 *    - Expand right pointer, shrink left if uniqueInWindow > targetUnique.
 *    - Update maxLen when uniqueInWindow == targetUnique && countAtLeastK == targetUnique.
 * 5. COMPLEXITY:
 *    - Time: O(26 * N) ≈ O(N), Space: O(26) = O(1)
 */

public class LongestWithAtLeastK {
    public int longestSubstring(String s, int k) {
        int maxLen = 0;
        int n = s.length();

        // Iterate through all possible numbers of unique characters (1 to 26)
        for (int targetUnique = 1; targetUnique <= 26; targetUnique++) {
            int[] freq = new int[26];
            int left = 0, right = 0;
            int uniqueInWindow = 0;
            int formed = 0;

            while (right < n) {
                // -------------------------------
                // 1. EXPAND window by including s[right]
                // -------------------------------
                int rightChar = s.charAt(right) - 'a';   //Gives us the index in the ref.

                if (freq[rightChar] == 0) {
                    uniqueInWindow++; // first occurrence of this char in window
                }

                freq[rightChar]++; // increment frequency count of that character

                if (freq[rightChar] == k) {  // check if it has made it
                    formed++; // now this char satisfies at least k
                }
                right++; // move right pointer

                // -------------------------------
                //  window if unique > targetUnique
                // -------------------------------
                while (uniqueInWindow > targetUnique) {
                    int leftChar = s.charAt(left) - 'a';

                    // 
                    if (freq[leftChar] == k) {
                        formed--; // removing this char breaks "at least k"
                    }
                    freq[leftChar]--; // decrement frequency

                    if (freq[leftChar] == 0) {
                        uniqueInWindow--; // char completely removed from window
                    }
                    left++; // move left pointer
                }

                // -------------------------------
                //validate window
                // -------------------------------
                if (uniqueInWindow == targetUnique && uniqueInWindow == formed) {
                    maxLen = Math.max(maxLen, right - left);
                }
            }
        }

        return maxLen;
    }
}
