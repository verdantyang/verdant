/**
 * @Time Complexity:	O(n)
 * @Space Complexity:	O(1)
 */

#include <stdio.h>
#include <vector>
#include <iostream>
using namespace std;

/* Solution */
class Solution {
public:
	vector<int> countBits(int num) {
		vector<int> res(num + 1);
		res[0] = 0;
		for (int i = 1; i <= num; i++)
			res[i] = res[i & (i - 1)] + 1;
		return res;
	}
};

int main() {
	Solution sol;
	vector<int> res = sol.countBits(6);
	vector<int>::iterator iter;
	for (iter = res.begin(); iter != res.end(); iter++)
	{
		cout << *iter << ' ';
	}
	return 0;
}