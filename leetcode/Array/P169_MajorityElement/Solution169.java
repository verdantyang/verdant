package Array.P169_MajorityElement;

/**
 * @Data Structures:
 * @Algorithms used:  Sentinel
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution169 {
    public int majorityElement(int[] nums) {
        int counts = 0;
        int ret = 0;
        for (int i = 0; i < nums.length; i++) {
            if (counts == 0) {
                ret = nums[i];
                counts = 1;
            } else if (nums[i] == ret) {
                counts++;
            } else if (nums[i] != ret) {
                counts--;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        int[] nums = {5, 2, 2, 11};
        Solution169 sol = new Solution169();
        System.out.println(sol.majorityElement(nums));
    }
}
