package Bit.P136_SingleNumber;


public class Solution136 {
    public static int singleNumber(int[] nums) {
        int ret = 0;
        for (int num : nums) {
            ret ^= num;
        }
        return ret;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 2, 3, 1};
        System.out.println(Solution136.singleNumber(nums));
    }
}
