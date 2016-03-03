/**
 * @Time Complexity:	O(nlog(n))
 * @Space Complexity:	O(nlog(n))
 */

#include <iostream>
#include <stdio.h>

using namespace std;

class Solution {
public:
	static int partition(int unsorted[], int low, int high) {
		int pivot = unsorted[low];
		while (low < high) {
			if (unsorted[high] > pivot)
				high--;
			unsorted[low] = unsorted[high];

			if (unsorted[low] < pivot)
				low++;
			unsorted[high] = unsorted[low];
		}
		unsorted[low] = pivot;
		return low;
	}
	static void quickSort(int unsorted[], int low, int high) {
		int loc = 0;
		if (low < high) {
			loc = partition(unsorted, low, high);
			quickSort(unsorted, low, loc);
			quickSort(unsorted, loc + 1, high);
		}

	}
};

int main() {
	int nums[] = {1, 4, 5, 6, 2, 7, 9, 3};
	Solution sol;
	sol.quickSort(nums, 0, 7);
	for (int i = 0; i < sizeof(nums) / sizeof(nums[0]); i++)
		cout << nums[i] << " ";
	return 0;
}