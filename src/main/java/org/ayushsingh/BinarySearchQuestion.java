package org.ayushsingh;

public class BinarySearchQuestion {

    // Basic Binary Search
    public int binarySearch(int[] arr, int s) {
        int low = 0,  high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == s) return mid;
            else if (arr[mid] < arr[high]) low = mid + 1;
            else high = mid - 1;
        }

        return -1;
    }


    // Boundary search (insertion point)
    //If arr[mid] < target, the insert position must be after mid, so low = mid + 1
    //Otherwise, target should go before or at mid, so high = mid
    //Loop stops when low == high → insertion index
    public int insertPosition(int[] arr, int target) {
        int low = 0, high = arr.length;  // notice high = n (exclusive upper bound)
        while (low < high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;  // move left, including mid
            }
        }
        return low;  // low == high => insertion index
    }

    // Find Peak Element, (Non-Sorted Array)
    // Given an array nums, return the index of any one peak element.
    // Compare nums[mid] and nums[mid + 1] todetect slope direction:
    //If nums[mid] < nums[mid + 1] → we are increasing, so the peak lies on the right → move low = mid + 1
    //Else → we are decreasing (or at peak), so the peak lies on the left or at mid → move high = mid
    // We’re effectively doing a binary search on the slope.
    // Note:
    // Why while (low < high) (and not <=)
    //Because
    //You’re not looking for an exact value but for a boundary — the transition from increasing to decreasing.
    //Once low == high, you’ve narrowed it down to the peak position.
    //
    //If you used <=, you’d risk accessing nums[mid + 1] out of bounds or looping infinitely.
    public int findPeakElement(int[] nums) {
        int low = 0, high = nums.length - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < nums[mid + 1]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
    // Follow Up, how to find all Peaks in a arrays
    // Ans: It can be done using linear scan of array.

    // Search in a Rotated Array by K index(K is unknown)
    //
    // One half of the array ([low..mid] or [mid..high]) must be sorted.
    // Check if the target lies inside that sorted half; if not, discard that side;
    public int search(int[] nums, int target) {
        int low = 0, high = nums.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) return mid;

            if (nums[low] == nums[mid] && nums[mid] == nums[high]) { // Shrink The array if both low and high are equal ( To handle duplicates)
                low++; high--;
            } else if (nums[mid] <= nums[high]) { // Right half is sorted
                if (target > nums[mid] && target <= nums[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            else { // Left half is sorted
                if (target >= nums[low] && target < nums[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
        }

        return -1;
    }

    // Find First /Last Occurrence of an Element in a sorted Array.
    public int findFirst(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        int first = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                first = mid;
                high = mid - 1; // InCase-of Last Occurrence, make it: "low = mid + 1"
            } else if (nums[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return first;
    }
    // This question can be used to find the count of an element in sorted array.
    // Just find the first and last occurrence of an element, then ( lastIndex - firstIndex + 1) for count;

    // Find Minimum in Rotated Sorted Array
    public int findMin(int[] nums) {
        int low = 0, high = nums.length - 1;

        while(low < high) {
            int mid = low + (high - low)/2;

            if(nums[mid] <= nums[high]) {
                high = mid;   // If the element at mid is less than or equal to the element at right, this means the minimum element could be at mid or to its left (in the left half of the current subarray).
            } else {
                low = mid + 1;
            }
        }
        return nums[high];
    }




    // Notes:
    // Questions: Why to use < or <= in Binary Search
    // Ans: If you are moving your pointers by mid + 1 or mid - 1 idx then you must use left <= right
    //      Otherwise, you must use left < right if you are moving your pointers by mid idx only.

}
