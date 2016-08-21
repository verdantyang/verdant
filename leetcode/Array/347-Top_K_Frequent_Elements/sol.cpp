/**
 * @Time Complexity:    O(n)
 * @Space Complexity:   O(1)
 */
#include <vector>
#include <iostream>
#include <unordered_map>
#include <queue>
using namespace std;

/* Solution */
class Solution {
public:
    vector<int> topKFrequent(vector<int>& nums, int k) {
        unordered_map<int, int> map;
        for (int num : nums) {
            map[num]++;
        }

        vector<int> ret;
        // pair<first, second>: first is frequency,  second is number
        priority_queue<pair<int, int>> pq;
        for (auto it = map.begin(); it != map.end(); it++) {
            pq.push(make_pair(it->second, it->first));
        }
        while (k-- > 0) {
            ret.push_back(pq.top().second);
            pq.pop();
        }
        return ret;
    }
};

/* Test Case */
int main() {
    Solution sol;
    int a[] = {1, 1, 3, 1, 2, 1};
    vector<int> ori(a, a + sizeof(a) / sizeof(a[0]));
    vector<int> ret = sol.topKFrequent(ori, 2);
    vector<int>::iterator iter;
    for (iter = ret.begin(); iter != ret.end(); iter++)
    {
        cout << *iter << ' ';
    }
    return 0;
}