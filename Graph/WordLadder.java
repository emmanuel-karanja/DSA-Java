package Graph;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/*
 You are given: a beginWord, and an endWord, and a wordlist (dictionary of words)
 in each step, you can only change one character, and the resulting word must be in the word list,
 return the minimum number of steps to move from beginWord to endWord, if it's not possible return 0.

 INTUTION:
 When you hear minimum, think BFS, starting with the begiWord, change one character and at each position
  i from 0 to word.length-1 and if the word exists in the dictionary, then,add one step, and then from that word try
  to generate the next word.

  The challenge here is how to generate the neighboring word
 **/

class DistPair{
    public int steps;
    public String word;

    public DistPair(String word, int steps){
        this.word=word;
        this.steps=steps;
    }
}
public class WordLadder {
    
    public static void main(String[] args){
        String begin = "hit";
        String end = "cog";
        String[] wordList = {"hot", "dot", "dog", "lot", "log", "cog"};

        int steps = getMinSteps(begin, end, wordList);
        System.out.println("Minimum steps: " + steps); // Expected: 5

    }

    public static int getMinSteps( String beginWord, String endWord, String[] wordList){
        Set<String> dictionary= new HashSet<>();
        //insert to a set to get unique words dictionary
        for(String word: wordList){
            dictionary.add(word);
        }

        Set<String> visited=new HashSet<>();

        Queue<DistPair> queue=new ArrayDeque<>();

        queue.add(new DistPair(beginWord,1)); //note we begin at step 1
        visited.add(beginWord);

        //set word generation limits a to z
        int aCode=(int) 'a';
        int zCode=(int) 'z';

        while(!queue.isEmpty()){
            DistPair currPair=queue.poll();

            String currWord=currPair.word;
            int currSteps=currPair.steps;
            //generate the neighbour
            for(int i=0;i<currWord.length();i++){
                for(int c=aCode;c<=zCode;c++){
                    char newChar = (char) c;
                    String nextWord = currWord.substring(0, i) + newChar + currWord.substring(i + 1);
                    //check if it's the endWord
                    if(nextWord.equals(endWord)) return currSteps+1;

                    //check if in set, and visit
                    if(dictionary.contains(nextWord) && !visited.contains(nextWord)){
                        visited.add(nextWord);
                        queue.add(new DistPair(nextWord,currSteps+1));
                    }
                }
            }
        }

        return 0; //we never found it
    }
}
