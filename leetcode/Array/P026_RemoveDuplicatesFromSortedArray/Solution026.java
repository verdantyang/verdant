package Array.P026_RemoveDuplicatesFromSortedArray;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution026 {
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length < 1)
            return 0;
        int counts = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1])
                nums[counts++] = nums[i];
        }
        return counts;
    }

    public static void main(String[] args) {
        Solution026 sol = new Solution026();
        int[] nums = {1};
        System.out.println(sol.removeDuplicates(nums));
    }
}
