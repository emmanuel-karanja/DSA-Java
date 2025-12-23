package Strings;
import java.util.List;
import java.util.ArrayList;
/**Problem: Nested List Weighted Sum

Description:

You are given a nested list of integers. Each element of the list is either:

An integer, or

Another nested list of integers

We define the weight of an integer as 2^depth, where depth is how many lists it is nested inside:

The outermost list has depth 1 (or multiplier 1)

Every time you go into a nested list, the depth increases by 1 (or the multiplier doubles)

Task:

Write a function to compute the sum of all integers multiplied by their weight.

KEY: I FAILED THIS PROBLEM AS A META PHONESCREEN.

Now, it doesn't matter if it's a tree structure, if you hear nesting,it's recursion.*/
public class NestedSum {

    public static int sumNested(List<Object> list) {
        return helper(list, 1); // initial multiplier = 1
    }

    private static int helper(List<Object> list, int multiplier) {
        int sum = 0;
        for (Object obj : list) {
            if (obj instanceof Integer) {
                sum += (Integer) obj * multiplier; // multiply integer by current multiplier
            } else if (obj instanceof List) {
                sum += helper((List<Object>) obj, multiplier * 2); // deeper nesting doubles multiplier
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        // Example nested list
        List<Object> nested = new ArrayList<>();
        nested.add(5);
        nested.add(3);

        List<Object> inner1 = new ArrayList<>();
        inner1.add(2);
        inner1.add(4);
        inner1.add(3);

        List<Object> inner2 = new ArrayList<>();
        inner2.add(7);
        inner2.add(7);

        inner1.add(inner2);
        inner1.add(5);

        nested.add(inner1);
        nested.add(8);
        nested.add(9);

        System.out.println("Sum: " + sumNested(nested)); 
        // Expected output: 117
    }
}
