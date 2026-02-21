package Heap;

import java.util.*;

/**
 * PROBLEM:
 * Given two integer arrays A and B, find the top K largest products by picking one element
 * from A and one from B. Arrays can contain negative numbers.
 * 
 * STRATEGY:
 * - Sort arrays descending by absolute value to prioritize largest magnitudes.
 * - Use a max-heap to extract the largest product candidate.
 * - Track visited index pairs (i,j) to avoid duplicates.
 * - Generate neighbors by incrementing i or j to explore the next largest potential products.
 * - Account for negative numbers by considering absolute values and computing the actual product.
 */
public class TopKArrayProduct {

    static class Pair {
        int i, j;
        long product;
        Pair(int i, int j, long product) {
            this.i = i;
            this.j = j;
            this.product = product;
        }
    }

    public static List<Long> topKProducts(int[] A, int[] B, int K) {
        int n = A.length, m = B.length;

        // Sort by absolute value descending
        Integer[] a = Arrays.stream(A).boxed().toArray(Integer[]::new);
        Integer[] b = Arrays.stream(B).boxed().toArray(Integer[]::new);

        Arrays.sort(a, (x, y) -> Integer.compare(Math.abs(y), Math.abs(x)));
        Arrays.sort(b, (x, y) -> Integer.compare(Math.abs(y), Math.abs(x)));

        PriorityQueue<Pair> maxHeap = new PriorityQueue<>((p1, p2) -> Long.compare(p2.product, p1.product));
        Set<String> visited = new HashSet<>();

        long initialProduct = (long)a[0] * b[0];
        maxHeap.add(new Pair(0, 0, initialProduct));
        visited.add("0,0");

        List<Long> result = new ArrayList<>();

        // Traditional for-loop instead of while(K-- > 0)
        for (int count = 0; count < K && !maxHeap.isEmpty(); count++) {
            Pair top = maxHeap.poll();
            result.add(top.product);

            int i = top.i, j = top.j;

            // neighbor (i+1, j)
            if (i + 1 < n && !visited.contains((i+1) + "," + j)) {
                long prod = (long)a[i+1] * b[j];
                maxHeap.add(new Pair(i+1, j, prod));
                visited.add((i+1) + "," + j);
            }

            // neighbor (i, j+1)
            if (j + 1 < m && !visited.contains(i + "," + (j+1))) {
                long prod = (long)a[i] * b[j+1];
                maxHeap.add(new Pair(i, j+1, prod));
                visited.add(i + "," + (j+1));
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] A = {3, -2, 5};
        int[] B = {4, -1, 2};
        int K = 5;

        List<Long> topProducts = topKProducts(A, B, K);
        System.out.println("Top " + K + " products: " + topProducts);
        // Expected: largest 5 products considering sign effects
    }
}
