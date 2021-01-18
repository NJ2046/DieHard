class Solution:
	def isp(self, s: str) -> bool:
		ls = len(s)
		mid = int(ls / 2)
		if ls % 2 == 0:
			if s[:mid] == s[mid:][::-1]:
				return True
		else:
			if s[:mid + 1] == s[mid:][::-1]:
				return True
		return False

	def longestPalindrome1(self, s: list) -> str:
		ls = len(s)
		if ls == 0:
			return ''
		if ls == 1:
			return s
		for i in range(ls):
			if self.isp(s[i:]):
				return s[i:]
			if self.isp(s[:ls - i]):
				return s[:ls - i]

	def longestPalindrome(self, s: list) -> str:
		ls = len(s)
		if ls == 0:
			return ''
		#if ls == 1:
			#return s
		r = str()
		for i in range(ls):
			for j in range(i + 1, ls + 1):
				if self.isp(s[i:j]):
					if len(r) < len(s[i:j]):
						r = s[i:j]
		return r


if __name__ == '__main__':
	s = 'a'
	e = Solution()
	k = e.longestPalindrome(s)
	print(k)

