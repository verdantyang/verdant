/**
 * @Time Complexity:	O(1)
 * @Space Complexity:	O(1)
 */
#include <iostream>
using namespace std;

/* Solution */
class Solution {
public:
	int getSum(int a, int b) {
		while (b) {
			int x = a ^ b;
			int y = (a & b) << 1;
			cout << x <<"  "<< y << endl;
			a = x;
			b = y;
		}
		return a;
	}
};

/* Test Case */
int main() {
	Solution sol;
	int ret = sol.getSum(6, 7);
	cout << ret;
}