package Intervals;

import java.util.*;

/*
Problem Statement:

You are given a list of intervals and a list of queries.

Each interval is represented as [l, r], inclusive.
For each query q, you must find the length of the smallest interval [l, r]
such that:

    l <= q <= r

If no such interval exists, return -1 for that query.

Interval length is defined as:
    length = r - l + 1

The result should be returned in the same order as the input queries.

Example:
---------
intervals = [[1,4], [2,4], [3,6], [4,4]]
queries   = [2, 3, 4, 5]

Output:
-------
[3, 3, 1, 4]


Reasoning / Approach:

Brute force would check every interval for every query, resulting in O(N * Q),
which is too slow.

Instead, we use a sweep-line + min-heap approach:

1. Sort intervals by their start value.
2. Sort queries by value (keep original indices).
3. Sweep queries from smallest to largest.
4. Maintain a min-heap of "active" intervals that:
   - Have started (interval.start <= query)
   - Have not ended (interval.end >= query)
5. The heap is ordered by interval length, so the smallest valid interval
   is always at the top.

For each query:
- Add all intervals that start before or at the query.
- Remove intervals that end before the query.
- The top of the heap is the answer.

Each interval is added and removed once → efficient.


Time Complexity:

Sorting intervals: O(N log N)
Sorting queries:   O(Q log Q)
Heap operations:   O((N + Q) log N)

Total: O((N + Q) log N)

Space Complexity:

Heap + auxiliary arrays: O(N + Q)

*/

public class SmallestIntervalContainingQuery {

    public static int[] minInterval(int[][] intervals, int[] queries) {
        int n = intervals.length;
        int q = queries.length;

        // Sort intervals by start
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // Pair queries with original indices
        int[][] queriesWithIndex = new int[q][2];
        for (int i = 0; i < q; i++) {
            queriesWithIndex[i][0] = queries[i];
            queriesWithIndex[i][1] = i;
        }

        // Sort queries by value
        Arrays.sort(queriesWithIndex, Comparator.comparingInt(a -> a[0]));

        // Min-heap ordered by interval length
        // Each entry: [intervalLength, intervalEnd]
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
                (a, b) -> Integer.compare(a[0], b[0])
        );

        int[] result = new int[q];
        Arrays.fill(result, -1);

        int i = 0; // pointer over intervals

        // Sweep over queries
        for (int[] queryPair : queriesWithIndex) {
            int queryValue = queryPair[0];
            int queryIndex = queryPair[1];

            // Add all intervals that start <= query
            while (i < n && queryValue >=intervals[i][0] ) {
                int start = intervals[i][0];
                int end = intervals[i][1];
                int length = end - start + 1;
                minHeap.offer(new int[]{length, end});
                i++;
            }

            // Remove intervals that end < query
            while (!minHeap.isEmpty() && minHeap.peek()[1] < queryValue) {
                minHeap.poll();
            }

            // The smallest valid interval is at the top
            if (!minHeap.isEmpty()) {
                result[queryIndex] = minHeap.peek()[0];
            }
        }

        return result;
    }

    // -------------------------------------------------------------------------
    // Driver code
    // -------------------------------------------------------------------------
    public static void main(String[] args) {
        int[][] intervals = {
                {1, 4},
                {2, 4},
                {3, 6},
                {4, 4}
        };

        int[] queries = {2, 3, 4, 5};

        int[] answers = minInterval(intervals, queries);

        System.out.println("Results:");
        System.out.println(Arrays.toString(answers));
        // Expected output: [3, 3, 1, 4]
    }
}