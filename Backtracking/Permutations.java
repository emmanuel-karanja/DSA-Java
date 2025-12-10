package Backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**Find all the possible permutations of elements of the letters of as tring.
 * 
 * INTUTION
 * 
 * Use backtracking
 */
public class Permutations {

    public static List<String> getPermutations(String str){
        if(str==null || str.length()==0){
            return Collections.emptyList();
        }

        //split the string in chars

        List<Character> choices=new ArrayList<>();
        for(int i=0;i<str.length();i++){
            choices.add(str.charAt(i));
        }

        List<String> solutions=new ArrayList<>();
        
        backtrack(choices, new ArrayList<Character>(),solutions);

        return solutions;

    }

    private static void backtrack(List<Character> choices, List<Character> candidate, List<String> solutions){

         if(candidate.size()==choices.size()){
             //add solution
             StringBuilder sb=new StringBuilder();
             for(Character c: candidate){
                sb.append(c);
             }
             solutions.add(sb.toString());
             return;
         }

         //make the choices
         for(char c: choices){
            //check if char in choices
           if(candidate.contains(c)) continue;
            
            //append char
            candidate.add(c);
            backtrack(choices, candidate, solutions);
            candidate.remove(candidate.size()-1); //remove the last remove(c) doesn't remove the last one.
         }
    }

    public static void main(String[] args){
        String s="abc";

        List<String> perms=getPermutations(s);

        for(String str:perms){
            System.out.println(str);
        }
    }
}
