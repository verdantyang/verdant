package Array.P238_ProductOfArrayExceptSelf;

/**
 * @Data Structures:
 * @Algorithms used:  TwoPointer
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution238 {
    public static int[] productExceptSelf(int[] nums) {
        int size = nums.length;
        int[] rets = new int[size];
        for (int i = 0; i < size; i++)
            rets[i] = 1;
        int fromBegin = 1;
        int fromEnd = 1;
        for (int i = 0; i < size; i++) {
            rets[i] *= fromBegin;
            fromBegin *= nums[i];
            rets[size - i - 1] *= fromEnd;
            fromEnd *= nums[size - i - 1];
        }
        return rets;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4};
        for (int elem : Solution238.productExceptSelf(nums))
            System.out.println(elem);
    }
}
