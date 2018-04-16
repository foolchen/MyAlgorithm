package com.foolchen.algorithm.sort;

import com.foolchen.algorithm.sort.utils.ArrayUtils;
import java.util.Arrays;

/**
 * @author chenchong
 * 2018/4/16
 * 下午9:24
 */

public class QuickSort {

  public static void main(String[] args) {
    int[] arr = ArrayUtils.generateReversedArray(20);
    quickSort(arr);
    System.out.println("quick sort : " + Arrays.toString(arr));
  }

  ///////////////////////////////////////////////////////////////////////////
  // 快速排序
  // 分治算法，本质还是将大的数组不断分割为小的数组进行递归调用
  ///////////////////////////////////////////////////////////////////////////
  private static void quickSort(int[] arr) {
    int n = arr.length;

    // 前后都是闭区间，便于理清思路。故此处r=n-1
    quickSort(arr, 0, n - 1);
  }

  ///////////////////////////////////////////////////////////////////////////
  // 对arr[l,r]左闭右闭区间的数组进行分治
  ///////////////////////////////////////////////////////////////////////////
  private static void quickSort(int[] arr, int l, int r) {

    if (l >= r) { // 已遍历完成，跳出
      return;
    }
    // 对arr[l,r]进行分治，然后返回标定点anchor（anchor索引对应的位置，位于两个数组的中间）
    int anchor = partition(arr, l, r);

    // 然后对得到的两个数组进行递归调用quickSort
    quickSort(arr, l, anchor - 1);
    quickSort(arr, anchor + 1, r);
  }

  ///////////////////////////////////////////////////////////////////////////
  // 对数组arr[l,r]进行分治
  // 返回p，使arr[l,p]<arr[p]，并且arr[p+1,r]>arr[p]
  ///////////////////////////////////////////////////////////////////////////
  private static int partition(int[] arr, int l, int r) {
    // 取数组的第一个元素作为标定值
    int value = arr[l];
    // 对数组[l+1,r]进行遍历，并在其中交换元素，使满足arr[l+1,j]<value,arr[j+1,i)>value
    // 由于i索引对应的元素是正在进行比较的元素，故右侧为开区间
    int j = l;
    for (int i = l + 1; i <= r; i++) {
      // 如果正在比较的值比value大，则应该放在arr[j+1,i)中，此时直接将i后移即可（其实不需做任何操作）
      if (arr[i] < value) {
        // 如果正在比较的值比value小，则应该将当前正在比较的值与arr[j+1,i)的第一个元素进行交换
        // 并将arr[j+1,i)的第一个元素的索引+1（由于第一个元素被替换为了小于value的值，该操作相当于对arr[l+1,j]进行扩容）
        ArrayUtils.swap(arr, j + 1, i);
        j++;
      }
    }

    // 在循环结束后，整个数组分为了三部分：value->arr[l],arr[l+1,j],arr[j+1,r]
    // 则此时将l位置与j位置的元素交换，则数组为arr[l,j-1]<=arr[j]<=arr[j+1,r]的组成
    ArrayUtils.swap(arr, l, j);
    // 此时的j即为标定点
    return j;
  }
}
