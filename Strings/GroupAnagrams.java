package Strings;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

/**Given an array of words, group anagrams together:
 * 
 * INTUTION
 * 
 * Iterate over the list =, have a hashMap where you take each word and sort it, if you find it in the map,
 * add that unsorted word under that key, and if it's not add, the sorted as key and the unsorted word as the value.
 * 
 * At the end, compose an array of arrays where each inner array are words under one key
 */
public class GroupAnagrams {
    public static List<List<String>> getGroups(String[] words){
        if(words==null || words.length==0){
            return Collections.emptyList();
        }

        Map<String,List<String>> wordMap =new HashMap<>();

        List<List<String>> result=new ArrayList<>();

        for(String word: words){
            //sort word
            char[] chars=word.toCharArray();
            Arrays.sort(chars);
            String sorted=new String(chars);

            if(wordMap.containsKey(sorted)){
                wordMap.get(sorted).add(word);
            }else{
                List<String> newWords=new ArrayList<>();
                newWords.add(word);
                wordMap.put(sorted, newWords);
            }
        }

        for(Map.Entry<String,List<String>> entry: wordMap.entrySet()){
            result.add(entry.getValue());
        }

        return result;
    }

    public static void main(String[] args) {

        String[] words = {
            "eat", "tea", "tan", "ate", "nat", "bat"
        };

        List<List<String>> groups = GroupAnagrams.getGroups(words);

        System.out.println("Grouped Anagrams:");
        for (List<String> group : groups) {
            System.out.println(group);
        }
    }
}
