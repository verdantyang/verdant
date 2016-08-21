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
    bool canConstruct(string ransomNote, string magazine) {
        int arr[26] = {0};
        for (int i = 0; i < magazine.length(); i++) {
            arr[magazine[i] - 'a']++;
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            if (--arr[ransomNote[i] - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }
};

/* Test Case */
int main() {
    Solution sol;
    string str1 = "a";
    string str2 = "b";
    bool ret = sol.canConstruct(str1, str2);
    cout << ret;
}
