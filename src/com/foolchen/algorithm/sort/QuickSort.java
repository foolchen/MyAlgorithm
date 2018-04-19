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
    int[] hugeArr = ArrayUtils.generateReversedArray(100000);
    //quickSort(hugeArr);
    //System.out.println("quick sort : " + Arrays.toString(hugeArr));
    ArrayUtils.testSort(QuickSort.class, "quickSort", hugeArr);
    //System.out.println("quick sort : " + Arrays.toString(hugeArr));
    // 生成一个近乎有序的数组。此处生成200000个0~10的元素组成数组，元素重复数非常多。
    int[] sourceHugeArr = ArrayUtils.generateRandomArray(100000, 0, 10);
    ArrayUtils.testSort(QuickSort.class, "quickSort",
        Arrays.copyOf(sourceHugeArr, sourceHugeArr.length));
    // 针对sourceHugeArr，消耗249ms。发现在重复元素多时，快速排序的速度大大下降。

    // 先测试双路快速排序的正确性
    int[] arr = ArrayUtils.generateRandomArray(20, 0, 99);
    quickSortTwoWays(arr);
    System.out.println("quick sort two ways : " + Arrays.toString(arr));

    // 然后测试双路快速排序的时间消耗
    ArrayUtils.testSort(QuickSort.class, "quickSortTwoWays",
        Arrays.copyOf(sourceHugeArr, sourceHugeArr.length));
    // 双路快速排序，针对sourceHugeArr进行排序，消耗13ms

    // 首先测试三路快速排序的正确性
    arr = ArrayUtils.generateRandomArray(50, 0, 10);
    System.out.println("quick sort three ways source arr : " + Arrays.toString(arr));
    quickSortThreeWays(arr);
    System.out.println("quick sort three ways result arr : " + Arrays.toString(arr));

    // 测试三路快速排序的性能
    ArrayUtils.testSort(QuickSort.class, "quickSortThreeWays",
        Arrays.copyOf(sourceHugeArr, sourceHugeArr.length));
    // 普通快速排序 267ms,双路 13ms，三路 9ms
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

  ///////////////////////////////////////////////////////////////////////////
  // 双路快速排序
  ///////////////////////////////////////////////////////////////////////////
  private static void quickSortTwoWays(int[] arr) {
    int n = arr.length;
    quickSortTwoWays(arr, 0, n - 1);
  }

  // 对数组arr[l,r]进行双路快速排序
  private static void quickSortTwoWays(int[] arr, int l, int r) {

    if (r - l <= 15) {
      SortGather.insertionSort(arr, l, r);
      return;
    }

    int anchor = partitionTwoWays(arr, l, r);
    quickSortTwoWays(arr, l, anchor - 1);
    quickSortTwoWays(arr, anchor + 1, r);
  }

  ///////////////////////////////////////////////////////////////////////////
  // 该方法对数组arr[l,r]进行分割
  // 随机取标定点value，并使数组从前往后、从后往前同时进行遍历。
  // 针对从前往后的遍历：
  // 起始点i=l+1（索引l对应的元素为标定点value），不断向后遍历直至遇到一个元素arr[i]>=value，此时跳出遍历，得到索引i
  // 针对从后往前的遍历：
  // 起始点j=r（最后一个元素），不断向前遍历直至遇到一个元素arr[j]<=value，此时跳出遍历，得到索引j。
  // 两个遍历都跳出后，则交换i、j处的元素，使i++、j--，并继续遍历。
  // 直至i>j，跳出所有的循环。
  //
  // 针对双路遍历，得到arr[i]>=value、arr[j]<=value,并交换i、j处元素的过程中，不断将
  // 小于等于value的元素分割到arr[l+1,i)数组中，将大于等于value的元素分割到arr(j,r]数组中
  // 该过程，即使arr[i]==arr[j]也会进行交换，这样就将与value重复的元素不断的分配到了左右两个
  // 数组中，避免了两个数组分割不平衡，从而有效避免了快速排序时间复杂度退化为O(n^2)的情况
  //
  // 在最后的循环跳出后，i>j。此时i是整个数组中大于（大于等于）value的第一个索引
  // j是整个数组中小于（小于等于）value的最后一个索引
  // 故将l处的value与j处的元素进行交换，即得到了需要的两个数组，并且j即为两个数组的标定点
  ///////////////////////////////////////////////////////////////////////////
  private static int partitionTwoWays(int[] arr, int l, int r) {

    // 随机获取标定点
    ArrayUtils.swap(arr, l, (int) (Math.random() * (r - l + 1)) + l);
    int value = arr[l];

    int i = l + 1;
    int j = r;
    while (true) {
      // 在arr[i]<value的情况下，不断对arr[l+1,i)进行扩容即满足数组的需求
      while (i <= r && arr[i] < value) i++;
      // 在arr[j]>value的情况下，不断对arr(j,r]进行扩容即满足数组的需求
      while (j >= l + 1 && arr[j] > value) j--;
      // 在i>j时，整个数组遍历完成，此时跳出循环
      if (i > j) break;

      // 执行到此处时，得到了arr[i]>=value和arr[j]<=value
      // 此时将i、j处的元素交换，以使数组满足arr[l+1,i)<=value和arr(j,r]>=value
      ArrayUtils.swap(arr, i, j);
      // 在交换元素后，使各自索引继续改变，以便于进行下次的循环
      i++;
      j--;
    }

    ArrayUtils.swap(arr, l, j);
    return j;
  }

  ///////////////////////////////////////////////////////////////////////////
  // 三路快速排序
  // 遍历过程中，将整个数组划分为三部分
  // arr[l+1,lt]<value，arr[lt+1,i)==value，arr[gt,r]>value
  // i为正在比较的元素的索引。
  // 如果arr[i]<value，则将arr[i]与arr[lt+1]交换位置，并将lt+1
  // 这样可将arr[l+1,lt]扩容，并保证其元素全部小于value
  // 如果arr[i]==value，则仅经i+1，使arr[lt+1,i)扩容
  // 如果arr[i]>value，则将arr[i]有arr[gt-1]交换。相当于将arr[gt,r]扩容，并使其所有元素
  // 大于value。交换后arr[i]处的元素大小不确定，需要再次进行比较，故i的值保持不变
  // 在i>=gt时，遍历完成。此时arr[i]为数组中第一个大于value的元素。此时将arr[l]=value与arr[lt]
  // 交换即可得到需要的数组，此时使arr[lt+1,i)进行了扩容。
  // 并且得到了标定点索引lt
  ///////////////////////////////////////////////////////////////////////////
  private static void quickSortThreeWays(int[] arr) {
    int n = arr.length;
    quickSortThreeWays(arr, 0, n - 1);
  }

  // 对数组arr[l,r]进行快速排序
  // 供快速排序递归调用
  private static void quickSortThreeWays(int[] arr, int l, int r) {
    /*if (r - l <= 15) {
    }*/
    if (l >= r) {
      return;
    }

    partitionThreeWays(arr, l, r);
  }

  ///////////////////////////////////////////////////////////////////////////
  // 对数组arr[l,r]进行分割
  // arr[l+1,lt]<value，arr[lt+1,i)==value，arr[gt,r]>value
  // 由于在分割中生成了三个子数组，故此处不返回标定点对应的索引，而是在该方法中继续使用
  // arr[l+1,lt]、arr[gt,r]进行递归调用来完成快速排序
  ///////////////////////////////////////////////////////////////////////////
  private static void partitionThreeWays(int[] arr, int l, int r) {
    if (l >= r) {
      return;
    }
    ArrayUtils.swap(arr, l, (int) (Math.random() * (r - l + 1)) + l);
    int value = arr[l];

    int lt = l;
    int gt = r + 1;
    int i = l + 1;
    while (i < gt) {
      if (arr[i] < value) {
        ArrayUtils.swap(arr, lt + 1, i);
        lt++;
        i++;
      } else if (arr[i] > value) {
        ArrayUtils.swap(arr, gt - 1, i);
        gt--;
      } else {
        i++;
      }
    }
    ArrayUtils.swap(arr, l, lt);
    lt--;
    quickSortThreeWays(arr, l, lt);
    quickSortThreeWays(arr, gt, r);
  }
}
