package Array.Sum.P016_3SumClosest;

import java.util.Arrays;

/**
 * @Data Structures:
 * @Algorithms used:
 * @Time Complexity:   O(n^2)
 * @Space Complexity:  O(1)
 */
public class Solution016 {
    public int threeSumClosest(int[] nums, int target) {
        int closest = nums[0] + nums[1] + nums[nums.length - 1];
        Arrays.sort(nums);
        int diffPos = Integer.MAX_VALUE;
        int diffNeg = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length - 2; i++) {
            int lo = i  + 1;
            int hi = nums.length - 1;
            while (lo < hi) {
                while (lo < hi && (nums[i] + nums[lo] + nums[hi]) >= target) {
                    diffPos = diffPos > nums[i] + nums[lo] + nums[hi] - target ?
                            nums[i] + nums[lo] + nums[hi] - target : diffPos;
                    if (diffPos == 0) return target;
                    hi--;
                }
                while (lo < hi && (nums[i] + nums[lo] + nums[hi]) <= target) {
                    diffNeg = diffNeg > target - (nums[i] + nums[lo] + nums[hi]) ?
                            target - (nums[i] + nums[lo] + nums[hi]) : diffNeg;
                    if (diffNeg == 0) return target;
                    lo++;
                }
            }
            closest = diffPos > diffNeg ? target - diffNeg : target + diffPos;
        }
        return closest;
    }

    public static void main(String[] args) {
        Solution016 sol = new Solution016();
        int[] numbers = {-1, 2, 1, -4};
        int target = 1;
        System.out.println(sol.threeSumClosest(numbers, target));
    }
}
