/**
 * @Time Complexity:    O(n)
 * @Space Complexity:   O(1)
 */
#include <vector>
#include <iostream>
using namespace std;

/* Solution */
class Solution {
public:
    string reverseString(string s) {
        int len = s.length();

        for (int i = 0; i < len / 2; i++)
        {
            char temp = s[i];
            s[i] = s[len - i - 1];
            s[len - i - 1] = temp;
        }
        return s;
    }
};

/* Test Case */
int main() {
    Solution sol;
    string str = "abc";
    string ret = sol.reverseString(str);
    cout << ret;
}
