package org.ayushsingh;

public class ArrayQuestions {

    // The majority element is the element that appears more than ⌊n / 2⌋ times.
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

}
