package Arrays.MonotonicStack;

import java.util.ArrayDeque;
import java.util.Arrays;

/**Given an array of positive integers representing stock prices, with the indices as consecutive days.
 * 
 * Find the consecutive number of days(including today) when the prices was less than  or equal to today's price.
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
 * We pop untilwe find a price > today's price and stop. And then find how many days since the current day
 * to the day with price > today's price and find the gap i.e. right-left-1+1  we add the one since today's
 * day is.
 * 
 * the argument for this is that right and left are one element past and the real
 * distance is
 * left+1, right-1 and we find the distance between via right-left+1
 * 
 * (right-1)-(left+1)+1
 * right-1-left-1+1 and since it's inclusive we add another 1.
 * 
 */
public class StockSpan {

    public static int[] getStockSpan(int[] prices){
        if(prices==null || prices.length==0){
            throw new IllegalArgumentException("Prices is null or empty;");
        }

        ArrayDeque<Integer> stack=new ArrayDeque<>();

        int[] span=new int[prices.length];
        for(int i=0;i<prices.length;i++){
         
            // Stack contains all the stock prices that have not encountered a higher price until now.
             while(!stack.isEmpty() && prices[i] >= prices[stack.peek()]){
                 stack.pop(); 
             }

             span[i]=stack.size()==0?i+1 : i-stack.peek();

             //only after do we push the value to the stack
             stack.push(i);
        }

        return span;

    }

    public static void main(String[] args){
        int[] testPrices={100,80,60,70,60,75,85};
        System.out.println(Arrays.toString(getStockSpan(testPrices)));
    }
    
}
