package Intervals;

import java.util.ArrayList;
import java.util.List;

/**Insert an interval in a sorted array of intervals:
 * 
 * INTUTION:
 * 
 *  Add all the intervals that come before newInterval
 *  Merge any overlapping intervals into newInterval
 *  Add the merged newInterval into the result
 *  Add the ones that come after it.
 * 
 * *Use a running pointer to correctly point the next insert position.
 */
public class InsertIntervalInSortedArray {
    
    public static List<int[]> insertInterval(List<int[]> intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;

        // 1️ Add all intervals before newInterval
        while (i < intervals.size() && newInterval[0] > intervals.get(i)[1]) {
            result.add(intervals.get(i));
            i++;
        }

        // 2️ Merge overlapping intervals. Merge them into the new interval.
        while (i < intervals.size() && newInterval[1] >=intervals.get(i)[0]) {
            newInterval[0] = Math.min(newInterval[0], intervals.get(i)[0]);
            newInterval[1] = Math.max(newInterval[1], intervals.get(i)[1]);
            i++;
        }

        // 3️ Add merged interval
        result.add(newInterval);

        // 4️ Add remaining intervals
        while (i < intervals.size()) {
            result.add(intervals.get(i));
            i++;
        }

        return result;
    }
    
}
