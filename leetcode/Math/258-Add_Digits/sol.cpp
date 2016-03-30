/**
 * @Time Complexity:	O(1)
 * @Space Complexity:	O(1)
 */
 
class Solution {
public:
	int addDigits(int num) {
		return 1 + (num - 1) % 9;
	}
};