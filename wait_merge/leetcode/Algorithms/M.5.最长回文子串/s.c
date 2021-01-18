#include<stdio.h>
#include <string.h>
char k[2000];
int isp(int min, int max, char * s){
	while(min < max){
		if(s[min] != s[max])
			return 0;
		min++;
		max--;
	}
	return 1;
}
char * longestPalindrome(char * s){
	int ls = strlen(s);
	if(ls < 2){
		return s;
	}
	int m = 1;
	int b = 0;
	for(int i = 0; i < ls - 1; i++){
		for (int j = i + 1; j < ls; j++){
			if(isp(i, j, s) == 1){
				if(m < (j - i + 1)){
					m = j - i + 1;
					b = i;
				}
			}
		}
	}
	printf("%d, %d\n", b, m);
	for(int i = 0; i < m; i ++){
		k[i] = s[b];
		b++;
	}
	k[m] = '\0';
	return k;
}

int main(){
	//char k[] = "helloworld";
	char k[] = "ac";
	//char k[] = "babad";
	//char k[] = "c";
	char *r = longestPalindrome(k);
	printf("%s\n", r);
	return 0;
}
