package Heap;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**Find the K-Closest points to a point
 * 
 * INTUTION:
 * 
 * Use hypotunuse distance but don't sqrt sinc it's a computationally expensive operation.
 * 
 * If this points arrived in a stream Heap is still the most relevant application.
 * 
 * 
 */

public class KClosestToPoint {
    
   public int[][] kClosest(int[][] points, int[] target, int k) {
        // Max-Heap: Compare distances from point 'p' to 'target'
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(k, (p1, p2) -> {
            long d2 = distSq(p2, target);
            long d1 = distSq(p1, target);
            return Long.compare(d2, d1); // Higher distance comes to the top
        });

        for (int[] p : points) {
            maxHeap.offer(p);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        // Convert back to array (O(k log k) or O(k))
        int[][] result = new int[k][2];
        while (k > 0) {
            result[--k] = maxHeap.poll();
        }
        return result;
    }

    private long distSq(int[] p, int[] target) {
        long dx = (long)p[0] - target[0];
        long dy = (long)p[1] - target[1];
        return dx * dx + dy * dy; 
    }
}
