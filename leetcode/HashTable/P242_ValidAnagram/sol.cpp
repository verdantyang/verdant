/**
 * @Time Complexity:	O(n)
 * @Space Complexity:	O(1)
 */
#include <cstdio>
#include <iostream>
using namespace std;

class Solution {
public:
	bool isAnagram(string s, string t) {
		if (s.length() != t.length())
			return false;
		int len = s.length();
		int counts[26] = {0};
		for (int i = 0; i < len; i++) {
			counts[s[i] - 'a']++;
			counts[t[i] - 'a']--;
		}
		for (int i = 0; i < 26; i++)
			if (counts[i])
				return false;
		return true;
	}
};

int main() {
	Solution sol;
	cout << sol.isAnagram("abc", "cba") << endl;
	cout << sol.isAnagram("abe", "cba") << endl;
	return 0;
}