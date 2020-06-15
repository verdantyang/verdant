package Array.Sum.P053_MaximumSubarray;

/**
 * @Data Structures:
 * @Algorithms used:  Sentinel
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution053 {
    public int maxSubArray(int[] nums) {
        if (nums == null)
            return 0;
        int cursor = nums[0];
        int maximum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (cursor > 0) {
                cursor += nums[i];
            } else {
                cursor = nums[i];
            }
            maximum = maximum > cursor ? maximum : cursor;
        }
        return maximum;
    }

    public static void main(String[] args) {
        int[] nums = {-2, -1};
        Solution053 sol = new Solution053();
        System.out.println(sol.maxSubArray(nums));
    }
}
