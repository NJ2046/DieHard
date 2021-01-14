

def process(s):
    sl = len(s)
    m = 0
    win = dict()
    i = 0
    while i < sl:
        if s[i] in win:
            if m < len(win):
                m = len(win)
            i = win[s[i]] + 1
            win = dict()
            continue
        else:
            win[s[i]] = i
            if m < len(win):
                m = len(win)
        i += 1
    return m


if __name__ =='__main__':
    s = 'wwkew' * 10**4
    #s = 'abcabcbb'
    c = process(s)
    print(c)
