package Array.P035_SearchInsertPosition;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution035 {

    public int searchInsert(int[] nums, int target) {
        if (nums == null)
            return 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] >= target)
                return i - 1;
            if (nums[i - 1] < target && nums[i] > target)
                return i;
        }
        return target > nums[nums.length - 1] ? nums.length : nums.length - 1;
    }

    public static void main(String[] args) {
        Solution035 sol = new Solution035();
        int[] nums = {1, 3, 5, 6};
        System.out.println(sol.searchInsert(nums, 5));
    }
}
