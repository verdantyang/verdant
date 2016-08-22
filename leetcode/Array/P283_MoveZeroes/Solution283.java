package Array.P283_MoveZeroes;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution283 {
    public static void moveZeroes(int[] nums) {
        int cur = 0;
        for (int i = 0; i < nums.length; i++)
            if (nums[i] != 0)
                nums[cur++] = nums[i];
        for (; cur < nums.length; cur++)
            nums[cur] = 0;
    }

    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 3, 12};
        Solution283.moveZeroes(nums);
        for (int elem : nums)
            System.out.println(elem);
    }
}
