package Arrays;
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
 * This does it in time O(n) and space O(1)
 * */

public class DominoRotationsEfficient {

    public static int getMinFlips(int[]A, int[]B){
        if(A==null || A.length==0 || B==null || B.length==0){
            throw new IllegalArgumentException("Either A or B is null or empty.");
        }

        if(A.length!=B.length){
            throw new IllegalArgumentException("A and B not of equal length.");
        }

        int rotations= Math.min(checkCandidate(A[0], A, B),checkCandidate(B[0],A,B));
        return rotations ==Integer.MAX_VALUE? -1: rotations;

    }

    private static int checkCandidate(int candidate, int[] A, int[] B){
        //Candidate ie eith A[0] or B[0]

        int rotateA=0;
        int rotateB=0;
        for(int i=0;i<A.length;i++){
            //if A[i] and B[i] are not equal to candidate, that's an impossible case
            if(A[i] !=candidate && B[i] !=candidate) return Integer.MAX_VALUE;

            //if A is not the candidate, you need to rotate A and same for B
            if(A[i]!= candidate) rotateA++;
            if(B[i] !=candidate) rotateB++;
        }

        return Math.min(rotateA,rotateB);
    }

     public static void main(String[] args){
        int[] A1={2,1,2,4,2,2};
        int[] B1={5,2,6,2,3,2};

        System.out.println("MinFlips1:"+getMinFlips(A1, B1));

        int[] A2={3,5,1,2,3};
        int[] B2={3,6,3,3,4};
         System.out.println("MinFlips2:"+getMinFlips(A2, B2));

    }
    
}

