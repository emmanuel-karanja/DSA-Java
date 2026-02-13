package Graph;

import java.util.*;

/**
 * ============================
 *  Bipartite Graph Explained
 * ============================
 *
 * A graph is called BIPARTITE if its vertices can be divided into TWO disjoint sets
 * such that:
 *
 *   - Every edge connects a vertex from one set to a vertex in the other set
 *   - No edge connects two vertices within the same set
 *
 * Equivalent (and more practical) definition:
 * -------------------------------------------
 * A graph is bipartite IF AND ONLY IF it is possible to color every vertex
 * using exactly TWO colors (say 0 and 1) such that:
 *
 *   - No two adjacent vertices have the same color
 *
 * This is known as 2-coloring.
 *
 * -------------------------------------------
 * Why does this work?
 * -------------------------------------------
 * When traversing the graph (using BFS or DFS):
 * - Assign the start node color 0
 * - All its neighbors must be color 1
 * - Their neighbors must be color 0
 * - And so on...
 *
 * If at any point we find an edge (u, v) where:
 *   color[u] == color[v]
 * then the graph CANNOT be bipartite.
 *
 * -------------------------------------------
 * Key Theorem (Interview Favorite):
 * -------------------------------------------
 * A graph is bipartite <=> it contains NO ODD-LENGTH CYCLE.
 *
 * - Even cycles can be colored with 2 colors
 * - Odd cycles (like triangles) cannot
 *
 * -------------------------------------------
 * Why is EVERY TREE bipartite?
 * -------------------------------------------
 * A tree has TWO defining properties:
 *   1. It is connected
 *   2. It has NO cycles
 *
 * Since a tree has no cycles at all:
 *   -> It certainly has no odd-length cycles
 *
 * Therefore:
 *   -> Every tree is bipartite
 *
 * Intuition:
 * - Pick any node as root
 * - Color nodes by depth:
 *     depth % 2 == 0 -> color 0
 *     depth % 2 == 1 -> color 1
 * - Parent and child always have opposite depths
 * - Hence, opposite colors
 *
 * This is why:
 * - BFS level-order coloring works on trees
 * - Tree DP problems often assume 2-colorability
 *
 * -------------------------------------------
 * Algorithm Used Below:
 * -------------------------------------------
 * We use BFS-based graph coloring.
 *
 * Time Complexity:  O(V + E)
 * Space Complexity: O(V)
 */
public class BipartiteGraph {

    public static boolean isBipartite(List<Integer>[] graph) {
        int n = graph.length;

        // color[i] = -1  -> unvisited
        // color[i] =  0  -> color A
        // color[i] =  1  -> color B

        //1. Initialize the color array
        int[] color = new int[n];
        Arrays.fill(color, -1);

        //2.Iterate from each node doing a BFS. Graph may be disconnected, so we must check every component
        for (int start = 0; start < n; start++) {
            if (color[start] != -1) continue;

            // Start BFS from this node
            Queue<Integer> queue = new ArrayDeque<>();
            queue.offer(start);
            color[start] = 0; // arbitrary choice

            while (!queue.isEmpty()) {
                int u = queue.poll();

                for (int v : graph[u]) {
                    // If neighbor not colored, color it opposite
                    if (color[v] == -1) {
                        color[v] = 1 - color[u];
                        queue.offer(v);
                    }
                    // If neighbor has same color -> conflict
                    else if (color[v] == color[u]) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    
    public static void main(String[] args) {
        // Example 1: Bipartite graph
        // 0 -- 1
        // |    |
        // 3 -- 2
        List<Integer>[] graph1 = new ArrayList[4];
        for (int i = 0; i < 4; i++) graph1[i] = new ArrayList<>();

        graph1[0].add(1); graph1[1].add(0);
        graph1[1].add(2); graph1[2].add(1);
        graph1[2].add(3); graph1[3].add(2);
        graph1[3].add(0); graph1[0].add(3);

        System.out.println(isBipartite(graph1)); // true

        // Example 2: NOT bipartite (triangle)
        // 0 -- 1
        //  \  /
        //   2
        List<Integer>[] graph2 = new ArrayList[3];
        for (int i = 0; i < 3; i++) graph2[i] = new ArrayList<>();

        graph2[0].add(1); graph2[1].add(0);
        graph2[1].add(2); graph2[2].add(1);
        graph2[2].add(0); graph2[0].add(2);

        System.out.println(isBipartite(graph2)); // false
    }
}
