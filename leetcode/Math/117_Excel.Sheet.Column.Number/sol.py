class Solution:
    # @param {string} s
    # @return {integer}
    def titleToNumber(self, s):
    	l = len(s);
        return  (ord(s[0])-64) if (l==1) else (ord(s[l-1])-64) + 26*self.titleToNumber(s[:-1]);

# Test Case
if __name__=="__main__":
    ori = "ABC";
    cla = Solution()
    print cla.titleToNumber(ori)
