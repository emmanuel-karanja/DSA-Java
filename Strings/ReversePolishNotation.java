package Strings;

import java.util.*;

/*

Problem Statement:

You are given an array of strings representing a mathematical expression in
Reverse Polish Notation (RPN).

Evaluate the expression and return the integer result.

Rules:
- Valid operators are "+", "-", "*", "/"
- Division between two integers truncates toward zero
- Operands and results fit in a 32-bit signed integer
- The expression is guaranteed to be valid

Example:
---------
Input:  ["2", "1", "+", "3", "*"]
Output: 9

Explanation:
---------
((2 + 1) * 3) = 9

Reasoning / Key Insight:

Reverse Polish Notation removes parentheses by enforcing evaluation order.

Key observation:
- When we see a number → push it onto a stack
- When we see an operator → pop the last two numbers, apply the operator,
  then push the result back

Why this works:
- Operators always apply to the most recent operands
- Stack naturally enforces correct evaluation order

Algorithm:

Initialize an empty stack

For each token in the input:
  If token is a number:
      push it onto the stack
  Else (operator):
      pop operand2
      pop operand1
      result = operand1 (operator) operand2
      push result back onto the stack

Final result is the only value left in the stack


Time Complexity:

O(N), where N is the number of tokens

Space Complexity:

O(N) for the stack (worst case)

*/

public class ReversePolishNotation {

    public static int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (String token : tokens) {
            // If token is an operator
            if (isOperator(token)) {
                int b = stack.pop(); // second operand
                int a = stack.pop(); // first operand

                int result;
                switch (token) {
                    case "+":
                        result = a + b;
                        break;
                    case "-":
                        result = a - b;
                        break;
                    case "*":
                        result = a * b;
                        break;
                    case "/":
                        result = a / b; // truncates toward zero
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operator");
                }

                stack.push(result);
            } else {
                // Token is a number
                stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-")
            || token.equals("*") || token.equals("/");
    }

    // -------------------------------------------------------------------------
    // Driver code
    // -------------------------------------------------------------------------
    public static void main(String[] args) {
        String[] tokens1 = {"2", "1", "+", "3", "*"};
        String[] tokens2 = {"4", "13", "5", "/", "+"};
        String[] tokens3 = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};

        System.out.println(evalRPN(tokens1)); // 9
        System.out.println(evalRPN(tokens2)); // 6
        System.out.println(evalRPN(tokens3)); // 22
    }
}