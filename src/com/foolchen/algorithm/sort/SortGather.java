package com.foolchen.algorithm.sort;

import com.foolchen.algorithm.sort.utils.ArrayUtils;

/**
 * 排序算法集合
 *
 * 该类中存放了排序算法的优化写法，可以用作其他算法的子算法
 *
 * @author chenchong. Created on 2018/4/14.
 */
public class SortGather {
  ///////////////////////////////////////////////////////////////////////////
  // 选择排序
  // 每次循环找到数组中最小的元素，并将最小的元素与当前正在检查的元素进行交换
  ///////////////////////////////////////////////////////////////////////////
  public static void selectionSort(int[] arr) {
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

  ///////////////////////////////////////////////////////////////////////////
  // 优化的插入排序
  // 在插入排序中，需要对检查值与比较值进行多次交换。
  // 该过程可以减少交换的次数（实际为减少了临时内存的分配和赋值的次数）
  // 并且在数组有序时，内层循环不会进入，此时时间复杂度为O(n)
  //
  // 故，优化的插入排序算法可以作为其他排序算法的子算法
  ///////////////////////////////////////////////////////////////////////////
  public static void insertionSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n; i++) { // 执行n次循环
      int element = arr[i]; // 将当前检查值拿出来
      // 将检查值所在位置之前的值与检查值进行比较
      // 如果比检查值大，则进入循环，并将元素后移
      int j;
      for (j = i; j > 0 && arr[j - 1] > element; j--) { // 执行n-1次
        arr[j] = arr[j - 1];
      }
      // 直到某个值比检查值element还要小时/或相等（或者索引位置已经到达0），则跳出了循环。
      arr[j] = element;
    }
  }

  ///////////////////////////////////////////////////////////////////////////
  // 插入排序
  // 对数组arr[l,r]进行排序
  ///////////////////////////////////////////////////////////////////////////
  public static void insertionSort(int[] arr, int l, int r) {
    // 由于第一个元素没有比较的必要，则从第二个元素开始比较
    for (int i = l + 1; i <= r; i++) {
      int element = arr[i];
      int j;
      for (j = i; j > l && arr[j - 1] > element; j--) {
        arr[j] = arr[j - 1];
      }
      arr[j] = element;
    }
  }

  ///////////////////////////////////////////////////////////////////////////
  // 冒泡排序的改进算法
  // 该算法减少了元素的交换次数，仅在内存循环结束时进行一次交换
  // 但是循环的执行次数与普通的冒泡排序相同，时间复杂度仍为O(n^2)
  ///////////////////////////////////////////////////////////////////////////
  public static void bubbleSort(int[] arr) {
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
