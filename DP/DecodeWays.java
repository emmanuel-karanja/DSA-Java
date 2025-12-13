package DP;
/**A message containing letters A-Z is encoded into numbers using the mapping
 *  A->1, B->2,C->3 ... Z->26. 
 * 
 * Given a string s containing only digits, return the number of ways to decode it.
 * 
 * INTUITION
 * 
 * I have some rules the value of each break cannot exceed '26' or any of them be '0'. If we had 226
 * 
 * we can 2,2,26 or 22,6 or 2,2,6
 * i.e. how we decode the remainder depends on the previous state e.g. if we pick 22, it affects how we decode the remainder
 * or if wepick 2, it does influence how e can decode the remaining 26 but once again the rule holds
 * 
 * NOTE: initial instinct I had of breaking the stringso that no single value is equal to '0' or greater than '26'
 * is actually correct. But it'd answer the what those splits are and not how many. It was recursive and sort of
 * backtracking. 
 * 
 * So backtracking is when you are asked WHAT ,and DP is suggested by underlining HOW MANY or COUNT.
 */
public class DecodeWays {
    
    public static int getWaysToDecode(String s){
        final int n=s.length();

        int[] dp= new int[n+1]; //auto filled with 0

        dp[0]=1; //there is only way one way to decode a string of length 1.

        //dp[i] is basically, ways to decode the first i characters of s. i.e. s[0...i-1]
        
        for(int i=1;i<=n;i++){

            //how many ways can I decode one digit ending at i? it's whatever dp[i] holds (most likely 0)
            //and the ways to decode characters ending at i-1
            if(s.charAt(i-1)!='0') dp[i]+=dp[i-1];

            //how many ways can I decode two digits ending at i? it's wharever dp[i] holds (most likely 0)
            //and how many ways to decode the characters ending at i-2
            if(i>1){
                int twoDigit=Integer.parseInt(s.substring(i-2,i),10);
                if(twoDigit >=10 && twoDigit<=26){
                    dp[i]+=dp[i-2];
                }
            }
        }

        return dp[n];
    }
}
