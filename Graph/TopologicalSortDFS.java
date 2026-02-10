package Graph;

import java.util.*;

/**
 * Topological Sort using DFS (with cycle detection)
 *
 * Mnemonic: "Visit, Dive, Done, Reverse"
 * 1. Visit (visiting) → mark node as in-progress (recursion stack)
 * 2. Dive (DFS neighbors) → recursively explore all neighbors
 * 3. Done (visited) → remove from visiting, mark fully processed
 * 4. Reverse (post-order append) → add node after exploring neighbors, reverse list at the end
 *
 * WHY RECURSIVE? DFS recursion makes it easy to track cycles via the recursion stack.
 */
public class TopologicalSortDFS {

    /**
     * Computes a topological ordering of nodes given directed edges.
     * Returns empty list if a cycle is detected.
     */
    public List<Character> getTopologicalSort(char[][] edges) {
        // Build graph and collect all unique nodes
        Map<Character, Set<Character>> graph = new HashMap<>();
        Set<Character> nodes = new HashSet<>();

        for (char[] edge : edges) {
            char from = edge[0];
            char to = edge[1];

            graph.computeIfAbsent(from, k -> new HashSet<>()).add(to);
            nodes.add(from);
            nodes.add(to);
        }

        Set<Character> visited = new HashSet<>();
        Set<Character> visiting = new HashSet<>(); // for cycle detection
        List<Character> sorted = new ArrayList<>();

        // Perform DFS on unvisited nodes
        for (char node : nodes) {
            if (!visited.contains(node)) {
                if (!dfs(node, graph, visited, visiting, sorted)) {
                    return new ArrayList<>(); // cycle detected
                }
            }
        }

        // Reverse to get correct topological order
        Collections.reverse(sorted);
        return sorted;
    }

    /**
     * DFS helper function.
     * Returns false if a cycle is detected.
     */
    private boolean dfs(char node, Map<Character, Set<Character>> graph,
                        Set<Character> visited, Set<Character> visiting,
                        List<Character> sorted) {

        if (visiting.contains(node)) return false; // cycle detected
        if (visited.contains(node)) return true;   // already done

        visiting.add(node); // mark node as in-progress

        for (char neighbor : graph.getOrDefault(node, Collections.emptySet())) {
            if (!dfs(neighbor, graph, visited, visiting, sorted)) {
                return false;
            }
        }

        visiting.remove(node); // done exploring
        visited.add(node);     // mark fully processed
        sorted.add(node);      // post-order append
        return true;
    }

    public static void main(String[] args) {
        TopologicalSortDFS sorter = new TopologicalSortDFS();
        char[][] edges = {
            {'a', 'b'},
            {'b', 'c'},
            {'a', 'c'},
            {'d', 'c'}
        };

        List<Character> topoOrder = sorter.getTopologicalSort(edges);
        System.out.println(topoOrder); // one possible order: [d, a, b, c]
    }
}
