package Bit.P268_MissingNumber;

/**
 * @Data Structures:
 * @Algorithms used:  xor
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution268 {
    public static int missingNumber(int[] nums) {
        int len = nums.length;
        int ret = len;

        for (int i = 0; i < len; i++)
            ret = ret ^ i ^ nums[i];
        return ret;
    }

    public static void main(String[] args) {
        int[] nums = {0, 1, 3};
        System.out.println(Solution268.missingNumber(nums));
    }
}

