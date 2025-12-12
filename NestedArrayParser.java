import java.util.*;

public class NestedArrayParser {

    public static List<Object> parse(String s) {
        Stack<List<Object>> stack = new Stack<>();
        List<Object> current = new ArrayList<>();
        StringBuilder numBuffer = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (Character.isDigit(ch)) {
                numBuffer.append(ch);
            } else if (ch == '[') {
                List<Object> newList = new ArrayList<>();
                if (!stack.isEmpty()) {
                    stack.peek().add(newList);
                }
                stack.push(newList);
            } else if (ch == ',' || ch == ']') {
                if (numBuffer.length() > 0) {
                    int num = Integer.parseInt(numBuffer.toString());
                    stack.peek().add(num);
                    numBuffer.setLength(0);
                }
                if (ch == ']') {
                    current = stack.pop();
                }
            }
        }

        return current;
    }

    public static void main(String[] args) {
        String input = "[5,3,[2,4,3,[7,7],5],8,9]";
        List<Object> nestedList = parse(input);
        System.out.println(nestedList); // prints nested structure
    }
}
