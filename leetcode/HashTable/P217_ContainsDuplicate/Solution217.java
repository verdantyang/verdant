package HashTable.P217_ContainsDuplicate;

import java.util.HashSet;
import java.util.Set;

/**
 * @Data Structures:   HashSet
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution217 {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> hashSet = new HashSet<>();
        for (int elem : nums) {
            hashSet.add(elem);
        }
        return nums.length > hashSet.size();
    }

    public static void main(String[] args) {
        Solution217 sol = new Solution217();
        System.out.println(sol.containsDuplicate(new int[]{0, 1, 2, 1, 3}));
    }
}
