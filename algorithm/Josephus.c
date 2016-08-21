#include <stdio.h>

/* l is the length
 * n is the number out
 * i is the start position
 */
int fun(int l, int n, int i) 
{
	if (i == 1)
		return (l + n - 1) % l;
	else
		return (fun(l - 1, n, i - 1) + n) % l;
}

int main(int argc, char* argv[])
{
	for (int i = 1; i <= 10; i++)
		printf("其实位置%2d，胜出序号%2d\n", i, fun(10, 3, i));
	return 0;
}