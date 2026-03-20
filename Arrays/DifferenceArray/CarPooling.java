package Arrays.DifferenceArray;

import java.util.*;

/*
PROBLEM: Car Pooling (LeetCode 1094)

You are given trips:
trip[i] = [numPassengers, from, to]

Meaning:
- Pick up numPassengers at location "from"
- Drop them at location "to"

Return TRUE if the car never exceeds capacity.

---

THINKING PROCESS:

We need to track:
"How many passengers are in the car at any point?"

This is a RANGE ADD problem over a timeline.

Use DIFFERENCE ARRAY:
- +passengers at "from"
- -passengers at "to"

Then prefix sum gives passengers at each point.

If at any point > capacity → return false.

---

IMPORTANT:
We don't subtract at (to + 1)
Because passengers leave exactly at "to".

---

TIME: O(max_location)
SPACE: O(max_location)
*/

public class CarPooling {

    public static boolean carPooling(int[][] trips, int capacity) {
        int maxLocation = 1000; // constraint-based safe bound
        int[] diff = new int[maxLocation + 1];

        // Step 1: Apply changes
        for (int[] t : trips) {
            int passengers = t[0];
            int from = t[1];
            int to = t[2];

            diff[from] += passengers;  //pick up
            diff[to] -= passengers;  //drop
        }

        // Step 2: Prefix sum + check capacity
        int currentPassengers = 0;

        for (int i = 0; i <= maxLocation; i++) {
            currentPassengers += diff[i];

            if (currentPassengers > capacity) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int[][] trips = {
                {2, 1, 5},
                {3, 3, 7}
        };

        int capacity = 4;

        boolean result = carPooling(trips, capacity);

        System.out.println("Can carpool? " + result);
    }
}