/**
 * @Time Complexity:	O(n+m)
 * @Space Complexity:	O(m)
 */
#include <iostream>
#include <vector>
#include <string>
using namespace std;

class Solution {
public:
	vector<int> getNext(string str)
	{
		int len = str.size();
		int j = 0;

		vector<int> next(len + 1);
		next[0] = next[1] = 0;

		for (int i = 1; i < len; i++)
		{
			while (j > 0 && str[i] != str[j])  //回溯
				j = next[j];
			if (str[i] == str[j])
				j++;
			next[i + 1] = j;
		}

		return next;
	}

	int indexKmp(string& source, string& target, int pos = 0) {
		int j = 0;  //子串下标
		vector<int> next = getNext(target);
		for (int i = pos; i < source.size(); i++) {
			while (j > 0 && source[i] != target[j])  //回溯
				j = next[j];
			if (source[i] == target[j])
				j++;
			if (j == target.size()) {
				return i + 1 - target.size();
			}
		}
		return 0;
	}
};

int main() {
	Solution sol;
	string source = "abcabda";
	string target = "cab";
	int result = sol.indexKmp(source, target);
	cout << result << endl;
	return 0;
}