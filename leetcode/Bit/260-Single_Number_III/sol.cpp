/**
 * @Time Complexity:	O(2n)
 * @Space Complexity:	O(1)
 */
#include <vector>
#include <iostream>
using namespace std;

class Solution {
public:
	vector<int> singleNumber(vector<int>& nums) {
		// Pass 1 :
		int diff = 0;
		for (int i = 0; i < nums.size(); i++)
			diff = diff ^ nums[i];
		// int diff = accumulate(nums.begin(), nums.end(), 0, bit_xor<int>());
		// Get its last set bit
		diff &= -diff;

		// Pass 2 :
		vector<int> rets(2, 0);
		// vector<int> rets = {0, 0};
		for (int i = 0; i < nums.size(); i++)
		{
			if ((nums[i] & diff) == 0)
			{
				rets[0] ^= nums[i];
			}
			else
			{
				rets[1] ^= nums[i];
			}
		}
		return rets;
	}
};

/* Test Case */
int main() {
}