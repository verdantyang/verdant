package Array.P027_RemoveElement;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution027 {

    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0)
            return 0;
        int counts = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val)
                nums[counts++] = nums[i];
        }
        return counts;
    }

    public static void main(String[] args) {
        Solution027 sol = new Solution027();
        int[] nums = {1, 1, 2};
        System.out.println(sol.removeElement(nums, 1));
    }
}
