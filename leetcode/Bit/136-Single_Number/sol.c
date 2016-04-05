/**
 * @Time Complexity:	O(n)
 * @Space Complexity:	O(1)
 */

/* Solution */
int singleNumber(int* nums, int numsSize) {
	int ret = 0;
	for (int i = 0; i < numsSize; i++)
		ret = ret ^ nums[i];
	return ret;
}

/* Test Case */
int main() {
}