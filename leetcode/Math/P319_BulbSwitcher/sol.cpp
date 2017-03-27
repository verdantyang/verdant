/**
 * @Time Complexity:	O(1)
 * @Space Complexity:	O(1)
 */

class Solution {
public:
	int bulbSwitch(int n) {
		int counts = 0;

		for (int i = 1; i * i <= n; ++i) {
			++ counts;
		}

		return counts;
	}
};

int main() {

}