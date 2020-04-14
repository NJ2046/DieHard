import pandas as pd
from itertools import combinations
import math
import itertools as it
import sys
sys.setrecursionlimit(10000000)


# 帮助朋友处理一些EXCEL的事情

class Solution0:
    def combinationSum(self, candidates: float, target: float) -> list:
        if target == 0: return [[]]
        if target < 0: return []
        res = []
        for num in candidates:
            sub_target = target - num
            sub_res = self.combinationSum(candidates, sub_target)
            res += [[num]+comb for comb in sub_res]
        return res


class Solution1(object):
    def combinationSum(self, candidates, target):
        """
        :type candidates: List[int]
        :type target: int
        :rtype: List[List[int]]
        """
        candidates = sorted(set(candidates))
        result = list()
        stack = [(0, list(), target)]
        cand_len = len(candidates)

        while stack:
            i, path, remain = stack.pop()
            while i < cand_len:
                if path and remain < path[-1]:
                    break
                if candidates[i] == remain:
                    result.append(path + [candidates[i]])
                stack += [(i, path + [candidates[i]],  remain - candidates[i])]
                i+=1

        return result


class Solution2(object):
    def combinationSum(self, candidates, target):
        def dfs(candidates, target, s, curr, ans):
            if target == 0:
                ans.append(curr[:])
                return

            for i in range(s, len(candidates)):
                if candidates[i] > target: return
                curr.append(candidates[i])
                dfs(candidates, target - candidates[i], i, curr, ans)
                curr.pop()

        ans = []
        candidates.sort()
        dfs(candidates, target, 0, [], ans);

        return ans


class Solution3:
    def combinationSum(self, candidates, target):
        res = []
        candidates.sort()
        self.dfs(candidates, target, 0, [], res)
        # print(res)
        return res


    def dfs(self, nums, target, index, path, res):
        # print(nums, target, index, path, res)
        for i in range(index, len(nums)):
            remain = target - nums[i]
            if remain < 0:
                break
            if remain == 0:
                res.append(path+ [nums[i]])
                break
            self.dfs(nums, remain, i, path + [nums[i]], res)
            # print('i=%d' %i)
            # print('===')


class Solution:
    # @param candidates, a list of integers
    # @param target, integer
    # @return a list of lists of integers
    def DFS(self, candidates, target, start, valuelist):
        length = len(candidates)
        if target == 0 and valuelist not in Solution.ret: return Solution.ret.append(valuelist)
        for i in range(start, length):
            if target < candidates[i]:
                return
            self.DFS(candidates, target - candidates[i], i + 1, valuelist + [candidates[i]])

    def combinationSum(self, candidates, target):
        candidates.sort()
        Solution.ret = []
        self.DFS(candidates, target, 0, [])
        return Solution.ret


def stack(lis, target_sum):
    tolerance = 150
    # target_sum = 8392
    found = False
    # lis = [497.96, 10, 5084, 156.43, 381.3, 3298.85, 625.68]

    for n in range(1, len(lis) + 1):
        print(n)
        numbers = combinations(lis, n)
        for combi in numbers:
            print('组合' + str(combi))
            print('求和' + str(sum(combi)))
            if target == sum(combi):
                print(combi)


if __name__ == '__main__':
    path = './1月设备折旧.xls'
    sheet = 'Sheet1'
    equipment_data = pd.read_excel(path, sheet_name=sheet)
    # equipment_data.info()
    # print(equipment_data.describe())
    lis = list(equipment_data['本月计提折旧额'])
    lis = [int(line) for line in lis]
    target = int(785785.55)
    lis = list(range(1, 10000))
    target = 80
    # print(lis)
    # print(target)
    # stack(lis, target)
    print('开始')
    result = Solution().combinationSum(lis, target)
    print('组合结果')
    print(result)

    '''
    # result = Solution().combinationSum([2,3,5,7], 7)
    result = Solution0().combinationSum(equ, target)
    print(result)
    # set the number of closest combinations to show, the targeted number and the list
    show = 5
    target = 785785.55
    # lis = [497.96, 10, 5084, 156.43, 381.3, 3298.85, 625.68]
    lis = list(equipment_data['本月计提折旧额'])

    diffs = []
    for n in range(1, len(lis) + 1):
        numbers = combinations(lis, n)
        # list the combinations and their absolute difference to target
        for combi in numbers:
            diffs.append([combi, abs(target - sum(combi))])

    diffs.sort(key=lambda x: x[1])

    print(diffs)
    exit(1)

    for item in diffs[:show]:
        print(item[0], round(item[1], 10))
    '''
