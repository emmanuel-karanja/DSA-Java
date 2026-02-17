package DP;

/**
 * PROBLEM STATEMENT:
 * You have N video streams to upload. Each stream i has a start time S[i, an end time] E[i], and a "Priority" P[i]. 
 * Your server can only handle one stream at a time. You want to select a subset of streams that do not overlap in time 
 * such that the total sum of Priority P is maximized.
 * 
 * * ELIMINATION STRATEGY REASONING:
 * 1. BSOA: Fails. Total priority isn't a threshold; it's a sum of specific combinations.
 * 2. Greedy: Fails. Picking the "highest priority" or "earliest end" can block 
 * multiple streams whose combined priority is higher (The Exchange Argument).
 * 3. DP: Passes. 
 * - Rule 2 (Accounting): Time is a shared resource being consumed.
 * - Rule 3 (Dependency): Choosing a stream now dictates which future streams 
 * are 'legal' (only those starting after this one ends).
 * - Rule 4 (Overlapping): Many paths lead to the same 'Time T', requiring 
 * the same sub-problem answer: "What's the best I can do from Time T onwards?"
 * * TIME COMPLEXITY: O(N log N) due to sorting and binary search.
 * SPACE COMPLEXITY: O(N) for the DP table.
 */

import java.util.*;

class Stream {
    int start, end, priority;
    Stream(int start, int end, int priority) {
        this.start = start;
        this.end = end;
        this.priority = priority;
    }
}

public class WeightedIntervalScheduling {
    public int maximizePriority(Stream[] streams) {
        int n = streams.length;
        if (n == 0) return 0;

        // 1. Sort by end time - This creates our "Timeline"
        Arrays.sort(streams, Comparator.comparingInt(s -> s.end));

        // 2. dp[i] = max priority using a subset of the first 'i' streams
        int[] dp = new int[n];
        dp[0] = streams[0].priority;

        for (int i = 1; i < n; i++) {
            // Option A: Skip current stream
            int skip = dp[i - 1];

            // Option B: Take current stream
            int take = streams[i].priority;
            
            // Find the latest stream that doesn't overlap with streams[i]
            int prevIndex = findLatestNonOverlapping(streams, i);
            if (prevIndex != -1) {
                take += dp[prevIndex];
            }

            dp[i] = Math.max(skip, take);
        }

        return dp[n - 1];
    }

    // Binary Search to find the latest stream whose end <= current stream's start
    private int findLatestNonOverlapping(Stream[] streams, int currentIndex) {
        int low = 0, high = currentIndex - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (streams[mid].end <= streams[currentIndex].start) {
                result = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        WeightedIntervalScheduling scheduler = new WeightedIntervalScheduling();
        Stream[] streams = {
            new Stream(1, 3, 50),
            new Stream(3, 5, 20),
            new Stream(6, 19, 100),
            new Stream(2, 100, 200) // One long stream with high priority
        };
        System.out.println("Max Priority: " + scheduler.maximizePriority(streams));
    }
}