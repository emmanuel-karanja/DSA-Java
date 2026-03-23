package Sorting;

public class CountInversions {

    /**
     * Problem: Count the number of inversions in an array.
     * Definition: An inversion is a pair (i, j) such that i < j and arr[i] > arr[j].
     * Approach: Use a modified merge sort.
     * - During the merge step, if an element from the right array is placed before
     *   an element from the left array, all remaining elements in the left array
     *   form inversions with it.
     * 
     * 
     * REASONING
     * 
     * If we split an array into two along the mid to get left and right subarrays,
       if the array is sorted,we know every element on the left from 0 to mid is smaller or equal
       to every element in the right from mid+1 to n-1. 

        i <=mid and j<=right
        i.e. arr[i] <= arr[j]  is the expcted condition.

        if arr[i]>arr[j] then we have an inversion and not only that,
        every element after i to mid, is also an inversion with the element at j.

        so we know the count of elements forming an ivrsion from i to mid are

         (mid-i)+1 (since it's inclusive)


         Time: O(nlogn)
     */



    // Function to count inversions
    public static long countInversions(int[] arr) {
        int[] temp = new int[arr.length];
        return mergeSortAndCount(arr, temp, 0, arr.length - 1);
    }

    // Recursive merge sort function that counts inversions
    private static long mergeSortAndCount(int[] arr, int[] temp, int left, int right) {
        long invCount = 0;
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Count inversions in left subarray
            invCount += mergeSortAndCount(arr, temp, left, mid);

            // Count inversions in right subarray
            invCount += mergeSortAndCount(arr, temp, mid + 1, right);

            // Count inversions while merging
            invCount += mergeTwoAndCount(arr, temp, left, mid, right);
        }
        return invCount;
    }

    // Merge two sorted halves and count cross inversions
    private static long mergeTwoAndCount(int[] arr, int[] temp, int left, int mid, int right) {
        int i = left;     // Initial index of left subarray
        int j = mid + 1;  // Initial index of right subarray
        int k = left;     // Initial index of merged array
        long invCount = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {    //  Element to the right is  < elment to the left side.
                temp[k++] = arr[j++];
                // All remaining elements in left subarray (arr[i..mid]) are greater than arr[j]
                invCount += (mid - i + 1);
            }
        }

        // Copy remaining elements
        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        // Copy merged elements back to original array
        for (i = left; i <= right; i++) {
            arr[i] = temp[i];
        }

        return invCount;
    }

    // Driver function
    public static void main(String[] args) {
        int[] arr = {2, 4, 1, 3, 5};
    

        long inversionCount = countInversions(arr);
        System.out.println("Number of inversions: " + inversionCount);
    }
}