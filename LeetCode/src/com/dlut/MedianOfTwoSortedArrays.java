package com.dlut;

import java.util.ArrayList;
import java.util.List;

/**
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 *
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 *
 * You may assume nums1 and nums2 cannot be both empty.
 *
 * hard!!!46ms
 */
public class MedianOfTwoSortedArrays
{
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        List sumList;
        int sizeOfTwoArrays;
        int nums1Length;
        int nums2Length;

        sumList = new ArrayList<>();
        nums1Length = nums1.length;
        nums2Length = nums2.length;
        sizeOfTwoArrays = nums1Length + nums2Length;

        int i = 0;
        int j = 0;
        while(i <= nums1Length && j <= nums2Length)
        {
            if (i == nums1Length || nums1Length == 0)
            {
                sumList.add(nums2[j]);
                j++;
            }
            else if (j == nums2Length || nums2Length==0)
            {
                sumList.add(nums1[i]);
                i++;
            }
            else if(nums1[i] < nums2[j])
            {
                sumList.add(nums1[i]);
                i++;
            }
            else if(nums1[i] >= nums2[j])
            {
                sumList.add(nums2[j]);
                j++;
            }
            if (sizeOfTwoArrays%2 == 1 && sumList.size() == (sizeOfTwoArrays/2 + 1))
            {
                return (int)sumList.get(sumList.size()-1);
            }
            if (sizeOfTwoArrays%2 == 0 && sumList.size() == (sizeOfTwoArrays/2 + 1))
            {
                Double d1 = Double.valueOf((int)sumList.get(sumList.size() - 1));
                Double d2 = Double.valueOf((int)sumList.get(sumList.size() - 2));
                return (d1 + d2)/2;
            }
        }
        return -1;
    }

    public static void main(String[] args)
    {
        int [] a = {1,2};
        int [] b = {3,4};
        System.out.println(findMedianSortedArrays(a,b));
    }
}
