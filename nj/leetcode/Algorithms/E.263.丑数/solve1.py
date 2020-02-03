import itertools
import numpy


class Solution:
    def isUgly(self, num: int) -> bool:
        if num <= 0:
            return False
        a = self.sub(float(num), 2)
        if a == 1:
            return True
        # print(a)
        b = self.sub(a, 3)
        if b == 1:
            return True
        # print(b)
        c = self.sub(b, 5)
        if c == 1:
            return True
        return False

    def sub(self, num, target):
        while num.is_integer() and num != 1:
            num = num / target

        if num == 1:
            return num
        return num * target


if __name__ == '__main__':
    s = Solution()
    print(s.isUgly(161243133))
