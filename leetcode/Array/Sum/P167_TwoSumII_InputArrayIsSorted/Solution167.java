package Array.Sum.P167_TwoSumII_InputArrayIsSorted;

/**
 * @Data Structures:
 * @Algorithms used:  TwoPointer
 * @Time Complexity:   O(n)
 * @Space Complexity:  O(1)
 */
public class Solution167 {
    public int[] twoSum(int[] numbers, int target) {
        int lo = 0;
        int hi = numbers.length - 1;
        int sum = 0;
        while ((sum = numbers[lo] + numbers[hi]) != target) {
            if (sum < target)
                lo++;
            if (sum > target)
                hi--;
        }
        return new int[]{lo + 1, hi + 1};
    }

    public static void main(String[] args) {
        Solution167 sol = new Solution167();
        int[] numbers = {2, 7, 11, 15};
        int target = 13;
        for (int elem : sol.twoSum(numbers, target))
            System.out.println(elem);
    }
}
