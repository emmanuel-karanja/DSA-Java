package Strings;

/**Implement atoi as follows:
 * 
 * Problem: Implement myAtoi(String s) that:
 * Ignores leading whitespace
 * Handles optional + or -
 * Converts digits until a non-digit is encountered
 * Clamps the result to 32-bit signed integer range if overflow occurs
 */
public class Atoi {
    
    public static int toNumber(String s){
        if(s == null || s.length() == 0) return 0;

        String trimmed = s.trim();
        if(trimmed.length() == 0) return 0;

        char signChar = '+';
        int i = 0;
        if(trimmed.charAt(0) == '-' || trimmed.charAt(0) == '+'){
            signChar = trimmed.charAt(0);
            i++;
        }

        StringBuilder sb = new StringBuilder();
        while(i < trimmed.length() && Character.isDigit(trimmed.charAt(i))){
            sb.append(trimmed.charAt(i));
            i++;
        }

        if(sb.length() == 0) return 0; // no digits found

        // Handle sign
        long num = Long.parseLong(sb.toString());
        if(signChar == '-') num = -num;

        // clamp to 32-bit signed integer
        if(num > Integer.MAX_VALUE) return Integer.MAX_VALUE;
        if(num < Integer.MIN_VALUE) return Integer.MIN_VALUE;

        return (int) num;
    }

    public static void main(String[] args){
        System.out.println(toNumber("   -42"));     // -42
        System.out.println(toNumber("4193 with words")); // 4193
        System.out.println(toNumber("words 123"));  // 0
        System.out.println(toNumber("-91283472332")); // -2147483648 (clamped)
        System.out.println(toNumber("+"));   // 0
        System.out.println(toNumber("   ")); // 0
        System.out.println(toNumber("-"));   // 
        System.out.println(toNumber("   -0012a42")); // -12
        System.out.println(toNumber("--42")); // 0
        System.out.println(toNumber("+-12")); // 0
    }
}
