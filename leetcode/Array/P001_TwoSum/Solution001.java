package Array.P001_TwoSum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Data Structures:    hashmap
 * @Algorithms:
 *
 * @Time Complexity:    O(n^2)
 * @Space Complexity:   O(1)
 */
public class Solution001 {
    public static int[] twoSum(int[] nums, int target) {
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
        int[] numbers = {2, 7, 15, 11};
        int target = 13;
        for (int elem : Solution001.twoSum(numbers, target))
            System.out.println(elem);
    }
}
