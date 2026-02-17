package Graph;

/**
 * PROBLEM STATEMENT:
 * ------------------
 * You are given:
 *   - An integer n representing n nodes labeled from 0 to n - 1
 *   - A list of undirected edges, where edges[i] = {u, v}
 *
 * Each edge connects node u and node v.
 *
 * Task:
 *   Return the number of disjoint sets (connected components) after
 *   all edges have been processed.
 *
 *
 * REASONING / INTUITION:
 * ---------------------
 * Initially, every node is its own disjoint set.
 * So we start with n disjoint sets.
 *
 * When we process an edge (u, v):
 *   - If u and v are already in the same set, nothing changes.
 *   - If u and v are in different sets, we merge them.
 *
 * Every successful merge reduces the number of disjoint sets by exactly 1.
 *
 * This naturally suggests using the Disjoint Set Union (Union-Find) data structure.
 *
 * Key optimizations:
 *   - Path Compression in find(): flattens the tree for near O(1) lookups
 *   - Union by Rank: keeps trees shallow
 *
 * By maintaining a counter that tracks the number of sets,
 * we can return the answer in O(1) time after processing all edges.
 *
 *
 * TIME COMPLEXITY:
 * ----------------
 *   O((n + e) * α(n)) ≈ O(n + e)
 *   where α(n) is the inverse Ackermann function
 *
 * SPACE COMPLEXITY:
 * -----------------
 *   O(n)
 */
public class UnionFindDisjointSetCount {

    private int[] parent;
    private int[] rank;
    private int count; // number of disjoint sets

    public UnionFindDisjointSetCount(int n) {
        parent = new int[n];
        rank = new int[n];
        count = n;

        // Initially, each node is its own parent
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    /**
     * Finds the representative (root) of the set containing x
     * with path compression optimization.
     */
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }

    /**
     * Unions the sets containing x and y.
     *
     * @return true  if a merge happened
     *         false if x and y were already in the same set
     */
    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        // Already in the same set → no merge
        if (rootX == rootY) {
            return false;
        }

        // Union by rank
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }

        // Successful merge reduces number of disjoint sets
        count--;
        return true;
    }

    /**
     * Returns the current number of disjoint sets.
     */
    public int getDisjointSetCount() {
        return count;
    }

    // ===================== DRIVER MAIN =====================
    public static void main(String[] args) {
        /*
         * Example:
         * n = 5 nodes: {0,1,2,3,4}
         * edges:
         *   0 -- 1
         *   1 -- 2
         *   3 -- 4
         *
         * Resulting sets:
         *   {0,1,2} and {3,4}
         * Answer = 2 disjoint sets
         */

        int n = 5;
        int[][] edges = {
            {0, 1},
            {1, 2},
            {3, 4}
        };

        UnionFindDisjointSetCount uf = new UnionFindDisjointSetCount(n);

        for (int[] edge : edges) {
            uf.union(edge[0], edge[1]);
        }

        System.out.println("Number of disjoint sets: "
                + uf.getDisjointSetCount());
    }
}
