package Strings;
import java.util.*;

/*

Problem:

Given a string s and an array of words, return all starting indices of substrings
in s that are a concatenation of all words exactly once in any order.

All words have the same length.

Example:
s = "barfoothefoobarman"
words = ["foo", "bar"]

Output: [0, 9]


Approach:

- Use sliding window with word-sized steps
- Use frequency maps to track required vs seen words
- Use multiple windows starting at offsets [0 .. wordLen - 1]
*/

public class SubstringWithConcatenationOfAllWords {

    public static List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (s == null || words == null || words.length == 0) return result;

        int wordLen = words[0].length();
        int wordCount = words.length;
        int totalLen = wordLen * wordCount;

        if (s.length() < totalLen) return result;

        // Frequency of required words
        Map<String, Integer> wordFreq = new HashMap<>();
        for (String w : words) {
            wordFreq.put(w, wordFreq.getOrDefault(w, 0) + 1);
        }

        // Try each offset
        for (int offset = 0; offset < wordLen; offset++) {
            int left = offset;
            int count = 0;
            Map<String, Integer> windowFreq = new HashMap<>();

            for (int right = offset; right + wordLen <= s.length(); right += wordLen) {
                String word = s.substring(right, right + wordLen);

                if (wordFreq.containsKey(word)) {
                    windowFreq.put(word, windowFreq.getOrDefault(word, 0) + 1);
                    count++;

                    // Too many occurrences → shrink window
                    while (windowFreq.get(word) > wordFreq.get(word)) {
                        String leftWord = s.substring(left, left + wordLen);
                        windowFreq.put(leftWord, windowFreq.get(leftWord) - 1);
                        left += wordLen;
                        count--;
                    }

                    // All words matched
                    if (count == wordCount) {
                        result.add(left);
                    }
                } else {
                    // Reset window
                    windowFreq.clear();
                    count = 0;
                    left = right + wordLen;
                }
            }
        }

        return result;
    }

    // -------------------------------------------------------------------------
    // Driver
    // -------------------------------------------------------------------------
    public static void main(String[] args) {
        String s1 = "barfoothefoobarman";
        String[] words1 = {"foo", "bar"};
        System.out.println(findSubstring(s1, words1)); // [0, 9]

        String s2 = "wordgoodgoodgoodbestword";
        String[] words2 = {"word", "good", "best", "word"};
        System.out.println(findSubstring(s2, words2)); // []

        String s3 = "barfoofoobarthefoobarman";
        String[] words3 = {"bar", "foo", "the"};
        System.out.println(findSubstring(s3, words3)); // [6, 9, 12]
    }
}