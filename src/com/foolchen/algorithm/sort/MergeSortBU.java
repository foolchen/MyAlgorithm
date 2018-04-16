package com.foolchen.algorithm.sort;

import java.util.Arrays;

/**
 * @author chenchong. Created on 2018/4/16.
 */
public class MergeSortBU {

  public static void main(String[] args) {
    int[] arr = {10, 9, 7, 6, 5, 4, 3, 2, 1};
    sort(arr);
    System.out.println("sort : " + Arrays.toString(arr));
  }

  ///////////////////////////////////////////////////////////////////////////
  // 归并排序（从底部到顶部）
  // 该方法在一开始就将数组进行了平分（根据设定的数组元素），然后从下往上进行归并
  // 一般从数组元素size=1开始进行归并
  // 该方法未使用递归，仅适用了迭代
  ///////////////////////////////////////////////////////////////////////////
  private static void sort(int[] arr) {
    /*int n = arr.length;

    // 间隔size进行数组的归并，例如：[0,1]|[2,3]|[4,5]|[6,7] -> [0,3]|[4,7]
    for (int size = 1; size < n; size += size) {
      for (int i = 0; i < n - size; i += size * 2) { // 每次归并完成后，应该跨越size*2个元素（两个元素数量为size的数组）
        if (arr[i + size - 1] > arr[i + size]) { // 此处i+size-1为左侧数组的最后一个元素的索引,i+size为右侧数组第一个元素的索引
          // 当右侧数组最小元素小于左侧数组最大元素时进行归并（此时无法保证两个数组直接合并后是有序的）
          //merge(arr, i, i + size - 1, i + size + size - 1);
          // 由于merge方法需要对左闭右闭区间的数组进行归并，故此处最后一个索引应为i+size+size-1
          // 并且由于i+size+size-1可能会超出索引的上限，此处去i+size+size-1与n-1其中的最小值
          merge(arr, i, i + size - 1, Math.min(i + size + size - 1, n - 1));
        }
      }
    }*/

    int n = arr.length;

    // 此处对归并排序进行优化
    // 由于在数组元素足够小时，插入排序的速度可能更快
    // 则此处以16个元素为跨度，对每16个元素组成的数组进行插入排序
    for (int i = 0; i < n; i += 16) {
      SortGather.insertionSort(arr, i, Math.min(i + 15, n - 1));
    }

    // 由于之前对16个元素以内的数组进行了插入排序
    // 故此处直接从元素数量size=16开始进行归并（[0,15],[16,31],[31,47]...，这些数组已有序，没有进行归并的必要）
    // 间隔size进行数组的归并，例如：[0,1]|[2,3]|[4,5]|[6,7] -> [0,3]|[4,7]
    for (int size = 16; size < n; size += size) {
      for (int i = 0; i < n - size; i += size * 2) { // 每次归并完成后，应该跨越size*2个元素（两个元素数量为size的数组）
        if (arr[i + size - 1] > arr[i + size]) { // 此处i+size-1为左侧数组的最后一个元素的索引,i+size为右侧数组第一个元素的索引
          // 当右侧数组最小元素小于左侧数组最大元素时进行归并（此时无法保证两个数组直接合并后是有序的）
          //merge(arr, i, i + size - 1, i + size + size - 1);
          // 由于merge方法需要对左闭右闭区间的数组进行归并，故此处最后一个索引应为i+size+size-1
          // 并且由于i+size+size-1可能会超出索引的上限，此处去i+size+size-1与n-1其中的最小值
          merge(arr, i, i + size - 1, Math.min(i + size + size - 1, n - 1));
        }
      }
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
