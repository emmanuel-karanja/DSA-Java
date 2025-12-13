package DP;
/**Given a string s, partition it into all possible lists of substrings such that each substring is a palindrome.
 * s = "aab"
Output: [["a","a","b"], ["aa","b"]]
KEYS:

1. Precompute the palindrome dp array to check partitions.*/
import java.util.*;

public class PalindromePartitionDP {

    public static List<List<String>> partition(String s) {
        int n = s.length();
        boolean[][] isPal = new boolean[n][n];

        // Precompute palindrome table, we are checking to to see if substring(i,j) is a palidrome here
        // Now j>=i and we start from the end to avoid repeated computations. if we are at i,j, we check
        // if the inner substring from i+1 to j-1 is a palidrome as well
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i <= 2 || isPal[i + 1][j - 1])) {
                    isPal[i][j] = true;
                }
            }
        }

        // dp[i] = list of partitions of s[i..n-1]
        List<List<String>>[] dp = new ArrayList[n + 1];
        dp[n] = new ArrayList<>();
        dp[n].add(new ArrayList<>()); // base case: empty string

        for (int i = n - 1; i >= 0; i--) {
            dp[i] = new ArrayList<>();
            for (int j = i; j < n; j++) {
                if (isPal[i][j]) {
                    String pal = s.substring(i, j + 1);
                    for (List<String> list : dp[j + 1]) {
                        List<String> newList = new ArrayList<>();
                        newList.add(pal);
                        newList.addAll(list);
                        dp[i].add(newList);
                    }
                }
            }
        }

        return dp[0];
    }

    public static void main(String[] args) {
        String s = "aab";
        List<List<String>> res = partition(s);
        for (List<String> part : res) {
            System.out.println(part);
        }
    }
}
