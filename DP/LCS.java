package DP;

/**Given two strings s1, and s2,find the longest common subsequence. 
 * 
 * INTUTION
 * Basically
 * 
 * We only increase the length of LCS if s1[i]==s2[j] where i>=0 and j>=0 and i and j are not necessarily equal.
 * 
 * KEY: In any DP problem, especially 2D problem, if we are at position i,j, we only care about the 3 points
 * beforeit i.e. [i-1,j-1],[i-1,j] and [i,j-1] we call it the DEPENDENCY SQUARE
 */
public class LCS {

    public static int longestCommonSubsequence(String s1, String s2){
        final int m=s1.length();
        final int n=s2.length();

        //prefil a 2d matrix with 0, in Java we don't need to prefill since it's prefilled by default.
        int[][] dp=new int[m+1][n+1];


        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                //weonly increase if s1[i]==s2[j]
                if(s1.charAt(i-1)==s2.charAt(j-1)){
                    dp[i][j]=1+dp[i-1][j-1];
                }else{
                    //we explore the two diagonally previous prositions with [i-1][j] and [i][j-1]
                    //in the recurrence relation, the length of the lcs at currrent i,j is the maximum of the two preceeding
                    //ones  i.e. +---------+---------+
                    ///          | (i-1,j-1) | (i-1,j)|
                                //+---------+---------+
                                //| (i,j-1)   |  (i,j) |
                                //+---------+---------+

                    // We don't bother with i-1,j-1 since it was calculated in the last step. 
                    //
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args){
       String s1="abcde";
       String s2="ace";

       System.out.println(longestCommonSubsequence(s1, s2));
    }
    
}
