import java.util.HashMap;
import java.util.Map;

/**Convert a roman numeral to a number.
 * 
 * INTUTION:
 * 
 * Start from the end of the string and parse the chars, map to value and . Use key values only
 * 
 * Roman numerals mostly add values from left to right:

    VI = 5 + 1 = 6
    XII = 10 + 1 + 1 = 12

   But there’s a subtractive rule: if a smaller numeral comes before a larger one, you subtract it:
    IV = 5 - 1 = 4
    IX = 10 - 1 = 9
    XL = 50 - 10 = 40
 * 
 */

public class FromRoman {

    private static final Map<Character, Integer> ROMAN_MAP = new HashMap<>();
    static {
        ROMAN_MAP.put('I', 1);
        ROMAN_MAP.put('V', 5);
        ROMAN_MAP.put('X', 10);
        ROMAN_MAP.put('L', 50);
        ROMAN_MAP.put('C', 100);
        ROMAN_MAP.put('D', 500);
        ROMAN_MAP.put('M', 1000);
    }

    public static int convert(String roman){
        if(roman==null || roman.isEmpty()){
            throw new IllegalArgumentException("Roman is null or empty.");
        }

        int total=0;
        int prevValue=0;

        for(int i=roman.length()-1;i>=0;i--){
            //get chat
            char c=roman.charAt(i);

            int value=ROMAN_MAP.getOrDefault(c,-1); //to make code cleaner

            if(value ==-1){
                throw new IllegalArgumentException("Invalid roman numeral: "+c+" at position: "+i);
            }

            if(value < prevValue){  //Follow the subtractive rule e.g. if we had IV, it's  1 and 5, prevvalue is 5
            // and the current value is 1 and true value is 5-1 since we are starting from the end.
                total-=value;
            }else{
                total+=value;
            }

            prevValue=value;

        }
        
        return total;

    }
     public static void main(String[] args) {
        String roman = "MCMLXXXVII";
        System.out.println(convert(roman));  // Output: 1987
    }
}
