package Array.P347_TopKFrequentElements;

import java.util.*;

/**
 * @Data Structures:
 * @Algorithms used:  HashMap
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution347 {
    public List<Integer> topKFrequent(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();
        LinkedHashMap<Integer, List<Integer>> cache = new LinkedHashMap<>();
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int elem : nums) {
            if (freqMap.get(elem) == null)
                freqMap.put(elem, 0);
            freqMap.put(elem, freqMap.get(elem) + 1);
        }
        for (Integer elem : freqMap.keySet()) {
            if (cache.get(freqMap.get(elem)) == null) {
                cache.put(freqMap.get(elem), new ArrayList<Integer>());
            }
            cache.get(freqMap.get(elem)).add(elem);
        }
        for (int i = nums.length; i >= 0 && result.size() < k; i--) {
            if (cache.get(i) != null)
                result.addAll(cache.get(i));
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {5, 2, 2, 11};
        Solution347 sol = new Solution347();
        for (Integer elem : sol.topKFrequent(nums, 2))
            System.out.println(elem);
    }
}
