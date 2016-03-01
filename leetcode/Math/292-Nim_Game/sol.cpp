/**
 * @Time Complexity: O(n)
 * @Space Complexity: O(2n)
 */

class Solution {
public:
	bool canWinNim(int n) {
		if (0 == n % 4)
			return false;
		else
			return true;
	}
};

int main() {

}