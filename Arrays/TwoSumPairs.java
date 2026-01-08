package Arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoSumPairs {
    public List<int[]> twoSumAllPairs(int[] nums, int target) {
    if (nums == null || nums.length == 0) return new ArrayList<>();

    Map<Integer, Integer> map = new HashMap<>();
    List<int[]> result = new ArrayList<>();

    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            result.add(new int[]{map.get(complement), i});
        }
        map.put(nums[i], i);
    }

    return result; // just return the List<int[]>, no stream needed
}

}
