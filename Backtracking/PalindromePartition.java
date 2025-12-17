package Backtracking;

import java.util.ArrayList;
import java.util.List;

/**Given a string s, partition it into all possible lists of substrings such that each substring is a palindrome.
 * s = "aab"
Output: [["a","a","b"], ["aa","b"]]

 INTUTION:
 Much like word break but this time check if the generated is a substring. Partition to me means that
 once we pick a character for a given substring, it will notbe used in the other substring if it passes, we try partitioning
 the remainder, partition also means that if s[0...i-1] is a palindrome, we now need to check if s[i...n] is too
 */

public class PalindromePartition {

    private static boolean isPalindrome(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) return false;
            start++;
            end--;
        }
        return true;
    }

    private static void backtrack(String s, int start, List<String> path, List<List<String>> result) {
        if (start == s.length()) {
            // Found a valid partition
            result.add(new ArrayList<>(path));
            return;
        }

        for (int end = start; end < s.length(); end++) {
            if (isPalindrome(s, start, end)) {
                // Pick current substring
                path.add(s.substring(start, end + 1));
                // Recurse for the remaining string
                backtrack(s, end + 1, path, result);
                // Backtrack / remove the last choice
                path.remove(path.size() - 1);
            }
        }
    }

    public static List<List<String>> getPartitions(String s) {
        List<List<String>> result = new ArrayList<>();
        backtrack(s, 0, new ArrayList<>(), result);
        return result;
    }

    public static void main(String[] args) {
        String s = "aab";
        List<List<String>> partitions = getPartitions(s);
        for (List<String> p : partitions) {
            System.out.println(p);
        }
    }
}
