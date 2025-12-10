package Arrays;
/**Given an array containing 0s,1s, and 2s sort the array such that 0s,1s and 2s appear together in that order.
 * Use O (logn) time.
 * 
 * INTUTION
 * 
 * O(logn) suggests a single pass sorting and hence swapping in place. This suggests pointers.
 */
public class DutchFlagProblem {
    
    public static int[] sortColors(int[] colors){
        //edge case
        if(colors==null || colors.length==0) throw new IllegalArgumentException("Colors are either empty or null.");

        int low=0;
        int mid=0;
        int high=colors.length-1;

        while(mid<=high){
            //case of 0s and 1s
            if(colors[mid]==0){
                //swap colors[mid] and colors[low]
                swap(colors, low, mid);
                low++;
                mid++;
            }else if(colors[mid]==1){
                mid++;
            }else{
                //swaphigh and midand advance high
                swap(colors, mid,high);
                high--;
            }
        }

        return colors;
    }

    private static void swap(int[] arr, int i,int j){
        int temp=arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }

    public static void main(String[] args){
         int[] dutchFlag={2,0,2,1,1,0,2,1,0,0,1};

         for(int i: sortColors(dutchFlag)){
             System.out.print(i+",");
         }        
    }
}
