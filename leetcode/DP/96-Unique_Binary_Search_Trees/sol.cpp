/**
 * @Time Complexity:	O(n)
 * @Space Complexity:	O(1)
 */

/** Recursion Formula
 G(n) = F(1, n) + F(2, n) + ... + F(n, n).
 G(0)=1, G(1)=1.
 F(i, n) = G(i-1) * G(n-i)   1 <= i <= n i is the root
 G(n) = G(0) * G(n-1) + G(1) * G(n-2) + … + G(n-1) * G(0)
*/
#include <stdio.h>
#include <vector>
#include <iostream>
using namespace std;

/* Solution */
class Solution {
public:
	int numTrees(int n) {
		std::vector<int> G(n + 1);
		G[0] = G[1] = 1;
		for (int i = 2; i <= n; ++i) {
			for (int j = 1; j <= i; ++j) {
				G[i] += G[j - 1] * G[i - j];
			}
		}

		return G[n];
	}
};

int main() {
	Solution sol;
	cout << sol.numTrees(6);
	return 0;
}