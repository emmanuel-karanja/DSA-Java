package Strings;
/**Find the length of a substring within a String s where character count of character c is at most k */
public class LongestWithAtMostKCharOfChar {
    
    public static int longestWithAtMostCountOfChar(String s,char ch, int k){
        int left=0;
        int right=0;
        int count=0;
        int maxLen=0;

        while(right < s.length()){
            if(s.charAt(right)==ch){
                count++;
            }

            while(count >k){
               if(s.charAt(left)==ch){
                count--;
               }
               left++;
            }


            maxLen=Math.max(maxLen,right-left+1);
            right++;
        }

        return maxLen;
    }
}
