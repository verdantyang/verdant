package Bit.P260_SingleNumberIII;

/**
 * @Data Structures:
 * @Algorithms used:  xor
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution260 {
    public static int[] singleNumber(int[] nums) {
        int xor = 0;
        int a = 0, b = 0;
        for (int i = 0; i < nums.length; i++)
            xor ^= nums[i];
        int diff = xor & (-xor);
        for (int i = 0; i < nums.length; i++) {
            if ((nums[i] & diff) == 0)
                a ^= nums[i];
            else
                b ^= nums[i];
        }
        return new int[]{a, b};
    }

    public static void main(String[] args) {
        int[] nums = {2, 1, 2, 3, 4, 1};
        for (int elem : Solution260.singleNumber(nums))
            System.out.println(elem);
    }
}

