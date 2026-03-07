package Strings;

/**The key is to realize that a given type of character with an action e.g.
 *  [ -->double the multipler
 *  ] -->halve the multiplier
 *  , find num * multiplier, etc
 * 
 * An interviewer might even tell you that:
 *      numbers within [] are sum 
 *      those within () are product 
 *      and {} means you get max
 * 
 * 
 *  Operations on state is what this is about.
 */

public class NestedArrayDepthSum {

    public static int depthSum(String s) {

        int depth = 0;
        int sum = 0;
        StringBuilder numBuffer = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            if (Character.isDigit(ch)) {
                numBuffer.append(ch);
            } 
            else if (ch == '[') {
                depth++;
            } 
            else if (ch == ',' || ch == ']') {

                if (numBuffer.length() > 0) {
                    int num = Integer.parseInt(numBuffer.toString());
                    sum += num * depth;
                    numBuffer.setLength(0);
                }

                if (ch == ']') {
                    depth--;
                }
            }
        }

        return sum;
    }

    public static void main(String[] args) {

        String input = "[5,3,[2,4,3,[7,7],5],8,9]";
        int result = depthSum(input);

        System.out.println("Depth Weighted Sum: " + result);
    }
}