package com.foolchen.algorithm.heap;

/**
 * @author chenchong. Created on 2018/6/20.
 */
public interface Heap<Item extends Comparable<Item>> {
  /**
   * 获取堆中的节点数量
   */
  int getCount();

  void insert(Item element);

  Item extract();
}
