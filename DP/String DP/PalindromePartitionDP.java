
/**Given a string s, partition it into all possible lists of substrings such that each substring is a palindrome.
 * s = "aab"
Output: [["a","a","b"], ["aa","b"]]
KEYS:

INTUITION:

1. Iterate over the string and create a dp table such dp[i][j] tells if you if the substring between i and j inclusive
   is a palindrome. This is simple the rules are s[i]==s[j] && (j-1<=2 || isPalindrome(dp[i+1][j-1])) what this
   condition means is that a string of length 1 or the inner string between i+1,j-1 is a palindrome.

2. Do another pass using the first DP and a second one with lists of words (actual palindromes). You can do a messy DP
   or simpyl do backtracking to find those palindromes.


*Since the first step is an interval DP problem, we iterate from the end i.e. from n+1 



*/
import java.util.*;

public class PalindromePartitionDP {

    public static List<List<String>> partition(String s) {
        int n = s.length();
        boolean[][] isPal = new boolean[n][n];

        // Precompute palindromes (interval DP)
        for (int left = n - 1; left >= 0; left--) {
            for (int right= left; right < n; right++) {
                if (s.charAt(left) == s.charAt(right) && (right - left <= 2 || isPal[left + 1][right - 1])) {
                    isPal[left][right] = true;
                }
            }
        }

        List<List<String>> res = new ArrayList<>();
        backtrack(0, s, isPal, new ArrayList<>(), res);
        return res;
    }

    private static void backtrack(int start, String s, boolean[][] isPal,
                                List<String> path, List<List<String>> res) {
        if (start == s.length()) {
            res.add(new ArrayList<>(path));  //copy path solutuion and add it to the solution.
            return;
        }

        for (int end = start; end < s.length(); end++) {
            if (isPal[start][end]) {
                path.add(s.substring(start, end + 1)); ///choose character at end+1
                backtrack(end + 1, s, isPal, path, res);
                path.remove(path.size() - 1);  //unchoose it
            }
        }
    }


    public static void main(String[] args) {
        String s = "aab";
        List<List<String>> res = partition(s);
        for (List<String> part : res) {
            System.out.println(part);
        }
    }
}
