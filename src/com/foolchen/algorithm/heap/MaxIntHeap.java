package com.foolchen.algorithm.heap;

import com.foolchen.algorithm.sort.utils.ArrayUtils;
import java.util.Arrays;

/**
 * 整型最大堆<p/>
 * 每个节点的子节点都小于其父节点。
 * 在插入一个新的元素时，需要执行向上移位(shift up)，在取出元素时需要执行向下移位（shift down），使当前的堆仍然是一个最大堆
 *
 * 如果根节点的索引为1，则其leftChildIndex=2*i、rightChildIndex=2*i+1，通过子节点计算父节点索引parentIndex = i/2<br/>
 * 如果根节点的索引为0，则其leftChildIndex=2*i+1、rightChildIndex=2*i+2，通过子节点计算父节点索引parentIndex=(i-1)/2
 *
 * @author chenchong. Created on 2018/4/19.
 */
public class MaxIntHeap {
  ///////////////////////////////////////////////////////////////////////////
  // 一个最大对，需要具备构造函数、插入方法、取出方法、获取元素数量
  // 插入方法对应的需要有shift up方法，取出方法需要有对应的shift down方法
  ///////////////////////////////////////////////////////////////////////////
  private final int[] data;
  private int count;
  private int capacity;

  public MaxIntHeap(int capacity) {
    data = new int[capacity + 1];// 该最大对根节点的索引从1开始，索引0的位置限制，故需要capacity+1的容量
    count = 0;
    this.capacity = capacity;
  }

  public MaxIntHeap(int[] arr) {
    int length = arr.length;
    data = new int[length + 1];
    capacity = length;
    count = length;
    for (int i = 0; i < length; i++) {
      data[i + 1] = arr[i];
    }

    // 向下位移需要从第一个没有叶子的节点之前的一个节点（即第一个其叶子节点是最后一个节点的父节点）开始
    // 此处i=count/2即为该节点
    // 从该节点开始，对之前的所有节点进行向下位移，最终得到父节点的值大于其子节点的值，从而得到最大堆
    for (int i = count / 2; i >= 1; i--) {
      shiftDown(i);
    }
  }

  public int getCount() {
    return count;
  }

  public boolean isEmpty() {
    return count == 0;
  }

  /**
   * 向最大堆中插入一个新的元素
   */
  public void insert(int element) {
    assert count + 1 <= capacity; // 确定如果再增加一个元素是否超出了堆的容量。如果超出则报错。
    data[count + 1] = element;
    count++;
    // 对新插入的元素进行向上位移，以满足最大堆的结构
    shiftUp(count);
  }

  public int extract() {
    assert count > 0;// 确定堆中还有元素，如果已经没有元素，则报错
    int ret = data[1]; // 取出数组中的第1个元素
    // 并且将最后一个元素交换到第1个索引位置
    ArrayUtils.swap(data, 1, count);
    count--;// 每取出一个元素，将count-1
    // 并且对第一个元素执行向下位移，以满足最大堆的结构
    shiftDown(1);
    return ret;
  }

  public int getMaxElement() {
    assert count > 0;
    return data[1];
  }

  @Override public String toString() {
    StringBuilder builder = new StringBuilder("MaxIntHeap [");
    for (int i = 0; i < data.length; i++) {
      if (i != 0) {
        builder.append(data[i]);
        if (i < data.length - 1) {
          builder.append(", ");
        }
      }
    }
    builder.append("]");
    return builder.toString();
  }

  // 向下位移
  // 将当前位置的元素与其子节点比较
  // 如果子节点元素值>当前值，则将子节点中的最大值与当前值进行交换
  private void shiftDown(int position) {
    while (2 * position <= count) {
      // 只要当前节点的索引位置未超出堆的上限，则持续执行shiftDown
      int left = 2 * position; // 得到了子节点的索引
      int right = left + 1;
      int anchor = left;
      if (right <= count && data[right] > data[left]) {
        // 存在右叶子，并且右叶子的值比左叶子的值大，则取最大值的索引
        anchor = right;
      }

      if (data[anchor] <= data[position]) {
        // 如果两个叶子节点的值均比父节点的值小，则直接跳出
        break;
      }

      ArrayUtils.swap(data, anchor, position);
      // 将position重新指向下移完成的元素，继续下一次循环
      position = anchor;
    }
  }

  // 向上位移
  // 将当前元素值与其父节点进行比较，如果当前元素值大则交换两者位置
  // 并从交换完后的当前节点位置继续执行上述操作
  private void shiftUp(int position) {
    while (position > 1) {
      if (data[position] > data[position / 2]) {
        // 当前元素的值>父节点元素的值
        ArrayUtils.swap(data, position, position / 2);
        position = position / 2;
      } else {
        // 如果当前元素的值<=父节点元素的值，则直接跳出循环。当前的树即为完全二叉树，堆即为最大二叉堆
        break;
      }
    }
  }

  public static void main(String[] args) {
    int[] arr = {62, 41, 30, 28, 16, 22, 13, 19, 17, 15};
    ArrayUtils.disorderArr(arr, 5);
    System.out.println("disordered arr : " + Arrays.toString(arr));
    MaxIntHeap heap = new MaxIntHeap(arr);
    System.out.println("max heap constructed by arr : " + heap.toString());
    int count = heap.getCount();
    int[] result = new int[count];
    for (int i = 0; i < count; i++) {
      result[i] = heap.extract();
    }
    System.out.println("result : " + Arrays.toString(result));

    heap = new MaxIntHeap(10);
    for (int e : arr) {
      heap.insert(e);
    }

    count = heap.getCount();
    result = new int[count];
    for (int i = 0; i < count; i++) {
      result[i] = heap.extract();
    }
    System.out.println("max heap constructed by loop : " + heap.toString());
    System.out.println("result : " + Arrays.toString(result));
  }
}
