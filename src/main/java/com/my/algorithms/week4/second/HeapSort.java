package com.my.algorithms.week4.second;

import java.util.Arrays;
import static com.my.algorithms.tools.Arrays.swap;
import static com.my.algorithms.tools.Heaps.siftDownMin;
import static com.my.algorithms.tools.Math.roundUp;

/**
 * Heap sort algorithm implementation.
 */
public class HeapSort {

    public static void main(final String[] args) {

        final int[] maxHeapValues = {10, 11, 2, 8, 9, 4};
        System.out.println("Initial values: " + Arrays.toString(maxHeapValues));
        buildMaxHeap(maxHeapValues);
        System.out.println("Max heap values: " + Arrays.toString(maxHeapValues) + "\n");

        final int[] unsorted = {3, 6, 2, 1, 8, 7, 3, 5};
        System.out.println("Initial values: " + Arrays.toString(unsorted));
        sort(unsorted);
        System.out.println("Sorted values: " + Arrays.toString(unsorted));
    }

    public static void sort(final int[] unsorted) {
        buildMaxHeap(unsorted);
        int size = unsorted.length;
        for (int i = (unsorted.length - 1); i >= 1; i--) {
            swap(unsorted, i, 0);
            size--;
            siftDownMin(unsorted, 0, size - 1);
        }
    }

    private static void buildMaxHeap(int[] unsorted) {
        for (int index = roundUp(unsorted.length, 2); index >= 0; index--) {
            siftDownMin(unsorted, index, unsorted.length - 1);
        }
    }
}
