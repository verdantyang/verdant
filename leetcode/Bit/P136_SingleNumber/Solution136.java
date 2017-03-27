package Bit.P136_SingleNumber;

/**
 * @Data Structures:
 * @Algorithms used:  xor
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution136 {
    public int singleNumber(int[] nums) {
        int ret = 0;
        for (int num : nums) {
            ret ^= num;
        }
        return ret;
    }

    public static void main(String[] args) {
        Solution136 sol = new Solution136();
        int[] nums = {1, 2, 2, 3, 1};
        System.out.println(sol.singleNumber(nums));
    }
}
