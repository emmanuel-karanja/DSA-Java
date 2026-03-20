package DifferenceArray;

import java.util.*;

/*
PROBLEM: Range Addition (LeetCode 370)

You are given:
length = size of array
updates[i] = [start, end, val]

Apply:
Add val to all elements from start → end (inclusive)

Return final array.

---

THINKING PROCESS:

This is the PURE difference array problem.

Instead of updating every index:
Use:
diff[start] += val
diff[end + 1] -= val

Then prefix sum reconstructs the array.

---

TIME: O(n + updates)
SPACE: O(n)
*/

public class RangeAddition {

    public static int[] getModifiedArray(int length, int[][] updates) {
        int[] diff = new int[length];

        // Step 1: Apply updates
        for (int[] u : updates) {
            int start = u[0];
            int end = u[1];
            int val = u[2];

            diff[start] += val;

            if (end + 1 < length) {
                diff[end + 1] -= val;
            }
        }

        // Step 2: Prefix sum
        int[] result = new int[length];
        result[0] = diff[0];

        for (int i = 1; i < length; i++) {
            result[i] = result[i - 1] + diff[i];
        }

        return result;
    }

    public static void main(String[] args) {
        int length = 5;

        int[][] updates = {
                {1, 3, 2},
                {2, 4, 3},
                {0, 2, -2}
        };

        int[] result = getModifiedArray(length, updates);

        System.out.println("Modified array:");
        System.out.println(Arrays.toString(result));
    }
}