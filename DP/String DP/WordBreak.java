package DP;

import java.util.HashSet;
import java.util.Set;

/**Given a word s and a dictionary of words wordDict, return true if s can be segmetned into a space separated sequence
 * of one or more words in the dictionary.
 * 
 *INTUITION:

  We generated a prefix from i->j and check if the prefix is the dictionary if it's, we check the remainder of the
   word s from j+1 to s.length-1 recursively.

   You can even use memoization to speed up the recursive case
 */

public class WordBreak {

    public static boolean canBreakRecursive(int startIndex, String s, Set<String> wordDict){

        if(startIndex==s.length()) return true; //we'vearrived at the end
        for(int end=startIndex+1;end<=s.length();end++){
               String prefix=s.substring(startIndex,end);
               if(wordDict.contains(prefix) && canBreakRecursive(end,s, wordDict)){
                    return true;
               }
        } 

        return false;
    }

    public static boolean canBreakDP(String s, Set<String> wordDict){
        //create the dp array
        boolean[] dp=new boolean[s.length()+1]; //auto initialized to false
        dp[0]=true; //an emptystring case or independent base case

        //in 1D casws the entire subsection of the array before i is the dependency space 
        //hence we always check j==0 to j<i. 
        //dp[j] is literally wordfrom 0 to j

        for(int i=1;i<=s.length();i++){
            for(int j=0;j<i;j++){
                if(dp[j] && wordDict.contains(s.substring(j,i))){
                    dp[i]=true;
                    break;
                }
            }
        }

        return dp[s.length()];

    }

    public static void main(String[] args) {

        // Test case 1
        String s1 = "leetcode";
        Set<String> dict1 = new HashSet<>();
        dict1.add("leet");
        dict1.add("code");

        System.out.println("Input: " + s1);
        System.out.println("Recursive: " +
                WordBreak.canBreakRecursive(0, s1, dict1));
        System.out.println("DP: " +
                WordBreak.canBreakDP(s1, dict1));

        System.out.println();

        // Test case 2
        String s2 = "applepenapple";
        Set<String> dict2 = new HashSet<>();
        dict2.add("apple");
        dict2.add("pen");

        System.out.println("Input: " + s2);
        System.out.println("Recursive: " +
                WordBreak.canBreakRecursive(0, s2, dict2));
        System.out.println("DP: " +
                WordBreak.canBreakDP(s2, dict2));

        System.out.println();

        // Test case 3 (should be false)
        String s3 = "catsandog";
        Set<String> dict3 = new HashSet<>();
        dict3.add("cats");
        dict3.add("dog");
        dict3.add("sand");
        dict3.add("and");
        dict3.add("cat");

        System.out.println("Input: " + s3);
        System.out.println("Recursive: " +
                WordBreak.canBreakRecursive(0, s3, dict3));
        System.out.println("DP: " +
                WordBreak.canBreakDP(s3, dict3));
    }
    
}
