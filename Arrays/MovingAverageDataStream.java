package Arrays;

/*
============================================================
PROBLEM: Moving Average from Data Stream
============================================================

Problem Statement
-----------------
Design a class that calculates the moving average of the last
k values in a stream of integers.

You must implement:

    MovingAverage(int size)
    double next(int val)

- size = window size
- next(val) adds a value to the stream and returns the
  average of the last "size" values.

If there are fewer than size elements, compute the average
of all elements seen so far.

Example
-------
MovingAverage m = new MovingAverage(3)

m.next(1)  -> 1.0
m.next(10) -> (1+10)/2 = 5.5
m.next(3)  -> (1+10+3)/3 = 4.67
m.next(5)  -> (10+3+5)/3 = 6.0

------------------------------------------------------------
Reasoning
------------------------------------------------------------

Naive approach:
Each time we compute the average of the last k elements.

Cost:
O(k) per operation

Better idea:
Maintain:

1. A queue containing the last k values
2. A running sum of those values

When a new value arrives:

1. Add it to the queue
2. Add it to the running sum
3. If queue size exceeds k:
       remove the oldest element
       subtract it from the sum
4. Return sum / queue.size()

Each operation becomes:

Time Complexity: O(1)
Space Complexity: O(k)

This is a classic "sliding window over a stream".

============================================================
*/

import java.util.*;

public class MovingAverageDataStream {

    private int windowSize;
    private Queue<Integer> window;
    private double sum;

    public MovingAverageDataStream(int size) {
        this.windowSize = size;
        this.window = new LinkedList<>();
        this.sum = 0;
    }

    public double next(int val) {

        window.offer(val);
        sum += val;

        if (window.size() > windowSize) {
            sum -= window.poll();
        }

        // Find average
        return sum / window.size();
    }

    public static void main(String[] args) {

        MovingAverageDataStream m = new MovingAverageDataStream(3);

        System.out.println(m.next(1));   // 1.0
        System.out.println(m.next(10));  // 5.5
        System.out.println(m.next(3));   // 4.666...
        System.out.println(m.next(5));   // 6.0
}
}


    
