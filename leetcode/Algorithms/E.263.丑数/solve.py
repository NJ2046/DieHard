import itertools
import numpy


class Solution:
    def isUgly(self, num: int) -> bool:
        init = [2, 3, 5]
        ugly = [1, 2, 3, 5]
        i = 2
        while i <= 30:
            comb = itertools.combinations_with_replacement(init, i)
            for c in comb:
                u = numpy.prod(c)
                if u > 0:
                    ugly.append(numpy.prod(c))
            i += 1
        if num in ugly:
            return True
        return False


if __name__ == '__main__':
    s = Solution()
    print(s.isUgly(30))

