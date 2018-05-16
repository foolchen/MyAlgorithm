package com.foolchen.algorithm.graph;

/**
 * 稠密图
 *
 * @author chenchong. Created on 2018/5/16.
 */
public class DenseGraph implements Graph {
  private int n; // 节点数
  private int m; // 边数
  private boolean isDirected; // 用于标识是否为有向图
  private boolean[][] g; // 具体的指向关系数组

  /**
   * 构造函数，用于创建DenseGraph
   *
   * @param n 节点数
   * @param isDirected 是否为有向图
   */
  public DenseGraph(int n, boolean isDirected) {
    assert n > 0;
    this.n = n;
    this.m = 0; // 初始时，边数为0
    this.isDirected = isDirected;

    // 二维数组g初始化为n*n的bool矩阵，初始时每一个g[i][h]均为false
    // 表示任意两个节点之间没有边
    g = new boolean[n][n];
  }

  @Override public int V() {
    return n;
  }

  @Override public int E() {
    return m;
  }

  @Override public void addEdge(int v, int w) {
    assert v >= 0 && v < n;
    assert w >= 0 && w < n;

    if (hasEdge(v, w)) {
      // 如果在v,w两个节点之间已经有边，则直接中断操作
      // 防止重复添加
      return;
    }

    // 首先建立v->w的边
    g[v][w] = true;
    // 然后，如果为无向图，则同步建立w->v的边
    if (!isDirected) {
      g[w][v] = true;
    }
    // 在添加边之后，对m进行递增，表示边的数量增加
    m++;
  }

  // 该方法适用于有向图和无向图
  @Override public boolean hasEdge(int v, int w) {
    assert v >= 0 && v < n;
    assert w >= 0 && w < n;
    return g[v][w];
  }
}
