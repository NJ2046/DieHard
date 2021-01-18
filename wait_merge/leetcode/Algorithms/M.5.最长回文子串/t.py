

def longestPalindrome(s):
    smax = len(s) - 1
    smin = 0
    r = list()
    f = 0
	while smin < smax:
		pass
		smin += 1
    while smin < smax:
        if s[smin] == s[smax]:
            if f == 0:
                r.append((smin, smax))
            else:
                f = 1
                r = list()
            smin += 1
            smax -= 1
            continue
        smin += 1
        f = 1

    smax = len(s) - 1
    smin = 0
    rm = list()
    f = 0
    while smin < smax:
        if s[smin] == s[smax]:
            if f == 0:
                rm.append((smin, smax))
            else:
                f = 1
                rm = list()
            smin += 1
            smax -= 1
            continue
        smax -= 1
        f = 1
    print(r)
    print(rm)


if __name__ == '__main__':
    s = 'babba'
    print(s)
    k = longestPalindrome(s)
    print(k)
