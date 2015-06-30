#include <string.h>
#include <stdio.h>
int titleToNumber(char* s) {
    char substr[10];
    int len = strlen(s);
    int c = 0;
    strncpy(substr, s, len-1);
    
    substr[len] = '\0';
	if(len > 1) {
		c = s[len-1]-64;
    	return c + 26*titleToNumber(substr);
	}
	else
		return s[len-1]-64;
}

int main(){
	char in[10] = "AA";
	int res = titleToNumber(in);
	printf("%d\n", res);
}
