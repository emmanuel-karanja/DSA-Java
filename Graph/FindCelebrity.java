package Graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**without even knowing any more details about this problem, I havea feeling that it's a graph problem and it's
about indegree. 

 Further if we have n people, the node will have indegree count of n-1 (known to everyone)
 and an outdegree of 0 (knows no one)

 If A knows B, then A cannot be the celebrity.

 This teaches the idea of outdegree.

 MORE EFFICIENT:

 Since we know there is no outdegree, we can store the nodes in a set and then proceed to do the outdegree check
 ifwe find a node has an out degree, it's eliminated from the set, after this iteration, then the remaining nodes are
 celebrities provided they have indegree of n-1. 
*/
public class FindCelebrity {

    // Find celebrity given a list of "a knows b" pairs
    public static int find(int n, int[][] knows) {
        Map<Integer, Integer> indegree = new HashMap<>();
        Map<Integer, Integer> outdegree = new HashMap<>();

        // Initialize maps
        for (int i = 0; i < n; i++) {
            indegree.put(i, 0);
            outdegree.put(i, 0);
        }

        // Build indegree/outdegree from edge list
        for (int[] edge : knows) {
            int a = edge[0];
            int b = edge[1];

            outdegree.put(a, outdegree.get(a) + 1);
            indegree.put(b, indegree.get(b) + 1);
        }

        // Check for celebrity
        for (int i = 0; i < n; i++) {
            if (outdegree.get(i) == 0 && indegree.get(i) == n - 1) {
                return i;
            }
        }

        return -1; // no celebrity
    }

     // Find celebrity given a list of "a knows b" pairs
    public static int findElimination(int n, int[][] knows) {
       // Start with all people as candidates
        Set<Integer> candidates = new HashSet<>();
        for (int i = 0; i < n; i++) candidates.add(i);

        // Elimination scan
        for (int[] edge : knows) {
            int a = edge[0];
            int b = edge[1];

            if (candidates.contains(a) && candidates.contains(b)) {
                // If a knows b, a cannot be celebrity
                candidates.remove(a);
                // If a didn't know b (edge missing in a generalized case), b cannot be celebrity
                // This can be added if input is complete adjacency matrix or API
            }
        }

        if (candidates.size() != 1) return -1; // No celebrity

        int candidate = candidates.iterator().next();

        // Verification step
        int indegree = 0, outdegree = 0;
        for (int[] edge : knows) {
            if (edge[0] == candidate) outdegree++;
            if (edge[1] == candidate) indegree++;
        }

        if (outdegree == 0 && indegree == n - 1) return candidate;
        return -1;
    }

    public static void main(String[] args) {
        int n = 4;
        // Each pair [a, b] means a knows b
        int[][] knows = {
            {0, 2},
            {1, 2},
            {3, 2}
        };

        int celeb = find(n, knows);
        if (celeb != -1) {
            System.out.println("Celebrity is person: " + celeb);
        } else {
            System.out.println("No celebrity exists");
        }
    }
}


