package BinarySearch;

import java.util.Arrays;

/**Given positions to place servers, minimize the maximum distance between servers.
 * INTUTION:
 * 
 * Maximize the min means: 'The largest distance is at most minDistance' or 'space in as much as possible.'
 * 
 * 
 * We can sort the positions
 * We want to find the min position which is positions[0] and the maximum is positions[n-1] -positions[0]
 * 
 * We search within that space. 
 * 
 * Bear in mind that positions[i] - positions[i-1] are not even for i from 0...n-1
 * 
 * so, the feasibility test is, how many servers can we place between positions[i] and positions[i-1]
 * 
 * 
 */
public class ServerPlacement {
    
    public static int getMinMaxServers(int[] positions, int servers){
        //find lower limit
        Arrays.sort(positions);
 
        int left=0;  //min is 0 since we'll have a gap.
        int right=positions[positions.length-1]-positions[0];

        int minDistance=0;

        while(left<=right){
            int mid=left+(right-left)/2;

            if(canPlaceServer(positions, servers, mid)){
                //try a smaller distance minimize maximum distance 
                //keep searching left, this is the same as the FirstOcurrence binary search
                minDistance=mid;
                right=mid-1;
            }else{
                left=mid+1;// smaller
            }
        }

        return minDistance;
    }
    public static boolean canPlaceServer(int[] positions, int servers, int minDistance){
        final int n=positions.length;
        int placedServers=1;
        //we place the first server at the first place
        //how many servers can we place?

        for(int i=1;i<n;i++){
            int gap=positions[i]-positions[i-1];
            int serversInThisGap=(gap-1)/minDistance;  //no need to Math.floor since 
            placedServers+=serversInThisGap;
        }

        return placedServers <=servers;
    }
}
