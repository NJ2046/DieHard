

class Solution:
    def isUgly(self, num: int) -> bool:
        return False

    def calc_Ugly(self):
        ugly = list()
        bound = 2**31-1
        factors = [2, 3, 5, 6, 10, 15, 30]
        for factor in factors:
            i = 1
            while True:
                if factor**i <= bound:
                    ugly.append(factor**i)
                else:
                    break
                i += 1
        return ugly


if __name__ == '__main__':
    s = Solution()
    print(s.calc_Ugly())
