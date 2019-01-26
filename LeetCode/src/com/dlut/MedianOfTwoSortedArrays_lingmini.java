package com.dlut;

import java.util.ArrayList;
import java.util.List;

/**
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 * <p>
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 * <p>
 * You may assume nums1 and nums2 cannot be both empty.
 * <p>
 * hard!!!28ms
 */
public class MedianOfTwoSortedArrays_lingmini
{
    public static double findMedianSortedArrays(int[] nums1, int[] nums2)
    {
        int sizeOfTwoArrays;
        int nums1Length;
        int nums2Length;

        nums1Length = nums1.length;
        nums2Length = nums2.length;
        sizeOfTwoArrays = nums1Length + nums2Length;

        int sumListLength = (sizeOfTwoArrays / 2) + 1;
        int[] sumList;
        sumList = new int[sumListLength];

        int flag = 0;

        int i = 0;
        int j = 0;
        for (int u = 0; u < sumListLength; u++)
        {
            if (i == nums1Length || nums1Length == 0)
            {
                sumList[flag] = nums2[j];
                flag++;
                j++;
            }
            else if (j == nums2Length || nums2Length == 0)
            {
                sumList[flag] = nums1[i];
                flag++;
                i++;
            }
            else if (nums1[i] < nums2[j])
            {
                sumList[flag] = nums1[i];
                flag++;
                i++;
            }
            else if (nums1[i] >= nums2[j])
            {
                sumList[flag] = nums2[j];
                flag++;
                j++;
            }
        }
        return (sizeOfTwoArrays%2==1) ? sumList[sumListLength-1] : (((float)sumList[sumListLength-1])+((float)sumList[sumListLength-2]))/2 ;
    }

    public static void main(String[] args)
    {
        int[] a = {1,2};
        int[] b = {};
        System.out.println(findMedianSortedArrays(a, b));
    }
}
