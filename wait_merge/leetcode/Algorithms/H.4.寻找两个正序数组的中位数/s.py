class Solution:
    def findMedianSortedArrays(self, nums1: List[int], nums2: List[int]) -> float:
        n1l = len(nums1)
        n2l = len(nums2)
        n = list()
        i = 0
        j = 0
        while i < n1l and j < n2l:
            if nums1[i] < nums2[j]:
                n.append(nums1[i])
                i += 1
            else:
                n.append(nums2[j])
                j += 1
        if i == n1l:
            n += nums2[j:]
        else:
            n += nums1[i:]

        nl = len(n)
        if nl % 2 == 0:
            return (n[int(nl / 2)] + n[int(nl / 2) - 1]) / 2
        else:
            return n[int(nl / 2)]
