package com.foolchen.algorithm.sort;

import java.util.Arrays;

import static com.foolchen.algorithm.sort.utils.ArrayUtils.generateNearlyOrderedArray;
import static com.foolchen.algorithm.sort.utils.ArrayUtils.generateRandomArray;
import static com.foolchen.algorithm.sort.utils.ArrayUtils.testSort;

/**
 * 所有的排序算法比较
 *
 * @author chenchong. Created on 2018/4/14.
 */
public class SortTests {
  public static void main(String[] args) {
    // 所有的排序算法对20000个随机元素进行排序
    System.out.println("对20000个无序元素进行排序");
    int[] arr = generateRandomArray(20000, 1, 100000);
    int[] arr2 = Arrays.copyOf(arr, arr.length);
    int[] arr3 = Arrays.copyOf(arr, arr.length);

    testSort(SortGather.class, "selectionSort", arr);
    testSort(SortGather.class, "insertionSort", arr2);
    testSort(SortGather.class, "bubbleSort", arr3);
    /*
    com.foolchen.algorithm.sort.SortGather.selectionSort: 178ms
    com.foolchen.algorithm.sort.SortGather.insertionSort: 55ms
    com.foolchen.algorithm.sort.SortGather.bubbleSort: 245ms
     */
    System.out.println("------------------------------");
    System.out.println("对20000个元素的近乎有序的数组进行排序");
    int[] nearlyOrderedArr = generateNearlyOrderedArray(20000, 10);
    int[] nearlyOrderedArr2 = Arrays.copyOf(nearlyOrderedArr, nearlyOrderedArr.length);
    int[] nearlyOrderedArr3 = Arrays.copyOf(nearlyOrderedArr, nearlyOrderedArr.length);
    testSort(SortGather.class, "selectionSort", nearlyOrderedArr);
    testSort(SortGather.class, "insertionSort", nearlyOrderedArr2);
    testSort(SortGather.class, "bubbleSort", nearlyOrderedArr3);
    /*
    com.foolchen.algorithm.sort.SortGather.selectionSort: 177ms
    com.foolchen.algorithm.sort.SortGather.insertionSort: 1ms
    com.foolchen.algorithm.sort.SortGather.bubbleSort: 251ms
    */

    /*
    冒泡排序即使优化，相比起插入排序来说也相差甚远
    选择排序对近乎有序的数组排序无法起到优化作用。
    故在后续的相对复杂的排序算法中，会采用插入排序作为子算法来对其他算法进行优化。
     */
  }
}
