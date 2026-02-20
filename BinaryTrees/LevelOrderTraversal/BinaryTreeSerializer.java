package BinaryTrees.LevelOrderTraversal;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

class TreeNode {
    public int value;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.value = val;
        this.left = null;
        this.right = null;
    }
}

public class BinaryTreeSerializer {

    // =========================
    // SERIALIZE (BFS)
    // =========================
    public static String serialize(TreeNode root) {

        if (root == null) return "";

        Queue<TreeNode> queue = new LinkedList<>(); //accepts nulls, ArrayQueue doesn't.
        StringBuilder sb = new StringBuilder();

        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            if (node != null) {
                sb.append(node.value).append(",");
                queue.offer(node.left);
                queue.offer(node.right);
            } else {
                sb.append("#,");
            }
        }

        // Alternative trim the trailing '#' for leaf nodes.
        return sb.toString();
    }

    // =========================
    // DESERIALIZE (BFS)
    // =========================
    public static TreeNode deserialize(String data) {

        if (data == null || data.isEmpty()) return null;

        String[] tokens = data.split(",");

        TreeNode root = new TreeNode(Integer.parseInt(tokens[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;

        while (!queue.isEmpty() && i < tokens.length) {
            TreeNode parent = queue.poll();

            // left child
            if (!tokens[i].equals("#")) {
                TreeNode left = new TreeNode(Integer.parseInt(tokens[i]));
                parent.left = left;
                queue.offer(left);
            }
            i++;

            // right child
            if (i < tokens.length && !tokens[i].equals("#")) {
                TreeNode right = new TreeNode(Integer.parseInt(tokens[i]));
                parent.right = right;
                queue.offer(right);
            }
            i++;
        }

        return root;
    }

    // =========================
    // TEST
    // =========================
    public static void main(String[] args) {

        /*
                1
              /   \
             2     3
                  /
                 4
        */

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);

        System.out.println(root);
        String serialized = serialize(root);
        System.out.println("Serialized:");
        System.out.println(serialized);

        TreeNode deserializedRoot = deserialize(serialized);
        System.out.println("Serialized again (after deserialization):");
        System.out.println(serialize(deserializedRoot));
    }
}
