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
    int[] arr = ArrayUtils.generateReversedArray(100000);
    //quickSort(arr);
    //System.out.println("quick sort : " + Arrays.toString(arr));
    ArrayUtils.testSort(QuickSort.class, "quickSort", arr);
    //System.out.println("quick sort : " + Arrays.toString(arr));
    // 生成一个近乎有序的数组。此处生成200000个0~10的元素组成数组，元素重复数非常多。
    arr = ArrayUtils.generateRandomArray(100000, 0, 10);
    ArrayUtils.testSort(QuickSort.class, "quickSort", arr);
    // 消耗时间分别为18ms和249ms。发现在重复元素多时，快速排序的速度大大下降。
  }

  ///////////////////////////////////////////////////////////////////////////
  // 快速排序
  // 分治算法，本质还是将大的数组不断分割为小的数组进行递归调用
  // 该排序方式，选定标定值，并根据遍历元素的大小将整个数组不断分割为左右两个数组并且不断进行递归调用
  // 该方式存在两个缺点：
  // 1. 在数组近乎有序时，由于数组的左右分割严重不平衡，使时间复杂度退化至O(n^2)
  // 2. 在数组中重复元素过多时，数组的分割方式，导致重复元素不断被分配到其中的一个数组中，导致数组分割不平衡。
  // 第1个问题，可以通过随机选定标定值的方式来保证快速排序的平均时间复杂度为O(nLog n)。
  // 第2个问题，则需要通过双路快速排序来进行处理。
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

    /*if (l >= r) { // 已遍历完成，跳出
      return;
    }*/
    if (r - l <= 15) {
      // 对于小规模数组，使用插入排序进行优化
      SortGather.insertionSort(arr, l, r);
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
    // int value = arr[l];

    // 在数组近乎有序时，在进行partition操作时，左侧数组可能为0，此时时间复杂度可能会变为O(n^2)
    // 为了避免这种情况，随机取标定值，并将其交换到数组l处
    // 这样可以最大程度上避免时间复杂度变为O(n^2)的情况，并且也不需要对partition的其他部分做修改
    ArrayUtils.swap(arr, l, (int) (Math.random() * (r - l + 1)) + l);
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
