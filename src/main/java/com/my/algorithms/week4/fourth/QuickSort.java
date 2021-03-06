package com.my.algorithms.week4.fourth;

import java.util.Arrays;

/**
 * Quick sort algorithm implementation.
 */
public class QuickSort extends AbstractQuickSort {

    public static void main(final String[] args) {

        final int[] unsortedForPivot = {6, 4, 3, 9, 2, 7, 11, 1};
        System.out.println("Initial values: " + Arrays.toString(unsortedForPivot));
        System.out.println("Pivot index: " + new QuickSort().partition(unsortedForPivot, 0, unsortedForPivot.length - 1));
        System.out.println("Partitioned values: " + Arrays.toString(unsortedForPivot) + "\n");

        final int[] unsorted = {3, 6, 2, 1, 8, 7, 3, 5};
        System.out.println("Initial values: " + Arrays.toString(unsorted));
        sort(unsorted);
        System.out.println("Sorted values: " + Arrays.toString(unsorted));
    }

    public static void sort(final int[] unsortedValues) {
        new QuickSort().quickSort(unsortedValues, 0, unsortedValues.length - 1);
    }

    @Override
    protected void definePivot(int[] unsorted, int left, int right) {
        // this is simple implementation without sophisticated selection of pivot element
    }
}
