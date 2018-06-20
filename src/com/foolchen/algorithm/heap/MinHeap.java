package com.foolchen.algorithm.heap;

import com.foolchen.algorithm.sort.utils.ArrayUtils;
import java.util.Arrays;

/**
 * 最小堆<br/>
 * 最小堆中的元素应满足如下条件：任意一个非叶子节点的值都应该小于等于其子节点的值
 *
 * @author chenchong. Created on 2018/6/19.
 */
public class MinHeap<Item extends Comparable<Item>> implements Heap<Item> {
  ///////////////////////////////////////////////////////////////////////////
  // 假设当前节点的索引为i，则其子节点的索引计算方式如下：
  // left = i*2 + 1
  // right = i*2 + 2
  // 其父节点的索引计算方式如下：
  // parent = (i-1)/2
  ///////////////////////////////////////////////////////////////////////////

  private Item[] data; // 用于存放数据的数组
  private int capacity; // 最小堆的容量
  private int count; // 最小堆中的元素数量

  public MinHeap(int capacity) {
    this.capacity = capacity;
    this.count = 0;
    //noinspection unchecked
    this.data = (Item[]) new Comparable[capacity];
  }

  public MinHeap(Item[] arr) {
    assert arr != null;
    this.capacity = arr.length;
    this.count = this.capacity;
    //noinspection unchecked
    this.data = (Item[]) new Comparable[this.capacity];
    for (int i = 0; i < this.capacity; i++) {
      this.data[i] = arr[i];
    }
    // 针对所有的非叶子节点进行shift down操作，保证最小堆的数据结构
    int lastLeafPosition = count - 1;
    for (int i = (lastLeafPosition - 1) / 2; i >= 0; i--) {
      shiftDown(i);
    }
  }

  /**
   * 向最小堆中插入一条数据
   */
  public void insert(Item element) {
    assert this.count + 1 <= capacity;

    data[count++] = element;
    shiftUp(count - 1);
  }

  /**
   * 从堆中获取最小值
   */
  public Item extract() {
    assert count - 1 >= 0;
    Item ret = data[0];
    ArrayUtils.swap(data, 0, count - 1);
    count--;
    shiftDown(0);
    return ret;
  }

  public int getCount() {
    return count;
  }

  @Override public String toString() {
    StringBuilder builder = new StringBuilder("MinHeap [");
    for (int i = 0; i < data.length; i++) {
      builder.append(data[i].toString());
      if (i < data.length - 1) {
        builder.append(", ");
      }
    }
    builder.append("]");
    return builder.toString();
  }

  private void shiftDown(int position) {
    int lastLeafPosition = count - 1;
    while (position * 2 + 1 <= lastLeafPosition) {// 如果当前节点存在子节点，则对当前节点进行shift down操作
      // 计算得到两个子节点中的最小值
      int leftLeafIndex = position * 2 + 1;
      int rightLeafIndex = leftLeafIndex + 1;
      // 如果存在右侧子节点，并且右侧子节点的值小于左侧子节点的值，则要position交换的节点为较小的那一个
      int leafIndex =
          rightLeafIndex <= count - 1 && data[rightLeafIndex].compareTo(data[leftLeafIndex]) < 0
              ? rightLeafIndex : leftLeafIndex;
      if (data[leafIndex].compareTo(data[position]) >= 0) {
        // 如果所有的子节点都已经大于父节点，则当前结构已满足最小堆的数据结构，直接跳出循环
        break;
      }
      // 否则，则需要交换父节点与子节点最小值
      ArrayUtils.swap(data, position, leafIndex);
      // 父节点下沉后，则需要对position重新赋值，并且进行下一次的比较，直至满足最小堆的数据结构
      position = leafIndex;
    }
  }

  // 在向堆中插入一条数据时，需要对该数据所在的节点进行上升操作
  // 以满足整体的最小堆结构
  private void shiftUp(int position) {
    // 如果父节点大于当前节点，则需要交换两者
    while (position > 0 && data[(position - 1) / 2].compareTo(data[position]) > 0) {
      ArrayUtils.swap(data, (position - 1) / 2, position);
      position = (position - 1) / 2;
    }
  }

  public static void main(String[] args) {
    Integer[] arr = { 62, 41, 30, 28, 16, 22, 13, 19, 17, 15 };
    ArrayUtils.disorderArr(arr, 5);
    System.out.println("source elements : " + Arrays.toString(arr));

    Heap<Integer> heap = new MinHeap<>(arr);
    System.out.println("Min heap constructed by constructor : " + heap);

    System.out.print("[");
    int count = heap.getCount();
    for (int i = 0; i < count; i++) {
      System.out.print(heap.extract());
      if (i != count - 1) {
        System.out.print(" , ");
      }
    }
    System.out.println("]");
    heap = new MinHeap<>(arr.length);
    for (Integer e : arr) {
      heap.insert(e);
    }
    System.out.println("Min heap constructed by loop : " + heap);
    System.out.print("[");
    count = heap.getCount();
    for (int i = 0; i < count; i++) {
      System.out.print(heap.extract());
      if (i != count - 1) {
        System.out.print(" , ");
      }
    }
    System.out.println("]");
  }
}
