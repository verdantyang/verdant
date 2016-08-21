/**
 * @Time Complexity:	O(1)
 * @Space Complexity:	O(1)
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