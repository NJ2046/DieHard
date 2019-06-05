import itertools


class Solution:
    def combine(self, n: int, k: int):
        return list(itertools.combinations(range(1, n + 1), k))


if __name__ == '__main__':
    t = Solution()
    print(t.combine(4, 2))
