package BinarySearch;
/**You are given k sorted arrays, merge them sorted in O(logk) time 
 * 
 * INTUTIONA
 * 
 *Divide and conquor ie. split it into two arrays and then merge upwards
*/
public class MergeKSortedArray {

     private static int[] mergePair(int[] a1,int[] a2){
        if(a1==null || a2==null){
            throw new IllegalArgumentException("A1 or A2 are null");
        }

        if(a1.length==0) return a2;
        if(a2.length==0) return a1;

        int[] merged=new int[a1.length+a2.length];

        int i=0;
        int j=0;
        int k=0;
        while( i <a1.length && j<a2.length){
            if(a1[i]<a2[j]){
               merged[k++]=a1[i++];
            }else{
                merged[k++]=a2[j++];
            }
        }

        while (i < a1.length) {
            merged[k++] = a1[i++];
        }

        // copy leftover from a2
        while (j < a2.length) {
            merged[k++] = a2[j++];
        }
        return merged;
     }

     private static int[] mergeRange(int[][] arrays, int left, int right){
         //base case
         if(left==right) return arrays[left];

         //calculate mid as follows to prevent interger overflow for very large values of left or right
         int mid=left+(right-left)/2; 

         int[] leftMerged=mergeRange(arrays,left,mid);
         int[] rightMerged=mergeRange(arrays,mid+1,right);

         return mergePair(leftMerged,rightMerged);
     }


     public static int[] mergeKSorted(int[][] arrays){

        if(arrays==null || arrays.length==0)throw new IllegalArgumentException("Arrays is null or empty");

        int left=0;
        int right=arrays.length-1;

        return mergeRange(arrays,left,right);
     }
    
     public static void main(String[] args) {
        int[][] arrays = {
            {1, 4, 5},
            {1, 3, 4},
            {2, 6}
        };

        int[] result = mergeKSorted(arrays);

        for (int x : result) {
            System.out.print(x + " ");
        }
    }
}
