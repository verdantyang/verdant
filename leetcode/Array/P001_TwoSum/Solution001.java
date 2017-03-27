package Array.P001_TwoSum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Data Structures:   Map
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(n)
 */
public class Solution001 {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> findMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (findMap.containsKey(nums[i]))
                return new int[]{findMap.get(nums[i]), i};
            else
                findMap.put(target - nums[i], i);
        }
        return new int[]{0, 0};
    }

    public static void main(String[] args) {
        Solution001 sol = new Solution001();
        int[] numbers = {2, 7, 15, 11};
        int target = 13;
        for (int elem : sol.twoSum(numbers, target))
            System.out.println(elem);
    }
}
