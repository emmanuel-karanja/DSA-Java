package Backtracking;

import java.util.*;

public class WordBreakMemo {

    // Wordbreak algorithm with backtracking and memoization, a canonical example of how to do memoization
    // memoization in bactracking only works if the candidate is not unique. For permutation, the validation step
    // before bactracking ensures that the state is unique so, it doesn't benefit from memoization.
    public static boolean canBreak(String s, Set<String> dict) {
        Boolean[] memo = new Boolean[s.length()];
        return dfsCanBreak(s, 0, dict, memo);
    }

    private static boolean dfsCanBreak(String s, int start, Set<String> dict, Boolean[] memo) {
        if (start == s.length()) return true;

        //  State pruning - memoization
        if (memo[start] != null) return memo[start];

        for (int end = start + 1; end <= s.length(); end++) {
            String prefix = s.substring(start, end);

            if (dict.contains(prefix)) {
                if (dfsCanBreak(s, end, dict, memo)) {
                    memo[start] = true;
                    return true;
                }
            }
        }

        memo[start] = false;
        return false;
    }

    // With word enumeration
    public static List<String> allSentences(String s, Set<String> dict) {
        Map<Integer, List<String>> memo = new HashMap<>();
        return dfsSentences(s, 0, dict, memo);
    }

    private static List<String> dfsSentences(String s, int start, Set<String> dict,
                                             Map<Integer, List<String>> memo) {

        // memo check
        if (memo.containsKey(start)) return memo.get(start);

        List<String> result = new ArrayList<>();

        if (start == s.length()) {
            result.add(""); // base for concatenation
            return result;
        }

        for (int end = start + 1; end <= s.length(); end++) {
            String prefix = s.substring(start, end);

            if (!dict.contains(prefix)) continue;

            List<String> suffixWays = dfsSentences(s, end, dict, memo);

            for (String suffix : suffixWays) {
                if (suffix.isEmpty()) {
                    result.add(prefix);
                } else {
                    result.add(prefix + " " + suffix);
                }
            }
        }

        // Observe result
        memo.put(start, result);
        return result;
    }

   
    public static void main(String[] args) {

        String s1 = "leetcode";
        Set<String> dict1 = new HashSet<>(Arrays.asList("leet", "code"));

        System.out.println("Can Break (leetcode): " + canBreak(s1, dict1));

        String s2 = "catsanddog";
        Set<String> dict2 = new HashSet<>(Arrays.asList(
                "cat", "cats", "and", "sand", "dog"
        ));

        System.out.println("\nAll Sentences (catsanddog):");
        List<String> sentences = allSentences(s2, dict2);

        for (String sentence : sentences) {
            System.out.println(sentence);
        }

        //  Stress test (shows memo impact)
        String s3 = "aaaaaaa";
        Set<String> dict3 = new HashSet<>(Arrays.asList("a", "aa", "aaa"));

        long startTime = System.nanoTime();
        System.out.println("\nCan Break (aaaaaaa): " + canBreak(s3, dict3));
        long endTime = System.nanoTime();

        System.out.println("Time taken: " + (endTime - startTime) + " ns");
    }
}