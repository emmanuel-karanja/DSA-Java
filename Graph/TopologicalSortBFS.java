package Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**Given an arrayof node pairs that represent a graph and  that each pair represents and edge e.g. {a,b} means a->b, 
 * find the topological sort of the graph*/
public class TopologicalSortBFS {

    public List<Character> getTopologicalSort(char[][] edges){
        Map<Character, Integer> inDegree = new HashMap<>();
        Map<Character, Set<Character>> graph = new HashMap<>();

        // Build graph and indegree
        for(char[] edge : edges){
            char a = edge[0];
            char b = edge[1];

            graph.computeIfAbsent(a, k -> new HashSet<>()).add(b);

            inDegree.put(a, inDegree.getOrDefault(a, 0)); // ensure a exists
            inDegree.put(b, inDegree.getOrDefault(b, 0) + 1);
        }

        // Queue of nodes with indegree 0
        Queue<Character> queue = new ArrayDeque<>();
        for(Map.Entry<Character, Integer> entry : inDegree.entrySet()){
            if(entry.getValue() == 0){
                queue.offer(entry.getKey());
            }
        }

        List<Character> sorted = new ArrayList<>();

        while(!queue.isEmpty()){
            char node = queue.poll();
            sorted.add(node);

            for(char neighbor : graph.getOrDefault(node, new HashSet<>())){
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if(inDegree.get(neighbor) == 0){
                    queue.offer(neighbor);
                }
            }
        }

        // If there was a cycle, return empty list
        return sorted.size() == inDegree.size() ? sorted : new ArrayList<>();
    }

    public static void main(String[] args) {
        TopologicalSortBFS sorter = new TopologicalSortBFS();
        char[][] edges = {{'a','b'},{'b','c'},{'a','c'}};
        System.out.println(sorter.getTopologicalSort(edges)); // [a, b, c]
    }
}
