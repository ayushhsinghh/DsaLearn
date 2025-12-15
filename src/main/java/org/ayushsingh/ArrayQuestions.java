package org.ayushsingh;

import java.util.HashMap;
import java.util.Map;

public class ArrayQuestions {

    // The majority element is the element that appears more than ⌊n / 2⌋ times.
    // Choose a candidate and counter,
    // everytime we encounter a new candidate, decrease the counter else increase
    // If the counter is zero, change the candidate.
    public int majorityElement(int[] nums) {
        int candidate = nums[0];
        int count = 0;

        for(int num: nums) {
            if(candidate != num) {
                count--;
                if(count == 0) {
                    candidate = num;
                    count++;
                }
            } else {
                count++;
            }
        }
        return candidate;
    }

    // Given an integer array nums sorted in non-decreasing order, remove some duplicates in-place such that each unique element appears at most k times.
    // The relative order of the elements should be kept the same.
    public int removeDuplicates(int[] nums, int k) {
        int p = 0; // write pointer

        for (int num : nums) {
            // Accept if less than 2 elements written
            // OR current num is not the same as the element two places back
            if (p < k || num != nums[p - k]) {
                nums[p] = num;
                p++;
            }
        }

        return p;
    }

    // H-Index Problem
    // Counting Sort Approach
    // The algorithm uses counting sort ideas instead of full sorting:
    //1. Count frequencies of citation values (like counting sort’s histogram).
    //2. Accumulate counts from high to low to determine the cutoff where papers ≥ i.
    //3. Avoid O(n log n) sorting entirely — achieve O(n) time.
    //So it’s a counting-based distribution method, not a sorting step — but the same principle of using frequency counts instead of comparisons applies.
    public int hIndex(int[] citations) {
            int papers = citations.length + 1;
            int counter = 0;
            int[] paper_count = new int[papers]; //default Value is Zero

            for(int num: citations) {
                if(num >= papers) paper_count[papers-1]++;
                else paper_count[num]++;
            }

            for(int i = papers - 1; i>=0 ; i--) {
                counter = counter + paper_count[i];
                if(counter >= i) return i;
            }

            return 0;
        }

    // Product of Array Except Self
    // PrefixSum Question
    //
    // Forward pass
    // ans[i] holds product of all numbers to the left of i.
    // Backward pass
    // Multiply each ans[i] by cumulative product of numbers to the right of i.
    public int[] productExceptSelf(int[] nums) {
        int[] ans = new int[nums.length]; //default to zero;
        ans[0] = 1;
        // Step 1: store prefix products
        for(int i = 1; i < nums.length ; i++) {
            ans[i] = ans[i-1] * nums[i-1];
        }
        // Step 2: multiply by suffix products
        int suff = 1;
        for(int i = nums.length - 1; i >=0 ; i--) {
            ans[i] = ans[i] * suff;
            suff *= nums[i];
        }
        return ans;
    }

    //Gas Station Problem (IMP) (Unique Greedy Solution)
    // 1. check if total gas available < total cost.
    // 2. Reset when Sum becomes negative, WHY
    //    No station between your start and this failure point could be a valid start.
    //    Because they’d have even less fuel accumulated when reaching here.
    class Solution {
        public int canCompleteCircuit(int[] gas, int[] cost) {
            int totalTank = 0;
            int currTank = 0;
            int start = 0;

            for (int i = 0; i < gas.length; i++) {
                int diff = gas[i] - cost[i];
                totalTank += diff;
                currTank += diff;

                // if we run out of gas here, start from next station
                if (currTank < 0) {
                    start = i + 1;
                    currTank = 0;
                }
            }

            // check if total gas >= total cost
            return totalTank >= 0 ? start : -1;
        }
    }

    // Count SubArray Sum equals to K
    // Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.
    // Why This solution Works
    // Subarray_sum = difference of two prefix sums (k, Prefix-k)
    // If prefix[r] - k has occurred before,
    // each occurrence represents one valid subarray ending at r
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int pSum = 0, ans = 0;

        for (int num : nums) {
            pSum += num;
            ans += map.getOrDefault(pSum - k, 0); // Get All occurrence of (pSum-k)
            map.put(pSum, map.getOrDefault(pSum, 0) + 1);
        }
        return ans;
    }

    // Question: Find the Duplicate Number
    // Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
    // There is only one repeated number in nums, return this repeated number.
    // You must solve the problem without modifying the array nums and using only constant extra space.
    // Use Flyods Cycle Detection Algorithm.(Fast and Slow pointer)
    public int findDuplicate(int[] nums) {
        int slow = 0 , fast = 0;

        while (true) {
            slow = nums[slow];
            fast = nums[nums[fast]];

            if(slow == fast)
                break;
        }

        fast = 0;
        while (true) {
            slow = nums[slow];
            fast = nums[fast];

            if(slow == fast) break;
        }
        return slow;
    }

}
