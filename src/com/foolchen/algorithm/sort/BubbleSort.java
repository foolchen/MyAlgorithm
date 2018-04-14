package com.foolchen.algorithm.sort;

import com.foolchen.algorithm.sort.utils.ArrayUtils;
import java.util.Arrays;

/**
 * @author chenchong. Created on 2018/4/14.
 */
public class BubbleSort {

  public static void main(String[] args) {
    int[] arr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    int[] arr2 = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    sort(arr);
    sortOpt(arr2);
    System.out.println("sort : " + Arrays.toString(arr));
    System.out.println("sortOpt : " + Arrays.toString(arr2));

    int[] randomArr = ArrayUtils.generateRandomArray(20000, 1, 100000);
    int[] randomArr2 = Arrays.copyOf(randomArr, randomArr.length);
    ArrayUtils.testSort(BubbleSort.class, "sort", randomArr);
    ArrayUtils.testSort(BubbleSort.class, "sortOpt", randomArr2);
  }

  ///////////////////////////////////////////////////////////////////////////
  // 冒泡排序
  // 依次比较低(0,1)个元素，第(1,2)个元素...第(k-1,k)个元素,n-1次
  // 依次比较低(1,2)个元素，第(2,3)个元素...第(k-1,k)个元素,n-2次
  // 依次比较低(2,3)个元素，第(3,4)个元素...第(k-1,k)个元素,n-3次
  // 比较第k-1,k个元素,1次
  // 如果第k-1比k大，则交换两者的位置
  // 包含两层循环，时间复杂度为O(n^2)
  ///////////////////////////////////////////////////////////////////////////
  private static void sort(int[] arr) {
    int n = arr.length;
    /*for (int i = 0; i < n; i++) {// 外层循环需要执行n次
      for (int j = 0; j < n - i - 1; j++) { // 内层循环需要执行n-i次
        if (arr[j] > arr[j + 1]) {
          ArrayUtils.swap(arr, j, j + 1);
        }
      }
    }*/

    for (int i = n - 1; i > 0; i--) { // 外层执行n次
      for (int j = 0; j < i; j++) { // 内层循环执行n-1 -> n-i 次
        if (arr[j] > arr[j + 1]) {
          ArrayUtils.swap(arr, j, j + 1);
        }
      }
    }
  }

  ///////////////////////////////////////////////////////////////////////////
  // 冒泡排序的改进算法
  // 该算法减少了元素的交换次数，仅在内存循环结束时进行一次交换
  // 但是循环的执行次数与普通的冒泡排序相同，时间复杂度仍为O(n^2)
  ///////////////////////////////////////////////////////////////////////////
  private static void sortOpt(int[] arr) {
    int n = arr.length;
    for (int i = n - 1; i > 0; i--) { // 外层执行n次
      int maxIndex = 0;
      for (int j = 0; j < i; j++) { // 内层循环执行n-1 -> n-i 次
        if (arr[j] < arr[j + 1]) {
          maxIndex = j + 1;
        }
      }
      ArrayUtils.swap(arr, maxIndex, i);
    }
  }
}
