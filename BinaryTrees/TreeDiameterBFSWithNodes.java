package BinaryTrees;
/**Enhancement:
 * 
 *  Keep a parentMap i.e. nod-->parent
 *  it does nothing to help you calculate the diameter it just allows you to be able to generate the path later.
 * 
 *  
 */
import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

public class TreeDiameterBFSWithNodes {

    static class Pair {
        TreeNode node;
        int dist;
        Pair(TreeNode node, int dist) {
            this.node = node;
            this.dist = dist;
        }
    }

    // BFS to find farthest node and optionally keep parent map
    private static Pair bfs(TreeNode start, Map<TreeNode, TreeNode> parentMap) {
        Queue<Pair> q = new ArrayDeque<>();
        Set<TreeNode> visited = new HashSet<>();
        q.offer(new Pair(start, 0));
        visited.add(start);

        // Keep track of the farthest node
        Pair farthest = new Pair(start, 0);

        // Node to parent
        parentMap.put(start, null); // root has no parent

        while (!q.isEmpty()) {
            Pair current = q.poll();
            TreeNode node = current.node;
            int dist = current.dist;

            if (dist > farthest.dist) {
                farthest = current;
            }

            if (node.left != null && !visited.contains(node.left)) {
                visited.add(node.left);
                q.offer(new Pair(node.left, dist + 1));
                parentMap.put(node.left, node);
            }
            if (node.right != null && !visited.contains(node.right)) {
                visited.add(node.right);
                q.offer(new Pair(node.right, dist + 1));
                parentMap.put(node.right, node);
            }
        }

        return farthest;
    }

    // Calculate diameter and return endpoints
    public static int diameter(TreeNode root, TreeNode[] endpoints) {
        if (root == null) return 0;

        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        // Step 1: BFS from root to find farthest node u
        Pair u = bfs(root, parentMap);

        parentMap.clear();
        // Step 2: BFS from u to find farthest node v
        Pair v = bfs(u.node, parentMap);

        // Optional: return endpoints
        endpoints[0] = u.node;
        endpoints[1] = v.node;

        //Optional: Get path
        List<TreeNode> path = new ArrayList<>();
        TreeNode curr = v.node;
        while (curr != null) {
            path.add(curr);
            curr = parentMap.get(curr); // move up toward the start node
        }
        Collections.reverse(path); // path now goes from u -> v


        return v.dist; // diameter in edges
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.left.left = new TreeNode(6);

        TreeNode[] endpoints = new TreeNode[2];
        int dia = diameter(root, endpoints);

        System.out.println("Diameter of tree = " + dia);
        System.out.println("Endpoints of diameter: " + endpoints[0].val + " and " + endpoints[1].val);
    }
}
