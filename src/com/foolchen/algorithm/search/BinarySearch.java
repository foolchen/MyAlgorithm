package com.foolchen.algorithm.search;

import java.util.Arrays;

/**
 * 二分查找
 *
 * @author chenchong. Created on 2018/5/2.
 */
public class BinarySearch {
  public static void main(String[] args) {
    int N = 1000000;
    Integer[] arr = new Integer[N];
    for (int i = 0; i < N; i++)
      arr[i] = i;

    // 对于我们的待查找数组[0...N)
    // 对[0...N)区间的数值使用二分查找，最终结果应该就是数字本身
    // 对[N...2*N)区间的数值使用二分查找，因为这些数字不在arr中，结果为-1
    for (int i = 0; i < 2 * N; i++) {
      int v = binarySearch(arr, i);
      if (i < N) {
        assert v == i;
      } else {
        assert v == -1;
      }
      v = binarySearchRecursive(arr, i);
      if (i < N) {
        assert v == i;
      } else {
        assert v == -1;
      }
    }

    arr = new Integer[] {100, 83, 74, 6723, 754, 93, 673, 57};
    Arrays.sort(arr);
    System.out.println("source arr : " + Arrays.asList(arr));
    System.out.println(
        "binarySearch() : target element index is " + binarySearch(arr, 93));
    System.out.println(
        "binarySearch() : target element index is " + binarySearchRecursive(arr, 93));
  }

  /**
   * 二分查找，非递归实现
   *
   * @return 如果查找到了target，则返回其索引；否则返回-1
   */
  private static <T extends Comparable<T>> int binarySearch(T[] arr, T target) {

    // 在数组arr[l,r]中查找元素，故限定查找的索引为0~arr.length-1，防止出现越界
    int l = 0, r = arr.length - 1;

    // 在查找的过程中，l、r在不断变化，如果l<=r，则表示数组尚未查找完毕
    while (l <= r) {
      // 求取数组中间元素的索引
      //int mid = (l + r) / 2;
      // 该计算方式的结果与上面的结果相同，但是避免了使用加法导致的整型数据溢出
      int mid = l + (r - l) / 2;

      if (arr[mid].compareTo(target) == 0) {
        // 此时查找到了需要的元素，直接返回其索引
        return mid;
      }

      if (arr[mid].compareTo(target) > 0) {
        // 此时中间元素的值>目标元素的值，则证明目标元素可能存在于左侧数组中
        // 则将数组右边界的索引变更为mid-1，便于进行下次查找
        r = mid - 1;
      } else {
        // 此时中间元素的值<目标元素的值，证明目标元素可能存在于右侧数组中
        // 则将数组左边界的索引变更为mid-1，便于进行下次查找
        l = mid + 1;
      }
    }

    return -1;
  }

  /**
   * 二分查找，递归实现
   *
   * @return 如果查找到了target，则返回其索引；否则返回-1
   */
  private static <T extends Comparable<T>> int binarySearchRecursive(T[] arr, T target) {
    int l = 0, r = arr.length - 1;
    return binarySearchRecursive(arr, target, l, r);
  }

  /**
   * 二分查找的递归过程
   *
   * @param arr 要查找的源数组
   * @param target 要查找的目标元素
   * @param l 要查找的数组的左边界
   * @param r 要查找的数组的右边界
   * @return 如果查找到了目标元素，则返回其索引；否则返回-1
   */
  private static <T extends Comparable<T>> int binarySearchRecursive(T[] arr, T target, int l,
      int r) {
    if (l > r) {
      // 此时数组已经查找完成，尚未查找到目标元素，则查找失败
      return -1;
    }

    // 计算中间值的索引
    int mid = l + (r - l) / 2;
    if (arr[mid].compareTo(target) == 0) {
      // 查找到了目标元素
      return mid;
    } else if (arr[mid].compareTo(target) > 0) {
      // 中间元素的值>目标值，目标值可能在左侧数组，对左侧数组进行递归调用
      return binarySearchRecursive(arr, target, l, mid - 1);
    } else {
      // 中间元素的值<目标值，目标值可能在右侧数组，对右侧数组进行递归调用
      return binarySearchRecursive(arr, target, mid + 1, r);
    }
  }
}
