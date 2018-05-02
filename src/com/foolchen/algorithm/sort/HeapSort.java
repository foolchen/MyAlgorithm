package com.foolchen.algorithm.sort;

import com.foolchen.algorithm.sort.utils.ArrayUtils;
import java.util.Arrays;

/**
 * @author chenchong
 * 2018/4/22
 * 下午3:51
 */

class HeapSort {
  public static void main(String[] args) {
    int[] ints = ArrayUtils.generateRandomArray(10, 0, 100);
    Integer[] arr = new Integer[ints.length];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = ints[i];
    }
    heapSort(arr);
    System.out.println("result : " + Arrays.toString(arr));
  }

  ///////////////////////////////////////////////////////////////////////////
  // 原地的堆排序
  // 首先将数组按照最大堆的方式进行shift down
  // 然后以从最大堆中提取元素的方式，不断提取第一个元素并放到数组的第n-1,n-2,n-3,...0个位置
  ///////////////////////////////////////////////////////////////////////////
  private static void heapSort(Comparable[] arr) {

    int n = arr.length;

    // 首先进行heapify，对数组按照最大堆的方式进行排序
    for (int i = (n - 1 - 1) / 2; i >= 0; i--) {
      shiftDown(arr, n, i);
    }

    // 在进行heapify后，再以最大堆的取值顺序对数组进行重新排序
    for (int i = n - 1; i > 0; i--) {
      // 从最后一个元素开始向前遍历，不断将未处理的元素中的最大值向当前数组的最后一个位置交换
      // 并且在每次交换后进行shift down，保证剩余元素组成的数组为最大堆
      ArrayUtils.swap(arr, 0, i);

      // 每次交换后，0位置的元素都是从最后一个位置交换过来的，需要执行shift down来保证最大堆结构
      shiftDown(arr, i, 0);
    }
  }

  ///////////////////////////////////////////////////////////////////////////
  // 执行shift down操作
  // 此处n为数组的长度，用于计算非叶子节点的索引
  // k为要进行shift down的非叶子节点的索引
  ///////////////////////////////////////////////////////////////////////////
  private static void shiftDown(Comparable[] arr, int n, int k) {
    // 暂存当前元素
    Comparable element = arr[k];
    while (2 * k + 1 <= n - 1) {// 保证k的索引<第一个非叶子节点，注意此处n-1为元素数量
      // 默认取左叶子节点的索引
      int leafPosition = 2 * k + 1;
      if (leafPosition + 1 < n && arr[leafPosition + 1].compareTo(arr[leafPosition]) > 0) {
        // 左叶子节点的值<右叶子节点的值
        leafPosition++;
      }
      if (element.compareTo(arr[leafPosition]) >= 0) {
        // 如果最大的叶子<父节点，则不需要执行shift down，直接跳出循环
        break;
      }

      // 否则，交换父节点与叶子节点的元素
      arr[k] = arr[leafPosition];
      k = leafPosition;// k被重新赋值，下次shift down从此处开始
    }
    // 将父节点的值放到新的位置
    arr[k] = element;
  }
}
