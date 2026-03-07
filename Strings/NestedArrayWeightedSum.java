package Strings;

public class NestedArrayWeightedSum {

    public static int weightedSum(String s) {

        int multiplier = 1;
        int sum = 0;
        StringBuilder numBuffer = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (Character.isDigit(ch)) {
                numBuffer.append(ch);
            } 
            else if (ch == '[') {
                multiplier *= 2;
            } 
            else if (ch == ',' || ch == ']') {

                if (numBuffer.length() > 0) {
                    int num = Integer.parseInt(numBuffer.toString());
                    sum += num * multiplier;
                    numBuffer.setLength(0);
                }

                if (ch == ']') {
                    multiplier /= 2;
                }
            }
        }

        return sum;
    }

    public static void main(String[] args) {

        String input = "[5,3,[2,4,3,[7,7],5],8,9]";
        int result = weightedSum(input);

        System.out.println("Weighted Sum: " + result);
    }
}