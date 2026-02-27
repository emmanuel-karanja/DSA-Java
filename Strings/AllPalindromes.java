package Strings;

import java.util.*;

/**
 * Given a string s, count all palindromic substrings and optionally
 * collect them, while also tracking the longest palindrome.
 *
 * INTUITION:
 * Use expand-around-center for odd and even length palindromes.
 * - For odd length: center at a single character
 * - For even length: center between two characters
 * While expanding:
 *   - record the palindrome: s[left..right]
 *   - update the longest palindrome if current is longer
 *
 * TIME COMPLEXITY: O(n^2)
 * SPACE COMPLEXITY:
 *    - O(1) if only counting / longest
 *    - O(n^2) if storing all palindromes
 */
public class AllPalindromes {

    public static class Result {
        int count;
        String longest;
        List<String> allPalindromes;

        Result(int count, String longest, List<String> allPalindromes) {
            this.count = count;
            this.longest = longest;
            this.allPalindromes = allPalindromes;
        }
    }

    public static Result getPalindromeCount(String s) {
        if (s == null || s.length() == 0) {
            throw new IllegalArgumentException("String is empty or null.");
        }

        int count = 0;
        String longest = "";
        List<String> allPalindromes = new ArrayList<>();

        for (int i = 0; i < s.length(); i++) {
            // Odd length palindromes
            count += expandAroundCenter(s, i, i, allPalindromes);
            // Even length palindromes
            count += expandAroundCenter(s, i, i + 1, allPalindromes);

            // Update longest palindrome found so far
            if (!allPalindromes.isEmpty()) {
                String last = allPalindromes.get(allPalindromes.size() - 1);
                if (last.length() > longest.length()) {
                    longest = last;
                }
            }
        }

        return new Result(count, longest, allPalindromes);
    }

    private static int expandAroundCenter(String s, int left, int right, List<String> allPalindromes) {
        int count = 0;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            // Record current palindrome
            String palindrome = s.substring(left, right + 1);
            allPalindromes.add(palindrome);

            count++;
            left--;
            right++;
        }
        return count;
    }

    public static void main(String[] args) {
        String test = "ababa";
        Result result = getPalindromeCount(test);

        System.out.println("Total palindromes: " + result.count);
        System.out.println("Longest palindrome: " + result.longest);
        System.out.println("All palindromes: " + result.allPalindromes);
    }
}