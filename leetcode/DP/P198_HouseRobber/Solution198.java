package DP.P198_HouseRobber;

/**
 * @Data Structures:
 * @Algorithms used:  DP
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(n)
 * f[n]= max(f[n-1], f[n-2]+a[n])
 */
public class Solution198 {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        if (nums.length == 1)
            return nums[0];
        if (nums.length == 2)
            return Math.max(nums[0], nums[1]);
        int[] his = new int[nums.length];
        his[0] = nums[0];
        his[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            his[i] = Math.max(nums[i] + his[i - 2], his[i - 1]);
        }
        return his[nums.length - 1];
    }

    public static void main(String[] args) {
        Solution198 sol = new Solution198();
        int[] nums = {2, 7, 9, 3, 1};
        System.out.println(sol.rob(nums));
    }
}
