package Graph;

/**
 * PROBLEM: "Allies in the Fog"
 * * STATEMENT:
 * Implement a social graph for a global game that tracks permanent "Alliances."
 * 1. addAlliance(playerA, playerB): Mutual alliance (undirected edge).
 * 2. getMinActions(startPlayer, targetGroupId): Shortest path (jumps) from a
 * player to ANY member of a target allied group.
 * * INTUITION:
 * - Minimum jumps in an unweighted graph = Breadth-First Search (BFS).
 * - "Allied Group" is a set of connected components. This is best managed via 
 * Union-Find (DSU) for O(alpha(N)) group lookups.
 * - Combining them: Start BFS from 'startPlayer'. The first node visited that 
 * belongs to 'targetGroupId' (check via DSU.find()) is the shortest path.
 * 
 * WHY BFS? Equal Edge weights. If you hear weighted then Dijkstra'or any of th others.
 * 
 * RANK:
 * Rank in union find keeps the tree shallow, I learned that you can implement union find without rank. 
 * Rank is a rough upper bound on the tree’s height. Always attach the shorter tree under the taller tree.
 * Attach the shorter tree under the taller one so that the resulting height remains the height of the taller.
 * 
 * 


 */

import java.util.*;

class UnionFind {
    private final Map<Long, Long> parent = new HashMap<>();

    public long find(long i) {
        if (!parent.containsKey(i)) {  //an interesting case where we don't have the node yet.
            parent.put(i, i);
            return i;
        }
        if (parent.get(i) == i) return i;
        parent.put(i, find(parent.get(i))); // Path Compression
        return parent.get(i);
    }

    public void union(long i, long j) {
        long rootI = find(i);
        long rootJ = find(j);
        if (rootI != rootJ) {  //this one way to put it,no need to checj for rank
            parent.put(rootI, rootJ);
        }
    }
}

public class AlliesInTheFog {
    private final Map<Long, List<Long>> adj = new HashMap<>();
    private final UnionFind dsu = new UnionFind();

    public void addAlliance(long a, long b) {
        adj.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
        adj.computeIfAbsent(b, k -> new ArrayList<>()).add(a);
        dsu.union(a, b);
    }

    public int getMinActions(long startPlayer, long targetGroupId) {
        // Validation
        if (!adj.containsKey(startPlayer)) {
            return (dsu.find(startPlayer) == targetGroupId) ? 0 : -1;
        }

        Queue<Long> queue = new ArrayDeque<>();
        Map<Long, Integer> distance = new HashMap<>();

        queue.add(startPlayer);
        distance.put(startPlayer, 0);

        

        while (!queue.isEmpty()) {
            long curr = queue.poll();
            int currentDist = distance.get(curr);

            // Goal check using the encapsulated DSU logic
            if (dsu.find(curr) == targetGroupId) {
                return currentDist;
            }

            for (long neighbor : adj.getOrDefault(curr, Collections.emptyList())) {
                if (!distance.containsKey(neighbor)) {
                    distance.put(neighbor, currentDist + 1);
                    queue.add(neighbor);
                }
            }
        }
        return -1;
    }
}