

def ep(s, l, r):
	ls = len(s)
	while l >= 0 and r < ls and s[l] == s[r]:
		l -= 1
		r += 1
	return l + 1, r - 1

def f(st):
	ls = len(st)
	'''
	if ls < 2:
		return st
	'''
	s = 0
	e = 0
	for i in range(ls):
		# 中心就是i和i之间的位置
		s1, e1 = ep(st, i, i)
		# 中心就是i和i + 1之间的位置
		s2, e2 = ep(st, i, i + 1)
		if e1 - s1 > e - s:
			s = s1
			e = e1
		if e2 - s2 > e - s:
			s = s2
			e = e2

	return st[s: e + 1]

if __name__ == '__main__':
	s = 'ab'
	print(f(s))
