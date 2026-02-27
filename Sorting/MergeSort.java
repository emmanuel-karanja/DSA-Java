package Sorting;

import java.util.Arrays;

/**Given an array of unsorted array, sort them in  O(nlogn) time.
 * 
 * INTUTION
 * Merge sort:
 * 
 * Use divide and conquor
 * 
 * time O(nlogn)
*/
public class MergeSort {

    public static int[] mergeSort(int[] unsorted){
        if (unsorted == null || unsorted.length <= 1) return unsorted;
        int left=0;
        int right=unsorted.length-1;
        return mergeRange(unsorted,left,right);
    }
    public static int[] mergeRange(int[] unsorted,int left, int right){
        
        if(left==right){
            // return an arrayof 1  element sorted
            int[] result= {unsorted[left]};
            return result;
        }

        int mid=left+(right-left)/2;

        int[] sortedLeft=mergeRange(unsorted,left,mid);
        int[] sortedRight=mergeRange(unsorted,mid+1,right);

        return mergeTwo(sortedLeft, sortedRight);

    }

    

    private static int[] mergeTwo(int[] arr1, int[] arr2){
        if(arr1==null || arr1.length==0) return arr2;
        if(arr2==null || arr2.length==0) return arr1;

        final int m=arr1.length;
        final int n=arr2.length;

        int[] merged=new int[m+n];

        int i=0;
        int j=0;
        int k=0;
        while(i<m && j<n){
            if(arr1[i]<=arr2[j]){
                merged[k++]=arr1[i++];
            }else{
                merged[k++]=arr2[j++];
            }
        }

        //add remaining
        while(i<m){
            merged[k++]=arr1[i++];
        }

        while(j<n){
            merged[k++]=arr2[j++];
        }

        return merged;
    }

    public static void main(String[] args){
        int[] ar1={3,2,1,6,4,8,5,7};

        System.out.println(Arrays.toString(mergeSort(ar1)));
    }
    
}
