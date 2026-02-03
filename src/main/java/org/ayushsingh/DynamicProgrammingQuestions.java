package org.ayushsingh;

import java.util.Arrays;
import java.util.stream.IntStream;

public class DynamicProgrammingQuestions {

    static int[][] dp = new  int[100][100];

    static {
        IntStream.range(0, dp.length).forEach(i -> Arrays.fill(dp[i], -1)); //Initialize all Elements with -1
    }

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

    // 0/1 Knapsack Bottom's Up
    public static int knapsack(int[] prices, int[] weights, int W, int n) {
        if(n == 0 || W == 0) return 0; // BaseCase

        if(dp[W][n] != -1) {
            return dp[W][n];   // Memoization
        }

        if(W >= weights[n-1]) {
            dp[W][n] =  Math.max(
                    prices[n-1] + knapsack(prices, weights, W-weights[n-1], n-1), // Pick
                    knapsack(prices, weights, W, n-1) // Not-Pick
            );
        } else {
            dp[W][n] =  knapsack(
                    prices, weights, W, n-1); // Not Pick
        }

        return dp[W][n];
    }


    // 0-1 Knapsack
    // TopDown Approach
    public static int knapsackDP(int[] prices, int[] weights, int W, int n) {
        // init base cases
        IntStream.range(0, n).forEach(i -> dp[0][i] = 0);
        IntStream.range(0, W).forEach(i -> dp[i][0] = 0);

        for (int i = 1; i <= W; i++) {      // capacity W
            for (int j = 1; j <= n; j++) {  // items n
                if (i >= weights[j - 1]) {  // Only try to pick if Bag capacity is greater than item weight
                    dp[i][j] = Math.max(
                            prices[j - 1] + dp[i - weights[j - 1]][j - 1], // Pick
                            dp[i][j - 1] // Not Pick
                    );
                } else {
                    dp[i][j] = dp[i][j - 1]; // Not Pick
                }
            }
        }

        return dp[W][n];
    }

    // Longest Common Subsequence (LCS)
    public static int LCSTopDown(String s1, String s2, int n1, int n2) {
        if(n1 == 0 || n2 == 0) return 0;

        if(dp[n1][n2] != -1) return dp[n1][n2];

        if(s1.charAt(n1-1) == s2.charAt(n2-1)) {
            dp[n1][n2] = 1 + LCSTopDown(s1, s2, n1-1, n2-1);
        } else {
            dp[n1][n2] = Math.max(LCSTopDown(s1, s2, n1, n2-1), LCSTopDown(s1, s2, n1-1, n2));
        }

        return dp[n1][n2];
    }

    public static int LCSButtomUp(String s1, String s2, int n1, int n2) {
        for(int i  = 0 ; i < n1+1 ; i++) {
           dp[i][0] = 0;
        }
        for(int i  = 0 ; i < n2+1 ; i++) {
            dp[0][i] = 0;
        }

        for (int i = 1; i < n1+1; i++) {
            for (int j = 1; j < n2+1; j++) {
                if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                } else {
                    dp[i][j] = Math.max(dp[i-1][j] , dp[i][j-1]);
                }
            }
        }

        return dp[n1][n2];
    }

    //Longest Common Substring
    public static int LongestCommonSubstring(String s1, String s2, int n1, int n2) {
        for(int i  = 0 ; i < n1+1 ; i++) {
            dp[i][0] = 0;
        }
        for(int i  = 0 ; i < n2+1 ; i++) {
            dp[0][i] = 0;
        }

        int max = 0;

        for (int i = 1; i < n1+1; i++) {
            for (int j = 1; j < n2+1; j++) {
                if(s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                } else {
                    dp[i][j] = 0;
                }
                max = Math.max(max, dp[i][j]);
            }
        }

        return max;
    }

    // Longest Palindromic Subsequence
    // Note: Same as LCS, just make a new string as the Reverse of First
    public static int LongestPalindromicSubsequence(String s1) {
        String s2 = new StringBuilder(s1).reverse().toString();
        return LongestPalindromicSubsequence(s1, s2, s1.length() -1, s2.length()-1);
    }

    public static int LongestPalindromicSubsequence(String s1, String s2, int n1, int n2) {
        if(n1 == 0 || n2 == 0) return 0;

        if(s2.charAt(n1-1) == s2.charAt(n2-1)) {
            return 1 + LongestPalindromicSubsequence(s1, s2, n1-1, n2-1);
        } else {
            return Math.max(LongestPalindromicSubsequence(s1, s2, n1-1, n2),  LongestPalindromicSubsequence(s1, s2, n1, n2-1));
        }
    }

    // Longest Increasing SubSequence
    public int lengthOfLIS(int[] nums) {
        int[][] dp = new int[nums.length+1][nums.length+1];
        for(int[] d : dp) Arrays.fill(d, -1);

        return solve(nums,0, -1, dp);
    }
    public int solve(int[] nums, int i, int prev, int[][] dp) {
        if(i == nums.length) return 0;

        if(dp[i][prev+1] != -1) return dp[i][prev+1];

        int take = Integer.MIN_VALUE;
        if(prev == -1 || nums[i] > nums[prev])
            take =  1 + solve(nums, i+1, i, dp);
        int skip = solve(nums, i+1, prev,dp);

        return dp[i][prev+1] = Math.max(take, skip);
    }


    // Interval DP
    public String longestPalindrome(String s) {
        Boolean[][] dp = new Boolean[s.length()+1][s.length()+1];
        String ans = "";
        for(int i = 0; i < s.length(); i++) {
            for(int j = 0; j < s.length(); j++) {
                if(isPalindrome(s, i, j, dp)) {
                    if(j - i + 1 > ans.length()) {
                        ans = s.substring(i , j+1);
                    }
                }
            }
        }
        return ans;
    }
    public boolean isPalindrome(String s, int left, int right, Boolean[][] dp) {
        if(left >= right) return true;

        if ( dp[left][right] != null ) return dp[left][right];

        if(s.charAt(left) != s.charAt(right)) {
            dp[left][right] = false;
        } else {
            dp[left][right] = isPalindrome(s, left+1, right-1, dp); // interval DP executes here
        }

        return dp[left][right];
    }



    public static void main(String[] chal) {
//        int[] prices = {1 , 2 , 3};
//        int[] weights = {5,2,1};
//        int W = 4;
//        System.out.println(knapsackDP(prices, weights, W, 3));

//        String t1 = "abacabaa";
//        String t2 = "XYhfjsdhfkjdBCDEFXYZIJK";
//
//        System.out.println(isPalindrome(t1, 0, t1.length()-1));
    }
}
