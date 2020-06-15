package Array.Sum.P015_3Sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Data Structures:
 * @Algorithms used:  TwoPointers
 * @Time Complexity:   O(n^2)
 * @Space Complexity:  O(1)
 */
public class Solution015 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        int lo, hi, sum;
        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                lo = i + 1;
                hi = nums.length - 1;
                sum = 0 - nums[i];
                while (lo < hi) {
                    if (nums[lo] + nums[hi] == sum) {
                        res.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                        while (lo < hi && nums[lo] == nums[lo + 1])
                            lo++;
                        while (lo < hi && nums[hi] == nums[hi - 1])
                            hi--;
                        lo++;
                        hi--;
                    } else if (nums[lo] + nums[hi] < sum) {
                        lo++;
                    } else
                        hi--;
                }
            }
        }
        return res;
    }
}
