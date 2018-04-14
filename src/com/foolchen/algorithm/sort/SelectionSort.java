package com.foolchen.algorithm.sort;

import java.util.Arrays;

/**
 * @author chenchong. Created on 2018/4/12.
 */
public class SelectionSort {
  public static void main(String[] args) {
    int[] arr = ArrayUtils.generateRandomArray(1000, 1, 10000);
    ArrayUtils.testSort(SelectionSort.class.getName(), "sort", arr);
    System.out.println("result = " + Arrays.toString(arr));
    arr = ArrayUtils.generateRandomArray(10000, 1, 10000);
    ArrayUtils.testSort(SelectionSort.class.getName(), "sort", arr);
    System.out.println("result = " + Arrays.toString(arr));
  }

  ///////////////////////////////////////////////////////////////////////////
  // 选择排序
  // 每次循环找到数组中最小的元素，并将最小的元素与当前正在检查的元素进行交换
  ///////////////////////////////////////////////////////////////////////////
  public static void sort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n; i++) {// 此处执行n次循环
      // 默认当前要检查的元素为最小
      // 在随后的循环中检查是否为最小，如果不是，则重新进行赋值
      int minIndex = i;
      for (int j = i + 1; j < n; j++) { // 此处执行n-1次循环
        if (arr[j] < arr[minIndex]) {
          minIndex = j;
        }
      }

      // 在找到最小的元素index后，进行交换
      ArrayUtils.swap(arr, i, minIndex);
    }
  }
}
