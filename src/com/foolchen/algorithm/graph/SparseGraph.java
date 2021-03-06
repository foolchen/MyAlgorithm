package com.foolchen.algorithm.graph;

import java.util.Vector;

/**
 * 稀疏图（邻接表）
 *
 * @author chenchong. Created on 2018/5/16.
 */
public class SparseGraph implements Graph {
  private int n; // 节点数
  private int m; // 边数
  private boolean isDirected; // 用于标识是否是有向图
  private Vector<Integer>[] g; // 边的具体指向关系

  public SparseGraph(int n, boolean isDirected) {
    assert n > 0;
    this.n = n;
    this.m = 0;
    this.isDirected = isDirected;

    // g初始化为n个空的Vector
    // 初始化时都为空，即没有任何边
    //noinspection unchecked
    g = new Vector[n];
    for (int i = 0; i < n; i++) {
      g[i] = new Vector<>();
    }
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

    // 如果v==w时，会出现自环边
    // 如果重复添加v->w边，则会出现平行边
    // 在此处进行hasEdge()的检查的话，会使该方法的时间复杂度变为O（n）
    // 故通常在addEdge()时允许有平行边
    // 在所有边都添加完成后，再删除平行边

    // 向v对应的Vector中添加w节点，表示建立v->w的边
    g[v].add(w);
    if (!isDirected) {
      // 如果为无向图，则同时向w对应的Vector中添加v节点，表示简历w->的边
      g[w].add(v);
    }
    m++;
  }

  @Override public boolean hasEdge(int v, int w) {
    assert v >= 0 && v < n;
    assert w >= 0 && w < n;

    // 时间复杂度为O（n）
    for (int i = 0; i < g[v].size(); i++) {
      if (g[v].get(i) == w) {
        return true;
      }
    }
    return false;
  }

  @Override public Iterable<Integer> iterator(int v) {
    assert v >= 0 && v < n;
    return g[v]; // 邻接表中，g[v]存储了其所有邻边
  }

  @Override public void show() {
    StringBuilder builder = new StringBuilder();
    builder.append("DenseGraph: [\n");
    for (int i = 0; i < n; i++) {
      builder.append("\t").append(i).append(" : ");
      Vector<Integer> vector = g[i];
      for (int j = 0; j < vector.size(); j++) {
        builder.append(vector.elementAt(j)).append("\t");
      }
      builder.append("\n");
    }
    builder.append("]");
    System.out.println(builder.toString());
  }
}
