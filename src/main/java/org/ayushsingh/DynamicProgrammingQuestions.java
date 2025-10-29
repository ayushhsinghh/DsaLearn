package org.ayushsingh;

public class DynamicProgrammingQuestions {

    // Kadane's Algorithm
    // Buy Stock and Sell - 1
    public int maxProfit(int[] prices) {
        if(prices.length == 1) return 0;

        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for(int price : prices) {
            if(price < minPrice) {
                minPrice = price;
            } else {
                if(price - minPrice > maxProfit) {
                    maxProfit = price - minPrice;
                }
            }
        }

        return maxProfit;
    }

}
