package Graph;

import java.util.*;

public class TopologicalSortDFS {

    public List<Character> getTopologicalSort(char[][] edges) {
        // Build graph
        Map<Character, Set<Character>> graph = new HashMap<>();
        Set<Character> nodes = new HashSet<>(); // keep track of all unique nodes

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

        for (char node : nodes) {
            if (!visited.contains(node)) {
                if (!dfs(node, graph, visited, visiting, sorted)) {
                    return new ArrayList<>(); // cycle detected, no topological order
                }
            }
        }

        Collections.reverse(sorted); // reverse to get correct order
        return sorted;
    }

    private boolean dfs(char node, Map<Character, Set<Character>> graph,
                        Set<Character> visited, Set<Character> visiting,
                        List<Character> sorted) {
        visiting.add(node);

        for (char neighbor : graph.getOrDefault(node, new HashSet<>())) {
            if (visited.contains(neighbor)) continue;
            if (visiting.contains(neighbor)) return false; // cycle detected
            if (!dfs(neighbor, graph, visited, visiting, sorted)) return false;
        }

        visiting.remove(node);
        visited.add(node);
        sorted.add(node); // add after visiting neighbors
        return true;
    }

    public static void main(String[] args) {
        TopologicalSortDFS sorter = new TopologicalSortDFS();
        char[][] edges = {{'a','b'},{'b','c'},{'a','c'},{'d','c'}};
        System.out.println(sorter.getTopologicalSort(edges)); // one possible order: [d, a, b, c]
    }
}
