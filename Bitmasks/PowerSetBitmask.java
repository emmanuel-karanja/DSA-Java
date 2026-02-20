import java.util.*;

/*
REASONING / MENTAL MODEL
-----------------------

Problem:
Generate all subsets (the power set) of a given array of size n.

Key Insight:
A subset can be represented using a bitmask of length n.

- Each bit position corresponds to an index in the array.
- Bit i == 1  → element at index i is included in the subset
- Bit i == 0  → element at index i is excluded

Example:
arr = [A, B, C]

mask = 101 (binary)
      ^
      bit 2 bit 1 bit 0

This mask represents subset: {A, C}

Why this works:
- There are 2^n possible subsets.
- All integers from 0 to (2^n - 1) enumerate every possible combination of bits.
- Iterating masks from 0 → (1<<n)-1 guarantees we generate all subsets exactly once.

Algorithm:
1. Loop over all masks from 0 to (1 << n) - 1
2. For each mask:
   - Loop over all indices i from 0 to n-1
   - If the i-th bit is set in mask, include arr[i] in the current subset
3. Collect and return all subsets

Time Complexity:
- O(n * 2^n)
  (2^n masks, each checking n bits)

Space Complexity:
- O(n * 2^n) for storing all subsets
- O(n) auxiliary per subset construction

This pattern generalizes directly to:
- Bitmask DP
- TSP
- Assignment problems
- State compression over boolean states
*/

public class PowerSetBitmask {

    public static List<List<Integer>> generateSubsets(int[] arr) {
        int n = arr.length;
        int totalMasks = 1 << n;   // 2^n

        List<List<Integer>> result = new ArrayList<>();

        for (int mask = 0; mask < totalMasks; mask++) {
            List<Integer> subset = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                // Check if i-th bit is set
                if ((mask & (1 << i)) != 0) {
                    subset.add(arr[i]);
                }
            }

            result.add(subset);
        }

        return result;
    }

    // Driver function
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};

        List<List<Integer>> subsets = generateSubsets(arr);

        for (List<Integer> subset : subsets) {
            System.out.println(subset);
        }
    }
}
