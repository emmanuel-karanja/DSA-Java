package Strings;

/**
 * PROBLEM: Longest Substring with At Least K Repeating Characters
 *
 * Given a string s and an integer k, find the length of the longest substring
 * such that every character in the substring appears at least k times.
 *
 * EXAMPLE:
 * Input: s = "aaabbc", k = 3
 * Output: 3
 * Explanation: The longest substring is "aaa", where 'a' appears 3 times.
 *
 * --------------------------------------------------------------------
 * APPROACH: Sliding Window + Variable Unique Count
 * --------------------------------------------------------------------
 *
 * IDEA:
 * 1. The substring must contain between 1 and 26 unique characters (for lowercase letters).
 * 2. Enumerate the number of unique characters `targetUnique` from 1 to 26:
 *    - Use a sliding window to maintain a substring with exactly `targetUnique` unique characters.
 *    - Track how many of those characters appear at least k times.
 *    - Expand the window if unique characters ≤ targetUnique.
 *    - Shrink the window if unique characters > targetUnique.
 *    - Update max length if all unique characters in the window appear at least k times.
 *
 * COMPLEXITY:
 * - Time: O(26 * N) ≈ O(N)
 * - Space: O(26) ≈ O(1) for frequency count
 */
public class LongestSubstringAtLeastKSlidingWindow {

    public int longestSubstring(String s, int k) {
        int maxLen = 0;
        int n = s.length();

        for (int targetUnique = 1; targetUnique <= 26; targetUnique++) {
            int[] count = new int[26];
            int left = 0;
            int right = 0;
            int unique = 0;
            int countAtLeastK = 0;

            // We either expand or shrink not both
            while (right < n) {
                if (unique <= targetUnique) {
                    int idx = s.charAt(right) - 'a';

                    if (count[idx] == 0) unique++;

                    count[idx]++;

                    if (count[idx] == k) countAtLeastK++;
                    
                    right++;
                } else {  
                    int idx = s.charAt(left) - 'a';

                    if (count[idx] == k) countAtLeastK--;

                    count[idx]--;

                    if (count[idx] == 0) unique--;

                    left++;
                }

                if (unique == targetUnique && unique == countAtLeastK) {
                    maxLen = Math.max(maxLen, right - left);
                }
            }
        }

        return maxLen;
    }

    public static void main(String[] args) {
        LongestSubstringAtLeastKSlidingWindow solver = new LongestSubstringAtLeastKSlidingWindow();
        String s = "aaabbc";
        int k = 3;
        System.out.println(solver.longestSubstring(s, k)); // Output: 3
    }
}