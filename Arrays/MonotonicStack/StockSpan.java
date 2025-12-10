package Arrays.MonotonicStack;

import java.util.ArrayDeque;
import java.util.Arrays;

/**Given an array of positive integers representing stock prices, with the indices as consecutive days.
 * 
 * Find the consecutive number of days(including today) when the prices was less than equal to today's price.
 * 
 * INTUTION:
 * 
 * You can brute force this using two loopsa and do it in O(n^2) i.e. j>=0 && prices[j] >=prices[i]
 * 
 * To do it in O(n)
 * 
 * it suggests we have way of finding monotonically decreasing prices from today'sdate.
 * (This is basically a monotonically increasing stack i.e. the oldest price is the smallest)
 * Basically in the stack remove ALL PRICES GREATER THAN TODAY'S PRICE.
 * We pop untilwe find a price > today's price and stop
 */
public class StockSpan {

    public static int[] getStockSpan(int[] prices){
        if(prices==null || prices.length==0){
            throw new IllegalArgumentException("Prices is null or empty;");
        }

        ArrayDeque<Integer> stack=new ArrayDeque<>();

        int[] span=new int[prices.length];
        for(int i=0;i<prices.length;i++){
         
             while(!stack.isEmpty() && prices[stack.peek()]<=prices[i]){
                 stack.pop();//remove all the prices greater than the current price
             }

             //stack top now has a price>=currentPrice
             //it's supposed to be right-left-1 but since we are considering the current price as well
             // we add 1 so it's right-left-1+1
             span[i]=stack.size()==0?i+1 : i-stack.peek();

             stack.push(i);
        }

        return span;

    }

    public static void main(String[] args){
        int[] testPrices={100,80,60,70,60,75,85};
        System.out.println(Arrays.toString(getStockSpan(testPrices)));
    }
    
}
