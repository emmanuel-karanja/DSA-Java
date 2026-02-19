package Heap;



import java.util.*;

/**
 * PROBLEM:
 * Given two arrays A and B, both sorted in descending order, 
 * find the top K largest sums where each sum is formed by picking 
 * one element from A and one element from B.
 * 
 * For example:
 * A = [9, 7, 5], B = [8, 6, 3], K = 3
 * Top-3 sums: [17, 15, 15] (9+8, 9+6, 7+8)
 * 
 * REASONING:
 * 1. Both arrays are sorted descending, so the largest sum is A[0]+B[0].
 * 2. Use a max-heap (priority queue) to always extract the current largest sum.
 * 3. For each extracted sum (i,j), push the next two candidates:
 *    - (i+1, j): next element from A
 *    - (i, j+1): next element from B
 *    Only push if this index combination hasn't been visited.
 * 4. Repeat K times to get the top-K sums efficiently.
 * 
 * Time Complexity: O(K log K) since we push/pop at most 2K elements in the heap. 
 * Space Complexity: O(K) for heap and visited set.
 * 
 * NOTE: This is a best first search  over state space i.e. generate pairs getting an element from each
 *  that gives us the highest sum. This is basically a 2D table. When you sort it, it becomes as such.
 *  It looks like a graph problem since you are visiting nodes from the top left corner. This is a grid graph
 *  problem including wall-checks!!
 * 
 * Best-first search over a monotone N-dimensional state space
 * 
 * And if we had a 3 arrays, you'd need to have a triple, and generate 3 nighbours (due to the monotonic property because of
 * the sorting). 
 */
public class TopKArraySums {

    static class Pair {
        int sum;
        int i, j;

        Pair(int sum, int i, int j) {
            this.sum = sum;
            this.i = i;
            this.j = j;
        }
    }

    public static List<Integer> topKSum(int[] A, int[] B, int K) {
        List<Integer> result = new ArrayList<>();
        if (A.length == 0 || B.length == 0 || K <= 0) return result;

        PriorityQueue<Pair> maxHeap = new PriorityQueue<>(
                (p1, p2) -> Integer.compare(p2.sum, p1.sum) // max-heap
        );

        Set<String> visited = new HashSet<>();
        int i = 0, j = 0;

        // initialize the maxHeap
        maxHeap.offer(new Pair(A[i] + B[j], i, j));
        visited.add(i + "," + j);

        while (!maxHeap.isEmpty() && result.size() < K) {
            Pair current = maxHeap.poll();
            result.add(current.sum);

            // Push neighbor (i+1, j)
            if (current.i + 1 < A.length) {
                String key1 = (current.i + 1) + "," + current.j;
                if (!visited.contains(key1)) {
                    maxHeap.offer(new Pair(A[current.i + 1] + B[current.j], current.i + 1, current.j));
                    visited.add(key1);
                }
            }

            // Push neighbor (i, j+1)
            if (current.j + 1 < B.length) {
                String key2 = current.i + "," + (current.j + 1);
                if (!visited.contains(key2)) {
                    maxHeap.offer(new Pair(A[current.i] + B[current.j + 1], current.i, current.j + 1));
                    visited.add(key2);
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] A = {9, 7, 5};
        int[] B = {8, 6, 3};
        int K = 5;

        List<Integer> topSums = topKSum(A, B, K);
        System.out.println("Top " + K + " sums: " + topSums);
    }
}
