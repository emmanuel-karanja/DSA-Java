/**
 * PROBLEM: Subarray Sum Equals K
 * STRATEGY: 
 * We use the property: Sum(i, j) = PrefixSum(j) - PrefixSum(i-1).
 * If Sum(i, j) = k, then PrefixSum(j) - k = PrefixSum(i-1).
 * By storing prefix sums in a HashMap, we can find the "complement" in O(1).
 */
import java.util.*;

public class SubArraySumEqualK {
    public int subarraySum(int[] nums, int k) {
        int count = 0, currentPrefixSum = 0;
        // Map: PrefixSum -> Frequency
        Map<Integer, Integer> map = new HashMap<>();
        
        // Base case: A prefix sum of 0 has been seen once (empty subarray)
        map.put(0, 1);
        
        for (int num : nums) {
            currentPrefixSum += num;
            
            // If (currentPrefixSum - k) exists in map, it means there is a 
            // previous prefix sum that, when subtracted from the current, equals k.
            if (map.containsKey(currentPrefixSum - k)) {
                count += map.get(currentPrefixSum - k);
            }
            
            map.put(currentPrefixSum, map.getOrDefault(currentPrefixSum, 0) + 1);
        }
        return count;
    }
    
    public static void main(String[] args) {
        SubArraySumEqualK sol = new SubArraySumEqualK();
        System.out.println("Test 1 (Expected 2): " + sol.subarraySum(new int[]{1, 1, 1}, 2));
        System.out.println("Test 2 (Expected 3): " + sol.subarraySum(new int[]{1, 2, 3}, 3));
    }
}