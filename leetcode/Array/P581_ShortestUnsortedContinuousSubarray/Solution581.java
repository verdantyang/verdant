package Array.P581_ShortestUnsortedContinuousSubarray;

/**
 * @Data Structures:   Set
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution581 {
    public int findUnsortedSubarray(int[] nums) {
        int begin = 0, end = -1;
        int max = nums[0];
        int min = nums[nums.length - 1];
        //保证begin之前、end之后都是有序的
        for (int i = 1; i < nums.length; i++) {
            max = nums[i] > max ? nums[i] : max;
            min = nums[nums.length - 1 - i] < min ? nums[nums.length - 1 - i] : min;
            if (nums[i] < max)
                end = i;
            if (nums[nums.length - 1 - i] > min)
                begin = nums.length - 1 - i;
        }
        return end - begin + 1;
    }

    public static void main(String[] args) {
        Solution581 sol = new Solution581();
        int[] nums1 = {2, 6, 4, 8, 10, 9, 15};
        System.out.println(sol.findUnsortedSubarray(nums1));
    }
}
