/**
 * @Time Complexity:	O(n)
 * @Space Complexity:	O(1)
 */
#include <vector>
#include <iostream>
using namespace std;

/* Solution */
class Solution {
public:
	int missingNumber(vector<int>& nums) {
		int len = nums.size();
		int ret = len;
		
		for (int i = 0; i < len; i++) 
			ret = ret ^ i ^ nums[i];
		return ret;
	}
};

/* Test Case */
int main() {
	Solution sol;
	int a[] = {0, 2, 3};
	vector<int> ori(a, a + sizeof(a) / sizeof(a[0]));
	int result = sol.missingNumber(ori);
	cout << result;
	return 0;
}