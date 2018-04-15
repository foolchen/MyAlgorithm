package com.foolchen.algorithm.sort;

import java.util.Arrays;

/**
 * @author chenchong
 * 2018/4/15
 * 下午9:38
 */

public class MergeSort {
  public static void main(String[] args) {
    int[] arr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    sort(arr);
    System.out.println("mergeSort:" + Arrays.toString(arr));
  }

  ///////////////////////////////////////////////////////////////////////////
  // 归并排序
  // 首先将整个数组不断二分，直至无法再次分割
  // 创建临时数组用于存放两个被分割后数组的元素
  // 左右两个数组进行遍历，如果哪个数组被遍历到的元素数更小，则替换到数组中
  ///////////////////////////////////////////////////////////////////////////
  private static void sort(int[] arr) {
    int n = arr.length;
    sort(arr, 0, n - 1);
  }

  // 对数组arr[l...r]进行排序
  private static void sort(int[] arr, int l, int r) {
    if (l >= r) {
      // 上下界相同，则直接返回
      return;
    }

    if (r - l <= 15) {
      // 在元素数量少时，则直接进行插入排序
      // 原因：插入排序的时间复杂度为O(N^2)，归并排序的时间复杂度为O(NLogN)
      //      可见，插入排序的时间复杂度中系数为1，归并排序时间复杂度的系数为N
      //      则在元素数量小的情况下，插入排序的时间增长比归并排序的时间增长要小
      //      故此时直接使用插入排序能够有效提升排序速度
      SortGather.insertionSort(arr, l, r);
      return;
    }

    // 计算得到中间索引
    int mid = (l + r) / 2;

    // 对两个分割后的数组进行递归调用继续进行分割
    sort(arr, l, mid);
    sort(arr, mid + 1, r);

    // 此处进行优化，仅在arr[mid]>arr[mid+1]时才进行归并
    // 原因：执行到此处时，arr[l,mid]、arr[mid+1,r]本身已经是有序数组
    //      如果arr[mid]<=[mid+1]，则其实两个数组没有继续比较的比较，直接连接就可以保证数组是有序的
    if (arr[mid] > arr[mid + 1]) {
      // 对递归调用完成的两个数组进行合并（两个数组各自已有序）
      merge(arr, l, mid, r);
    }
  }

  // 对arr[l..mid]以及arr[mid+1...r]数组进行归并
  private static void merge(int[] arr, int l, int mid, int r) {

    // 创建临时数组，用于存放要比较的元素
    int[] aux = Arrays.copyOfRange(arr, l, r + 1);
    // 由于该方法中的操作，针对arr数组来说，存在值为l的偏移量（从l处开始）
    // 则在使用索引时，都应该减去对应的偏移量l，才是aux数组正确的索引

    // 分别确定左右数组的起始索引
    int i = l;
    int j = mid + 1;

    for (int k = l; k <= r; k++) {
      if (i > mid) {
        // 此时左侧数组已全部遍历完成，则将右侧数组剩余的元素填充到数组中
        arr[k] = aux[j - l];
        j++;
      } else if (j > r) {
        // 此时右侧数组已全部遍历完成，将左侧数组剩余的元素填充到数组中
        arr[k] = aux[i - l];
        i++;
      } else if (aux[i - l] < aux[j - l]) {
        // 此时左侧数组的值比右侧数组的值小
        arr[k] = aux[i - l];
        i++;
      } else {
        // 此时右侧数组的值比左侧数组的值小
        arr[k] = aux[j - l];
        j++;
      }
    }
  }
}
