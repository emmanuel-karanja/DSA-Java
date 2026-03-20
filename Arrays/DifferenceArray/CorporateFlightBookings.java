package Arrays.DifferenceArray;


import java.util.*;

/*
PROBLEM: Corporate Flight Bookings (LeetCode 1109)

You are given bookings where:
booking[i] = [first, last, seats]

This means:
Add "seats" to every flight from index first → last (inclusive).

Return an array of size n where:
answer[i] = total seats booked for flight (i+1)

---

THINKING PROCESS:

Naive:
For each booking → update all indices in range → O(n * bookings)

Better:
We use a DIFFERENCE ARRAY.

Key Idea:
Instead of updating the entire range:
- Add seats at start index
- Subtract seats right after the end

Then take prefix sum to reconstruct final array.

---

DIFFERENCE ARRAY RULE:
For booking [l, r, val]:
diff[l] += val
diff[r + 1] -= val

Then prefix sum builds actual values.

---

TIME: O(n + bookings)
SPACE: O(n)
*/

public class CorporateFlightBookings {

    public static int[] corpFlightBookings(int[][] bookings, int n) {
        // Size n+2 to handle the Range+1 boundary safely
        int[] diff = new int[n+2];

        // Step 1: Apply range updates
        for (int[] b : bookings) {
            int l = b[0] - 1; // convert to 0-based
            int r = b[1] - 1;
            int seats = b[2];

            diff[l] += seats;
            
            diff[r + 1] -= seats;
        
        }

        // Step 2: Prefix sum to build result

        int[] result = new int[n];
        int curr = 0;
        for (int i = 1; i <= n; i++) {
            curr += diff[i];
             // For max bookings you can do maxBookings=Math.max(curr, maxBookings)
            result[i - 1] = curr;
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] bookings = {
                {1, 2, 10},
                {2, 3, 20},
                {2, 5, 25}
        };

        int n = 5;

        int[] result = corpFlightBookings(bookings, n);

        System.out.println("Flight bookings result:");
        System.out.println(Arrays.toString(result));
    }
}