package com.foolchen.algorithm.sort;

import java.util.Arrays;

/**
 * @author chenchong. Created on 2018/4/14.
 */
public class InsertionSort {
  public static void main(String[] args) {
    int[] unorderedArr = {10, 9, 4, 6, 7, 8, 5, 3, 2, 1};
    int[] unorderedArr2 = {10, 9, 4, 6, 7, 8, 5, 3, 2, 1};
    int[] unorderedArr3 = {10, 9, 4, 6, 7, 8, 5, 3, 2, 1};
    sort(unorderedArr);
    sort2(unorderedArr2);
    sortOpt(unorderedArr3);
    System.out.println("sort : " + Arrays.toString(unorderedArr));
    System.out.println("sort2 : " + Arrays.toString(unorderedArr2));
    System.out.println("sortOpt : " + Arrays.toString(unorderedArr3));

    int[] arr = ArrayUtils.generateRandomArray(20000, 0, 100000);
    int[] arr2 = Arrays.copyOf(arr, arr.length);
    int[] arr3 = Arrays.copyOf(arr, arr.length);
    ArrayUtils.testSort(InsertionSort.class.getName(), "sort", arr);
    ArrayUtils.testSort(InsertionSort.class.getName(), "sort2", arr2);
    ArrayUtils.testSort(InsertionSort.class.getName(), "sortOpt", arr3);
  }

  ///////////////////////////////////////////////////////////////////////////
  // 插入排序
  // 从第二个元素开始，向前与之前的每个元素进行比较，如果比之前的元素小，则交换位置
  // n*(n-1) -> O(n^2)
  ///////////////////////////////////////////////////////////////////////////
  public static void sort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n; i++) { // 执行n次循环
      for (int j = i; j > 0; j--) { // 执行n-1次
        // 从i开始，向前进行比较
        if (arr[j] < arr[j - 1]) {
          // 如果当前要检查的元素小于之前的元素，则进行交换
          ArrayUtils.swap(arr, j, j - 1);
        } else {
          // 否则，则跳出循环
          break;
        }
      }
    }
  }

  public static void sort2(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n; i++) { // 执行n次循环
      // 此处，在不满足arr[j]<arr[j-1]条件时，则直接继续下一次循环。代码量少，但是性能并没有提升
      for (int j = i; j > 0 && arr[j] < arr[j - 1]; j--) { // 执行n-1次
        // 从i开始，向前进行比较
        // 如果当前要检查的元素小于之前的元素，则进行交换
        ArrayUtils.swap(arr, j, j - 1);
      }
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
  public static void sortOpt(int[] arr) {
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
}
