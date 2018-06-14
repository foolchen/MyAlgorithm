package com.foolchen.algorithm.graph;

import java.util.Vector;

/**
 * 带权稠密图-邻接矩阵
 *
 * @author chenchong. Created on 2018/5/16.
 */
public class DenseWeightedGraph<Weight extends Number & Comparable>
    implements WeightedGraph<Weight> {
  private int n; // 节点数
  private int m; // 边数
  private boolean isDirected; // 用于标识是否为有向图
  private Edge<Weight>[][] g; // 具体的指向关系数组

  /**
   * 构造函数，用于创建DenseGraph
   *
   * @param n 节点数
   * @param isDirected 是否为有向图
   */
  public DenseWeightedGraph(int n, boolean isDirected) {
    assert n > 0;
    this.n = n;
    this.m = 0; // 初始时，边数为0
    this.isDirected = isDirected;

    // 二维数组g初始化为n*n的bool矩阵，初始时每一个g[i][h]均为false
    // 表示任意两个节点之间没有边
    // g = new boolean[n][n];
    // 此处g应该初始化为一个Edge的二维数组，初始化都为空
    //noinspection unchecked
    g = new Edge[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        g[i][j] = null;
      }
    }
  }

  @Override public int V() {
    return n;
  }

  @Override public int E() {
    return m;
  }

  @Override public void addEdge(Edge<Weight> edge) {
    assert edge.v() >= 0 && edge.v() < n;
    assert edge.w() >= 0 && edge.w() < n;

    if (hasEdge(edge.v(), edge.w())) {
      // 如果在v,w两个节点之间已经有边，则直接中断操作
      // 防止重复添加
      return;
    }

    // 首先建立v->w的边
    g[edge.v()][edge.w()] = new Edge<>(edge);
    // 然后，如果起始顶点和终止顶点不同，且未无向图，则同步建立w->v的边
    if (edge.v() != edge.w() && !isDirected) {
      g[edge.w()][edge.v()] = new Edge<>(edge.w(), edge.v(), edge.weight());
    }
    // 在添加边之后，对m进行递增，表示边的数量增加
    m++;
  }

  // 该方法适用于有向图和无向图
  @Override public boolean hasEdge(int v, int w) {
    assert v >= 0 && v < n;
    assert w >= 0 && w < n;
    return g[v][w] != null;
  }

  @Override public Iterable<Edge<Weight>> iterator(int v) {
    assert v >= 0 && v < n;
    Vector<Edge<Weight>> iter = new Vector<>();
    for (int i = 0; i < n; i++) {
      if (g[v][i] != null) { // 如果g[v][i]==true，则证明v->i存在邻边
        iter.add(g[v][i]);
      }
    }
    return iter;
  }

  @Override public void show() {
    StringBuilder builder = new StringBuilder();
    builder.append("DenseGraph: [\n");
    for (int i = 0; i < n; i++) {
      builder.append("\t");
      for (int j = 0; j < n; j++) {
        builder.append(g[i][j] == null ? "NULL" : g[i][j].weight()).append("\t");
      }
      builder.append("\n");
    }
    builder.append("]");
    System.out.println(builder.toString());
  }
}
