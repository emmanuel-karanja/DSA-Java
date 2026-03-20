package Arrays;

import java.util.*;

public class GenerateAllSubarrays {
    public static List<int[]> generateAllSubarrays(int[] nums) {
        List<int[]> result = new ArrayList<>();
        int n = nums.length;

        for (int start = 0; start < n; start++) {
            for (int end = start; end < n; end++) {
                int length = end - start + 1;
                int[] subarray = new int[length];

                // Manually copy elements
                for (int i = 0; i < length; i++) {
                    subarray[i] = nums[start + i];
                }

                result.add(subarray);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        List<int[]> subarrays = generateAllSubarrays(nums);

        for (int[] sub : subarrays) {
            System.out.println(Arrays.toString(sub));
        }
    }
}