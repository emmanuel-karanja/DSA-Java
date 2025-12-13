package Backtracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Given a string containing digits from 2-9 inclusive returna all possible letter combinations that a given number could
 * represent.
 * 
 *e.g. 23=ad,ae,af,bd,be,bf,cd,ce,cf since 2 is 'abc and 3 is 'def'

 INTUTION:
 A map to look up that maps a number to letters e.g. "2"->"abc" i.e. integer->string
 bactracking where the rules are:
    1.Picka signle digit from the digits
    2. backtrack on the char from that and then advance the next index in the digits array
 */

public class PhoneNumberDigits {

    public static List<String> getPhoneNumberCombos(String digits) {

        if (digits == null || digits.length() == 0) {
            return new ArrayList<>();
        }

        Map<Character, String> map = new HashMap<>();
        map.put('2', "abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");

        List<String> result = new ArrayList<>();
        backtrack(0, new StringBuilder(), map, digits, result);
        return result;
    }

    private static void backtrack(int index, StringBuilder candidate,
                                  Map<Character, String> map, String digits,
                                  List<String> result) {

        // Print current call
        System.out.println("Call: index=" + index + ", candidate=" + candidate);

        if (index == digits.length()) {
            result.add(candidate.toString());
            System.out.println("  => Added to result: " + candidate);
            return;
        }

         // Current digit as a char, e.g. '2'
        char digit = digits.charAt(index);

        // Lookup letters for this digit
        String letters = map.get(digit);

        // For each of the letters of of the digits, backtrack, we are basically appending the character from the letters
       //  temporarily and test the backtrack adn then see if we have found the solution and then remove it and try the
       //the nextone
        for (char c : letters.toCharArray()) {
            candidate.append(c);  //make choice
            System.out.println("  Choose: " + c + " -> candidate=" + candidate);
            backtrack(index + 1, candidate, map, digits, result);
            candidate.deleteCharAt(candidate.length() - 1); // backtrack /unmake choice
            System.out.println("  Backtrack: removed " + c + " -> candidate=" + candidate);
        }
    }

    public static void main(String[] args){
        String example="23";

        List<String> results=getPhoneNumberCombos(example);

        for(String s: results){
            System.out.println(s);
        }
    }
}
