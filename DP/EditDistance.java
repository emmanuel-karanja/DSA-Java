package DP;
/**Given two words word1 and word2, find the minimum number of operations to convert word1 into word2.
Allowed operations:
 1. Insert a character
 2. Delete a character
 3. Replace a character.
 
 BREAKDOWN
 Goal: Find the minimum number of operations be it insert,delete or replace a character
 Choice: Do we do an operation here and which do we do? i.e. min(the min of delete, min of inset and min of
 replace)+1 i.e. i.e. current state needs updating only if the second part is true ie minof all mins of
 delete,insert,replace for the sub-problem at i-1 or j-1
 Recurrence relation: arises natural from the choice i.e. dp[x]=Min(dp[x],1+min(delete,replace,insert))
 BASE CASE: When s1.length==0 or s2.length==0*/
public class EditDistance {

     // Recursive function to find edit distance
    public static int editDistanceResursive(String s1, String s2, int m, int n) {
        // Base cases
        if (m == 0) return n; // If s1 is empty, insert all characters of s2
        if (n == 0) return m; // If s2 is empty, remove all characters of s1

        // If last characters are the same, ignore last char
        if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
            return editDistanceResursive(s1, s2, m - 1, n - 1);
        }

        // If last characters are different, consider all three operations
        int insertOp = editDistanceResursive(s1, s2, m, n - 1);
        int deleteOp = editDistanceResursive(s1, s2, m - 1, n);
        int replaceOp = editDistanceResursive(s1, s2, m - 1, n - 1);

        return 1 + Math.min(insertOp, Math.min(deleteOp, replaceOp));
    }

    public static int editDistanceDP(String s1, String s2) {

        //DP[i][j] how to convert the first i characters of s1 to first j characters of s2
        int m = s1.length();
        int n = s2.length();

        int[][] dp = new int[m + 1][n + 1];

        // Initialize base cases
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i; // Deleting all characters from s1
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j; // Inserting all characters of s2
        }

        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // Characters match, no operation
                } else {
                    int deleteOp = dp[i - 1][j];
                    int insertOp = dp[i][j - 1];
                    int replaceOp = dp[i - 1][j - 1];
                    dp[i][j] = 1 + Math.min(deleteOp, Math.min(insertOp, replaceOp));
                }
            }
        }

        return dp[m][n];
    }
    
}
