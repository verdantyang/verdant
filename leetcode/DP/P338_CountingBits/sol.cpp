/**
 * @Time Complexity:	O(n)
 * @Space Complexity:	O(n)
 */

#include <stdio.h>
#include <vector>
#include <iostream>
using namespace std;

/** Recursion Formula
 G(n) = G[n&(n-1)] + 1
*/
class Solution {
public:
	vector<int> countBits(int num) {
		vector<int> rets(num + 1);
		rets[0] = 0;
		for (int i = 1; i <= num; i++)
			rets[i] = rets[i & (i - 1)] + 1;
		return rets;
	}
};

int main() {
	Solution sol;
	vector<int> rets = sol.countBits(6);
	vector<int>::iterator iter;
	for (iter = rets.begin(); iter != rets.end(); iter++)
	{
		cout << *iter << ' ';
	}
	return 0;
}