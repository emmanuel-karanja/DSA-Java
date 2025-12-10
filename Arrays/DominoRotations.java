package Arrays;

import java.util.HashMap;
import java.util.Map;

/**Given two rows of Dominos, A and B of the same length. Each position contains a number of the face of
 * the domino. Find the minimum number of flips to make the rows the same.
 * 
 * INTUTION:
 * 
 * Find the most frequent element in either rows, and then record the indices of that. And then,
 * consider the second domino and at those indices, find if the element there is the most common in the first
 * one, if not, you can't flip. 
 * 
 * KEY:  The only numbers that can fill either row are either A[0] or B[0] so the most common is either one of them.
 * 
 * So, get A[0] and B[0], find the count of either of them. And find the min. Of n-countA[0] and n-countB[0]
 * 
 * The first case:  Impossible case is: iterate over A and B and compare A[i] and B[i] if they are not equal,
 *  For B[i] find if it's equal to A[0] and vice-versa for A[i]
 * 
 */
public class DominoRotations {

    public static int getMinFlipCount(int[] A, int[]B){
        if(A==null || A.length==0 || B==null || B.length==0){
            throw new IllegalArgumentException("Either A or B are null or empty.");
        }

        if(A.length!=B.length){
            throw new IllegalArgumentException("Both A and B must be of equal length.");
        }

        final int n=A.length;

        //use a hashmap to store the frequency count
        Map<Integer,Integer> countA=new HashMap<>();
        Map<Integer,Integer> countB=new HashMap<>();
        Map<Integer,Integer> countSame=new HashMap<>();
        
        for(int i=0;i<A.length;i++){
            //record counts
            countA.put(A[i],countA.getOrDefault(A[i],0)+1);
            countB.put(B[i],countB.getOrDefault(B[i],0)+1);

            if(A[i]==B[i]){
                countSame.put(A[i], countSame.getOrDefault(A[i],0)+1);
            }
        }

        //check candidates, why? the only numbers that can fill either row are either A[0] or B[0]
        for(int x: new int[]{A[0],B[0]}){
            //Why?
            if(countA.getOrDefault(x,0) + countB.getOrDefault(x,0)
                 - countSame.getOrDefault(x,0)==n){

                 int mostCommonInA=n-countA.getOrDefault(x, 0);
                 int mostCommonInB=n-countB.getOrDefault(x,0);
                 return Math.min(mostCommonInA,mostCommonInB);
                }
        }

        return -1; //not possible
    }

    public static void main(String[] args){
        int[] A1={2,1,2,4,2,2};
        int[] B1={5,2,6,2,3,2};

        System.out.println("MinFlips1:"+getMinFlipCount(A1, B1));

        int[] A2={3,5,1,2,3};
        int[] B2={3,6,3,3,4};
         System.out.println("MinFlips2:"+getMinFlipCount(A2, B2));

    }
    
}
