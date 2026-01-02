/**Given a substring s, find the minimum number of insertions to make it a palindrome:
 GOAL: Find the minimum number of insertions to make it a palindrome.
 STATE: DP[i][j] the minimum number of insertions to make the substring between i..j a palindrome. 
 
 CHOICES: at each i and j if s[i]==s[j] we don't do any operations.
          d[i][j]=dp[i+1][j-1]; //the substring is contained berween i and j and hence i+1, j-1 is inside

          and if s[i]!=s[j]  we have to do an insertion on either the left side(i side) or the right side( j side).
            dp[i][j]=1+min(dp[i+1][j],dp[i][j-1])

BASE CASE:
  i==j
  dp[i][j]=0; //no operations needed
  

ORDER OF OPERATIONS:
  The state depends on dp[i+1][j-1], dp[i+1][j] and dp[i][j-1] which depend on SMALLER intervals.
  Short strings first.

SOLUTION:
 This is an interval project and we want to find between i and j so
 solution is DP[0][n-1]
 
 */

 public class MinInsertionsPalindrome{
    public static int minInsertions(String s){
      //get length
      final int n=s.length();

      if(n<=1) return 0;

      int[][]  dp=new int[n][n];

      for(int i=n-1;i>=0;i--){
        for(int j=i;j<n;j++){
           //if it's a plaindrome we do nothin
          if(s.charAt(i)==s.charAt(j)){
               dp[i][j]=dp[i+1][j-1]; // No operation
          }else{
               dp[i][j]=1+ Math.min(dp[i+1][j],dp[i][j-1]);
          }
        }
      }
      return dp[0][n-1];
    }
 }