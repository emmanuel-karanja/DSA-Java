package Strings;

import java.util.*;

/**
 * PROBLEM:
 * Given an encoded string, decode it using the following rule:
 *
 *   k[encoded_string] means the encoded_string inside the square brackets
 *   is repeated exactly k times.
 *
 * Encodings can be nested and k may be multi-digit.
 *
 * Examples:
 *   "3[a2[b]]"        -> "abbabbabb"
 *   "2[abc]3[cd]ef"  -> "abcabccdcdcdef"
 *   "10[a]"          -> "aaaaaaaaaa"
 *
 * ASSUMPTIONS:
 * - Input is valid.
 * - No whitespace.
 * - Only lowercase letters, digits, and brackets.
 *
 * ------------------------------------------------------------
 * REASONING / INTUITION:
 *
 * This is a stream parsing + scope management problem.
 *
 * We process the string left-to-right while maintaining:
 *
 * 1. countStack  -> repetition counts for each nested scope
 * 2. stringStack -> the partially built string BEFORE entering a scope
 * 3. current     -> the string being built in the current scope
 * 4. num         -> the number currently being parsed (can be multi-digit)
 *
 * INVARIANT:
 * - Before encountering '[', `current` contains the string built so far
 * - Upon '[', we:
 *      - push `num` to countStack
 *      - push `current` to stringStack
 *      - reset `current` for the inner scope
 * - Upon ']', we:
 *      - pop the repeat count
 *      - pop the previous string
 *      - append current repeated k times to the previous string
 *
 * This naturally handles nesting without recursion.
 *
 * ------------------------------------------------------------
 * TIME COMPLEXITY:
 *   O(N * K) in the worst case, where:
 *     - N = length of the encoded string
 *     - K = maximum expansion factor
 *
 *   (Each character in the final decoded string is appended once.)
 *
 * SPACE COMPLEXITY:
 *   O(N) for:
 *     - stacks
 *     - the output string
 *
 * ------------------------------------------------------------
 */

public class NestedRunLengthDecoder {

    public static String decode(String s) {
        Stack<Integer> countStack = new Stack<>();
        Stack<StringBuilder> stringStack = new Stack<>();

        StringBuilder current = new StringBuilder();
        int num = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (Character.isDigit(ch)) {
                // Build multi-digit number
                num = num * 10 + (ch - '0');

            } else if (ch == '[') {
                // Enter a new nested scope
                countStack.push(num);
                stringStack.push(current);

                current = new StringBuilder();
                num = 0;

            } else if (ch == ']') {
                // Exit scope and merge
                int repeat = countStack.pop();
                StringBuilder prev = stringStack.pop();

                for (int r = 0; r < repeat; r++) {
                    prev.append(current);
                }

                current = prev;

            } else {
                // Regular character
                current.append(ch);
            }
        }

        return current.toString();
    }

    public static void main(String[] args) {
        System.out.println(decode("3[a2[b]]"));        // abbabbabb
        System.out.println(decode("2[abc]3[cd]ef"));  // abcabccdcdcdef
        System.out.println(decode("10[a]"));          // aaaaaaaaaa
        System.out.println(decode("3[a]2[bc]"));      // aaabcbc
    }
}