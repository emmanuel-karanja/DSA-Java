package Strings;
/**Given a string, find the longest palindrome:
 * 
 * INTUTION
 * 
 * Starting from the first positon, expand around the character for odd length palidrome and from two positions 
 * for even ones, pick the max of odd and even and then record the further left and right pointers go.
 */

class Result{
    public String palindrome;
    public int length;

    public Result(String palindrome, int length){
        this.palindrome=palindrome;
        this.length=length;
    }
}
public class LongestPalindrome {

    private static int[] expandAroundCenter(char[] arr, int left,int right){

        int[] result=new int[2];
        //expand around center
        while(left>=0 && right<=arr.length-1 && arr[left]==arr[right]){
            left--;
            right++;
        }

        //don't forget that we find ourselves moving one past for either left or right
        result[0]=left+1;
        result[1]=right-1;  
        return result;
    }

    public static Result getLongestPalindrome(String str){
        if(str==null || str.length()==0) return new Result("",0);

        char[] asChar=str.toCharArray();

        int maxLength=0;
        int startIndex=0;
        
        for(int i=0;i<asChar.length;i++){
             int[] evenResult=expandAroundCenter(asChar, i, i+1);
             int[] oddResult=expandAroundCenter(asChar, i, i);

             int evenLength=evenResult[1]-evenResult[0]+1; //right-left+1;
             int oddLength=oddResult[1]-oddResult[0]+1;


             if(oddLength>maxLength){
                  startIndex=oddResult[0];
                  maxLength=oddLength;
             }else if(evenLength>maxLength){
                  startIndex=evenResult[0];
                  maxLength=evenLength;
             }
        }


        return new Result(str.substring(startIndex, startIndex+maxLength), maxLength);  // for substring str.substring(startIndex,startIndex+maxLength)

    }
    
    public static void main(String[] args) {

        String[] testCases = {
            "babad",
            "cbbd",
            "a",
            "aa",
            "aaaa",
            "racecar",
            "forgeeksskeegfor",
            "",
            null
        };

        for (String input : testCases) {
            Result result = LongestPalindrome.getLongestPalindrome(input);

            System.out.println("Input: " + input);
            System.out.println("Longest Palindrome: \"" + result.palindrome + "\"");
            System.out.println("Length: " + result.length);
            System.out.println("--------------------------------");
        }
    }
}
