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
 * RUBRIC-STYLE REASONING
 * --------------------------------------------------------------------
 *
 * GOAL:
 * Find the length of the longest substring where every character appears
 * at least k times.
 *
 * CHALLENGE:
 * The "at least k" condition is non-monotonic: adding characters to a valid
 * substring can make it invalid, so naive sliding window won't always work.
 *
 * INSIGHT (Divide & Conquer):
 * 1. Any character that occurs less than k times in the current substring
 *    cannot be part of a valid substring.
 * 2. We can split the string at these "bad" characters (frequency < k)
 *    and recursively solve the problem on the segments between them.
 * 3. If there are no bad characters, the whole substring is valid.
 *
 * ALGORITHM:
 * 1. Count character frequencies in the current substring.
 * 2. Scan the substring for characters appearing less than k (splitters).
 * 3. If found, split at those characters and recursively solve each segment.
 * 4. Return the maximum length among all segments.
 * 5. If no splitters, return the length of the substring.
 *
 * COMPLEXITY:
 * - Worst case time: O(N^2) (if many recursive splits)
 * - Average case time: O(N) (if splits are balanced)
 * - Space: O(N) for recursion stack + O(26) for frequency array
 */

public class LongestSubstringAtLeastK {

    public int longestSubstring(String s, int k) {
        return helper(s, 0, s.length(), k);
    }

    private int helper(String s, int start, int end, int k) {
        if (end - start < k) return 0; // substring too short to satisfy k

        // Count character frequency in the current substring
        int[] freq = new int[26];
        for (int i = start; i < end; i++) {
            freq[s.charAt(i) - 'a']++;
        }

        // Scan for splitters: characters with frequency < k
        for (int i = start; i < end; i++) {
            if (freq[s.charAt(i) - 'a'] < k) {
                int j = i + 1;
                // Skip consecutive splitters
                while (j < end && freq[s.charAt(j) - 'a'] < k) j++;
                // Recursively solve left and right segments
                return Math.max(helper(s, start, i, k), helper(s, j, end, k));
            }
        }

        // All characters appear at least k times → valid substring
        return end - start;
    }

    public static void main(String[] args) {
        LongestSubstringAtLeastK solver = new LongestSubstringAtLeastK();
        String s = "aaabbc";
        int k = 3;
        System.out.println(solver.longestSubstring(s, k)); // Output: 3
    }
}