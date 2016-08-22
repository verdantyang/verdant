package Array.P349_IntersectionOfTwoArrays;

import java.util.HashSet;
import java.util.Set;

/**
 * @Data Structures:   Set
 * @Algorithms used:
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution349 {
    public static int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> cache = new HashSet<>();
        Set<Integer> intersect = new HashSet<>();

        for (int i = 0; i < nums1.length; i++)
            cache.add(nums1[i]);
        for (int i = 0; i < nums2.length; i++)
            if (cache.contains(nums2[i]))
                intersect.add(nums2[i]);
        int[] rets = new int[intersect.size()];
        int cursor = 0;
        for (Integer elem : intersect)
            rets[cursor++] = elem;
        return rets;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 2, 1};
        int[] nums2 = {2, 2};
        for (int elem : Solution349.intersection(nums1, nums2))
            System.out.println(elem);
    }
}
