package Arrays;
/**Given an array of positive integers (which may include 0), find the max area histogram:
 * 
 * INTUTION:
 * 
 * Divide and Conquer:-->Find the minimum height and then calculate the area bounded by it,
 * find the max areaof the left bars to it and the right bars.
 * maxArea=Math.max(bounded,leftArea,rightArea)
 * 
 * Time O (n^2)
 * 
 * NOTE: When recursion splits the data set in half, the time complexity is n(logn) hence the time complexity
 * for the merge sort. And space O(logn)
*/
 class MaxAreaHistogram {

    private static int getMinHeightIndex(int[] heights, int left,int right){

        int minHeightIndex=left; //we start from left
        for(int i=left;i<=right;i++){
            if(heights[i]<heights[minHeightIndex]){
                minHeightIndex=i;
            }
        }

        return minHeightIndex;
    }

    public static int maxAreaBruteForce(int[] heights){
        if(heights==null || heights.length==0) throw new IllegalArgumentException("Heights are null or empty");
        
        return findMaxArea(heights, 0, heights.length-1);
        

    }

    private static int findMaxArea(int[] heights, int left, int right){

        if(left==right) return heights[left];  //just one bar's area this is the base case

        int minHeightIndex=getMinHeightIndex(heights, left, right);

        int boundedArea=heights[minHeightIndex] *(right-left+1);
        int leftArea=minHeightIndex==left? 0: findMaxArea(heights,left,minHeightIndex-1); //what if it's 0
        int rightArea=minHeightIndex==right? 0:findMaxArea(heights,minHeightIndex+1,right);

        return Math.max(boundedArea,Math.max(leftArea,rightArea));
    }

     public static void main(String[] args) {
        int[] heights = {2, 1, 5, 6, 2, 3};
        System.out.println(maxAreaBruteForce(heights)); // expected: 10
    }   
}
