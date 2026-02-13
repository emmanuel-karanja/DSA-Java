package Graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Or Disjoint Set Union.  or UnionFind
 * 
 * What you do is: find if two nodes are part of the same connected components and add them to the same component.
 * 
 * NOTE: basically it tells you if they are part of the same set not the path itself or the distance or any of that.
 * 
 *Is a data structure optimized for two operations.

  1. Find(x) be able to find the parent of x quickly. How? It stores the relationship between nodes in terms
     of what set they belong to i.e. are they part of the same hierarchy? i.e. they share the same parent.
     We can only tell if they are of the same parent not if they are peers or conected directlyto each other.
  2. Union(x,y) be able to join two disjoint sets to which x and y belong. If nodes have the same parent, then,
    they are already in the same set. If not, we attach the one with a smaller rank to the one with a bigger rank\
    i.e. set the parent of root of x to the root of y, if x root rank<y root rank, and if they are the same
     attach to any and for that increement the rank by 1.

   What's rank: Rank is an approximation of the tree height, used to keep the Union-Find trees shallow.
    It’s only maintained for root nodes.”
 */


public class UnionFind {

    private Map<Integer, Integer> parent;
    private Map<Integer, Integer> rank;

    public UnionFind(List<Integer> nodes) {
        parent = new HashMap<>();
        rank = new HashMap<>();

        for (int node : nodes) {
            parent.put(node, node); // parent of itself
            rank.put(node, 1);
        }
    }

    // FIND with path compression
    public int find(int x) {
        if (!parent.containsKey(x)) {
            throw new IllegalArgumentException("Node not found: " + x);
        }

        if (parent.get(x) != x) {
            parent.put(x, find(parent.get(x)));
        }
        return parent.get(x);
    }

    // UNION by rank
    public boolean union(int x, int y) {
        int px = find(x);
        int py = find(y);

        if (px == py) return false;

        //add to one with lower rank
        if (rank.get(px) < rank.get(py)) {
            parent.put(px, py);
        } else if (rank.get(py) < rank.get(px)) {
            parent.put(py, px);
        } else { //equal rank put one into the other
            parent.put(py, px); //attach root py under root px and since now parent of x is a super parent,
            //we increment the rank by 1.
            int updatedPxRank=rank.get(px) + 1;
            rank.put(px, updatedPxRank);
        }

        return true;
    }

    public boolean connected(int x,int y){
        int px=find(x);
        int py=find(y);
        return px==py;
    }

    public static void main(String[] args) {

        // Create nodes
        List<Integer> nodes = Arrays.asList(1, 2, 3, 4, 5, 6);

        UnionFind uf = new UnionFind(nodes);

        // Union operations
        uf.union(1, 2);
        uf.union(2, 3);
        uf.union(4, 5);

        // Connectivity checks
        System.out.println("1 connected to 3? " + uf.connected(1, 3)); // true
        System.out.println("1 connected to 4? " + uf.connected(1, 4)); // false
        System.out.println("4 connected to 5? " + uf.connected(4, 5)); // true
        System.out.println("5 connected to 6? " + uf.connected(5, 6)); // false

        // Now connect the two components
        uf.union(3, 4);

        System.out.println("\nAfter union(3, 4):");
        System.out.println("1 connected to 5? " + uf.connected(1, 5)); // true
        System.out.println("2 connected to 6? " + uf.connected(2, 6)); // false
    }
}
