package Arrays;
/**Find the array that's the result of the product of the array elements at each index except itself.
 * e.g. nums = [1, 2, 3, 4]
       output = [24, 12, 8, 6]

UNDERSTANDING PREFIX AND SUFFIX PRODUCT:

product_except_self[i] = product of all elements BEFORE i  *  product of all elements AFTER i

i.e.
prefix[0] = 1
prefix[i] = nums[0] * nums[1] * ... * nums[i-1]

and for the suffix

suffix[n-1] = 1
suffix[i] = nums[i+1] * nums[i+2] * ... * nums[n-1]

result:

result[i] = prefix[i] * suffix[i]

Optimization:
We don’t need two extra arrays. We can:

Fill result[] with prefix products first.

Walk from the end with a single suffix variable and multiply in-place.
 
 */
public class ProductOfArrayExceptSelf {

    public static int[] getProductExceptSelf(int[] nums){
        if(nums==null || nums.length==0){
            throw new IllegalArgumentException("Nums is empty or null.");
        }

        final int n=nums.length;
        //do the prefix product
        //i.e. prefixp[0]=1, 
        int[] result=new int[n];

        result[0]=1;
        for(int i=0;i<n;i++){
            result[i]=nums[i-1]*result[i-1]; //  product of all before
        }

        //do the suffix product
        int suffix=1;
        for(int i=n-1;i>=0;i--){
            result[i]=result[i]*suffix; //result[i] already contains the prefix product
            suffix*=nums[i];
        }

        return result;
    }

    public static int[] getProductExceptSelfOnSpace(int[] nums){

        if(nums==null || nums.length==0){
            throw new IllegalArgumentException("Nums is empty or null.");
        }

        final int n=nums.length;
        //do the prefix product
        //i.e. prefixp[0]=1, 
        

        int[] prefix=new int[n];
        prefix[0]=1;
        for(int i=0;i<n;i++){
            prefix[i]=nums[i-1]*prefix[i-1];
        }

        int[] suffix=new int[n];
        suffix[n-1]=1;
        for(int i=n-1;i>=0;i++){
            suffix[i]=nums[i+1]+suffix[i+1]; // the previous number since we are startingin the back i.e
        }

        int[] result=new int[n];
        for(int i=0;i<n;i++){
            result[i]=prefix[i]*suffix[i];
        }

        return result;

    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        int[] res = getProductExceptSelf(nums);

        for (int num : res) {
            System.out.print("O(1) space: "+num + " ");
        }

         int[] res2 = getProductExceptSelfOnSpace(nums);

        for (int num : res2) {
            System.out.print("O(n) space: "+ num + " ");
        }
        // Output: 24 12 8 6
    }
    
}
