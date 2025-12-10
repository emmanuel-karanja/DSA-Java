package DP;
/**Longest Palidromic Subsequence:
 *  
 * INTUTION:
 * 
 * It's the LCS with the s1 and s1Reversed.
 */
public class LPS {
    public static int longestCommonSubsequence(String s1, String s2){
        final int m=s1.length();
        final int n=s2.length();

        //prefil a 2d matrix with 0, java by default prefills it with 0s.
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

    public static int getLPS(String s1){
        StringBuilder sb=new StringBuilder();
        for(int i=s1.length()-1;i>= 0;i--){
            sb.append(s1.charAt(i));
        }

        String s2=sb.toString();

        return longestCommonSubsequence(s1, s2);

    }
}
