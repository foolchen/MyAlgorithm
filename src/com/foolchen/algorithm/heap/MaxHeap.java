package com.foolchen.algorithm.heap;

import com.foolchen.algorithm.sort.utils.ArrayUtils;
import java.util.Arrays;

/**
 * 最大堆<p/>
 * 每个节点的子节点都小于其父节点。
 * 在插入一个新的元素时，需要执行向上移位(shift up)，在取出元素时需要执行向下移位（shift down），使当前的堆仍然是一个最大堆
 *
 * 如果根节点的索引为1，则其leftChildIndex=2*i、rightChildIndex=2*i+1，通过子节点计算父节点索引parentIndex = i/2<br/>
 * 如果根节点的索引为0，则其leftChildIndex=2*i+1、rightChildIndex=2*i+2，通过子节点计算父节点索引parentIndex=(i-1)/2<br/>
 * 当前MaxHeap采用根节点索引为0的方式来创建
 *
 * @author chenchong. Created on 2018/4/20.
 */
public class MaxHeap<Item extends Comparable<Item>> {
  private Item[] data;
  private int capacity;
  private int count;

  public MaxHeap(int capacity) {
    this.capacity = capacity;
    this.count = 0;
    //noinspection unchecked
    data = (Item[]) new Comparable[capacity];
  }

  public MaxHeap(Item[] arr) {
    this.capacity = arr.length;
    //noinspection unchecked
    this.data = (Item[]) new Comparable[this.capacity];
    for (int i = 0; i < this.capacity; i++) {
      this.data[i] = arr[i];
    }
    this.count = this.capacity;

    // 针对非叶子节点进行shift down
    for (int i = (this.count - 1 - 1) / 2; i >= 0; i--) {
      shiftDown(i);
    }
  }

  public int getCount() {
    return count;
  }

  /**
   * 向堆中插入新的元素
   */
  public void insert(Item element) {
    // 首先检查数量是否已经达到了上限，此处后续进行扩容处理
    assert this.count + 1 <= capacity;

    // 如果没有达到上限，则先将新的元素插入最后一个位置
    // 并且数量+1
    data[count] = element;
    count++;
    // 然后针对该位置进行shift up操作
    shiftUp(count - 1);
  }

  /**
   * 从堆中获取最大的元素
   */
  public Item extract() {
    // 首先确认堆中还有数据
    assert count - 1 >= 0;

    // 如果还有数据，则取出最大的数据
    Item ret = data[0];
    // 此时将最后一个元素放到根节点的位置，并进行shiftDown
    ArrayUtils.swap(data, 0, count - 1);
    count--;
    shiftDown(0);
    return ret;
  }

  @Override public String toString() {
    StringBuilder builder = new StringBuilder("MaxHeap [");
    for (int i = 0; i < data.length; i++) {
      builder.append(data[i].toString());
      if (i < data.length - 1) {
        builder.append(", ");
      }
    }
    builder.append("]");
    return builder.toString();
  }

  // 向上位移
  // 将position处对应的元素值与其父节点值进行比较
  // 如果父节点的元素<position处的元素，则交换两者位置
  // 并不断进行循环
  private void shiftUp(int position) {
    while (position > 0 && data[(position - 1) / 2].compareTo(data[position]) < 0) {
      ArrayUtils.swap(data, (position - 1) / 2, position);
      position = (position - 1) / 2;
    }
  }

  // 向下位移
  // 从position开始，一直到第一个非叶子节点进行循环
  // 将position处的元素与其子节点的元素进行比较，如果小于子节点的元素，则将其与子节点中较大的一个进行交换
  // 并从新的位置开始进行新的循环
  private void shiftDown(int position) {
    while (2 * position + 1 <= count - 1) {
      // parentIndex = (i-1)/2，此处需要保证position第一个非叶子节点之前的节点。position之后的全部是叶子节点，没有进行遍历的意义
      // 首先取左叶子的索引
      int leafPosition = 2 * position + 1;
      // 如果存在右叶子
      if (leafPosition + 1 <= count - 1
          && data[leafPosition + 1].compareTo(data[leafPosition]) > 0) {
        // 如果右叶子的值>左叶子的值，则重新赋值
        leafPosition++;
      }
      if (data[leafPosition].compareTo(data[position]) <= 0) {
        // 叶子中的最大值<=父节点值，此时没有必要对父节点进行shift down，直接跳出
        break;
      }
      // 否则，将父节点与叶子节点交换
      ArrayUtils.swap(data, leafPosition, position);
      // 并且将父节点下沉后的新的索引赋值，进行下一次shift down
      position = leafPosition;
    }
  }

  public static void main(String[] args) {
    int[] arr = {62, 41, 30, 28, 16, 22, 13, 19, 17, 15};
    ArrayUtils.disorderArr(arr, 5);
    System.out.println("source elements : " + Arrays.toString(arr));
    MaxIntHeap maxIntHeap1 = new MaxIntHeap(arr.length);
    for (int value : arr) {
      maxIntHeap1.insert(value);
    }
    System.out.println("constructed by loop : " + maxIntHeap1);

    MaxHeap<Integer> maxHeap1 = new MaxHeap<Integer>(arr.length);
    for (int value : arr) {
      maxHeap1.insert(value);
    }
    System.out.println("constructed by loop : " + maxHeap1);

    int[] result = new int[maxIntHeap1.getCount()];
    for (int i = 0; i < result.length; i++) {
      result[i] = maxIntHeap1.extract();
    }
    System.out.println("result of MaxIntHeap constructed by loop :  " + Arrays.toString(result));
    result = new int[maxHeap1.getCount()];
    for (int i = 0; i < result.length; i++) {
      result[i] = maxHeap1.extract();
    }
    System.out.println("result of MaxHeap constructed by loop : " + Arrays.toString(result));
    //---------------------------------------------------------
    MaxIntHeap maxIntHeap2 = new MaxIntHeap(arr);
    System.out.println("constructed by constructor :  " + maxIntHeap2);
    Integer[] integers = new Integer[arr.length];
    for (int i = 0; i < integers.length; i++) {
      integers[i] = arr[i];
    }
    MaxHeap<Integer> maxHeap2 = new MaxHeap<Integer>(integers);
    System.out.println("constructed by constructor :  " + maxHeap2);

    result = new int[maxIntHeap2.getCount()];
    for (int i = 0; i < result.length; i++) {
      result[i] = maxIntHeap2.extract();
    }
    System.out.println(
        "result of MaxIntHeap constructed by constructor :  " + Arrays.toString(result));
    result = new int[maxHeap2.getCount()];
    for (int i = 0; i < result.length; i++) {
      result[i] = maxHeap2.extract();
    }
    System.out.println(
        "result of MaxHeap constructed by constructor :  " + Arrays.toString(result));
  }
}
