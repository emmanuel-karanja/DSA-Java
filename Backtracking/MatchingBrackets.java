package Backtracking;

import java.util.ArrayList;
import java.util.List;

//**Create a string of matching brackets are properly closed. Given an integer n that represents the number of open or closing
// e.g. n =2 ()(), (())  */
public class MatchingBrackets {
    public static List<String> createMatchingBraces(int n){
        if(n==0){
            return List.of("");
        }

        List<String> solutions=new ArrayList<>();

        backtrack("",0,0,solutions,n);

        return solutions;
    }

    private static void backtrack(String candidate, int openingCount, int closingCount, List<String> solutions ,int n){
        if(openingCount+closingCount==2*n){
             solutions.add(candidate);
             return;
        }

        //do selection
        //select an opening brace
        if(openingCount < n){
            backtrack(candidate+"(",openingCount+1, closingCount,solutions,n);
        }

        //select a closing brace
        if(closingCount< openingCount){
            backtrack(candidate+")",openingCount,closingCount+1, solutions,n);
        }
    }

    public static void main(String[] args){
        
        List<String> list=createMatchingBraces(3);

        for(String s: list){
            System.out.println(s);
        }
    }
}
