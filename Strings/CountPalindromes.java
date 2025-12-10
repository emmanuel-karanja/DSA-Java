package Strings;
/**Given a string s,count the number of palindromes:
 * 
 * INTUTION:
 * 
 * use expand around center for odd and even length palindromes.
 * Keep track of count
 */
public class CountPalindromes {

    public static int getPalindromeCount(String s){
        if(s==null || s.length()==0){
            throw new IllegalArgumentException("String is empty or null.");
        }

        int count=0;

        for(int i=0;i<s.length();i++){
            //odd length palndrome
            count+= expandAroundCenter(s, i, i);
            //even length palindrome
            count+= expandAroundCenter(s, i, i+1);
        }


        return count;
    }
    
    private static int expandAroundCenter(String s,int left,int right){
        int count=0;
        while(left>=0 && right< s.length() && s.charAt(left)==s.charAt(right)){
            count++;
            left--;
            right++;
        }

        return count;
    }
}


