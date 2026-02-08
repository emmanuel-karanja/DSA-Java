package Graph;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**You are given a list of words that are lexicographically sorted according to the alphabet of an alien language.
 * Your task is to determine the order of the characters in the alien language.
 * 
 * Rule: If the words are lexicopgrahically sorted, then,for every pair of adjacent words you can infer the precedence
 * rule between the first pair of differing characters.
 * 
 * INTUTION
 * 
 * 1. Build a graph of the characters.
 * 2. Perform a topological sort on it to determine the order, if a cycle is detected, then you know the words
 *    are not correctly sorted or the valid character order
 */
public class AlienDictionary {

   static Map<Character,Set<Character>> charGraph=new HashMap<>();
   static Map<Character,Integer> inDegree=new HashMap<>();

    public static void buildGraph(String[] words){
        // clear the graph if this is called multiple times
        charGraph.clear();
        inDegree.clear();

        //initialize
        for(String word: words){
            for (Character c: word.toCharArray()){
                if(!charGraph.containsKey(c)){
                    charGraph.put(c,new HashSet<Character>());
                }
                if(!inDegree.containsKey(c)){
                    inDegree.put(c,0);
                }
            }
        }


        //build the directed acyclic graph, explore adjacent word pairs and form the graph

        for(int i=0;i<words.length-1;i++){
            String word1=words[i];
            String word2=words[i+1];
            int minLength=Math.min(word1.length(),word2.length());

            //edge case where word1.length is greater than word2.length
            if(word1.length()>word2.length() && word1.startsWith(word2)){
                throw new IllegalArgumentException("Incorrect Lexical ordering");
            }

            //iterate on minleth, we are only interested in the FIRST uncommon character between the words
            char[] charArr1=word1.toCharArray();
            char[] charArr2=word2.toCharArray();

            for(int j=0;j<minLength;j++){
                char c1=charArr1[j];
                char c2=charArr2[j];
                if(c1 != c2){  //we don't care about matching characters
                    if(!charGraph.get(c1).contains(c2)){ 
                        charGraph.get(c1).add(c2);
                        //increment indegree of c2
                        inDegree.put(c2,inDegree.get(c2)+1);
                    }
                    break; //after the first uncommon we exit the inner loop
                }
            }
        }
    }

    //do a topological sort

    public static String topologicalSort(Map<Character,Set<Character>> graph, Map<Character,Integer> inDegree){
         //do a bfs

         Queue<Character> queue=new ArrayDeque<>();
         String charOrder="";

         // add all characters with indegree of 0
         for(Map.Entry<Character,Integer> entry: inDegree.entrySet()){
              if(entry.getValue()==0){
                queue.add(entry.getKey());
              }
         }

         while(!queue.isEmpty()){
            char currChar=queue.poll();
            charOrder+=currChar;

            //get neighbours, and decrement indegree by 1 if it falls to 0, add to queue
            Set<Character> neighbours=graph.get(currChar);
            for(char neighbor: neighbours){
                 inDegree.put(neighbor,inDegree.get(neighbor)-1);
                 if(inDegree.get(neighbor)==0){
                    queue.add(neighbor);
                 }
            }
         }

         
         return charOrder.length()==graph.size()?charOrder:"";

    }
   
    public static void main(String[] args){
        String[] words1={"wrt","wrft","er","ett","rftt"};

        buildGraph(words1);
        String order=topologicalSort(charGraph, inDegree);

        System.out.println(order);
    }  
}
