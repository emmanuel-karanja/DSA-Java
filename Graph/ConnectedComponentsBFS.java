package Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class ConnectedComponentsBFS {

    public static Set<List<Integer>> getComponents(Map<Integer,Set<Integer>> graph){
        Set<List<Integer>> components=new HashSet<>();
        Set<Integer> visited=new HashSet<>();

        //explore the graph
       for(Map.Entry<Integer,Set<Integer>> entry: graph.entrySet()){
          if(!visited.contains(entry.getKey())){
            components.add(bfs(graph,entry.getKey(),visited));
          }
       }

       return components;
    }

    private static List<Integer> bfs(Map<Integer,Set<Integer>> graph, Integer startNode,Set<Integer> visited){
       
       Queue<Integer> queue=new ArrayDeque<>();
    
       List<Integer> component= new ArrayList<>();
       visited.add(startNode);
       queue.add(startNode);

       while(!queue.isEmpty()){
        int currNode=queue.poll();
        component.add(currNode);

            //explore neighbours
            for(int neighbor: graph.getOrDefault(currNode, Collections.emptySet())){
                if(!visited.contains(neighbor)){
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
       }
       
       return component;
    }

     public static void main(String[] args) {

        // Construct graph
        Map<Integer, Set<Integer>> graph = new HashMap<>();

        // Initialize adjacency sets
        for (int i = 1; i <= 8; i++) {
            graph.put(i, new HashSet<>());
        }

        // Component 1: 1 - 2 - 3
        graph.get(1).add(2);
        graph.get(2).add(1);
        graph.get(2).add(3);
        graph.get(3).add(2);

        // Component 2: 4 - 5
        graph.get(4).add(5);
        graph.get(5).add(4);

        // Component 3: isolated nodes (6), (7), (8)
        // Already in graph but no edges

        // Compute connected components
        Set<List<Integer>> components = ConnectedComponentsBFS.getComponents(graph);

        // Print result
        System.out.println("Connected Components:");
        for (List<Integer> component : components) {
            System.out.println(component);
        }
    }    
}
