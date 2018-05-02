package com.foolchen.algorithm.search;

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
    }
  }

  /**
   * 在数组arr中查找target，非递归实现
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
        // 此时中间元素的值>目标元素的值，则证明目标元素可能存在于右侧数组中
        // 则将数组左边界的索引变更为mid+1，便于进行下次查找
        l = mid + 1;
      } else {
        // 此时中间元素的值<目标元素的值，证明目标元素可能存在于左侧数组中
        // 则将数组右边界的索引变更为mid-1，便于进行下次查找
        r = mid - 1;
      }
    }

    return -1;
  }
}
