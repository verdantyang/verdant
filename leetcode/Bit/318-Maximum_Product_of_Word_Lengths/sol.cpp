/**
 * @Time Complexity:    O(n^2)
 * @Space Complexity:   O(n)
 */

/** Tips
* alpha[26]
*/
#include <vector>
#include <unordered_map>
#include <iostream>
using namespace std;

/* Solution */
class Solution {
public:
    int maxProduct(vector<string>& words) {
        unordered_map<int, int> maxlen;
        int result = 0;
        for (string word : words) {
            int mask = 0;
            for (char c : word)
                mask |= 1 << (c - 'a');
            maxlen[mask] = max(maxlen[mask], (int) word.size());
            for (auto maskAndLen : maxlen)
                if (!(mask & maskAndLen.first))
                    result = max(result, (int) word.size() * maskAndLen.second);
        }
        return result;
    }
};

/* Test Case */
int main() {
}