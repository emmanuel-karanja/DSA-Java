package Arrays.SubArraySum;
/**THis can also be implemented with PrefixSum, sortedSet, indexMap and fenwick tree(for efficient query ranges)
 * 
 * Time : O(nlogn)
 * Space: O(n)
 */
import java.util.*;

public class SubarraySumAtMostK {
    public static int countSubarraysAtMostK(int[] nums, int K) {
        TreeMap<Long, Integer> prefixSums = new TreeMap<>();
        prefixSums.put(0L, 1); // Base case: empty prefix
        long currSum = 0;
        int count = 0;

        for (int num : nums) {
            currSum += num;

            // Find number of prefix sums >= currSum - K
            // TreeMap.tailMap returns keys >= (currSum - K)
            for (Map.Entry<Long, Integer> entry : prefixSums.tailMap(currSum - K, true).entrySet()) {
                count += entry.getValue();
            }

            // Add current prefix sum to the map
            prefixSums.put(currSum, prefixSums.getOrDefault(currSum, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, -2, 5};
        int K = 5;
        System.out.println(countSubarraysAtMostK(nums, K)); // Output: number of subarrays
    }
}