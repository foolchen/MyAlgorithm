package com.foolchen.algorithm.graph;

/**
 * @author chenchong. Created on 2018/5/16.
 */
public interface WeightedGraph<Weight extends Number & Comparable> {
  /** 返回图的节点数 */
  int V();

  /** 返回图的边数 */
  int E();

  /**
   * 向图中添加一条从v指向w的边（如果是无向图，则v->w，并且w->v）
   */
  void addEdge(Edge<Weight> edge);

  /**
   * 判断图中是否有从v->w的边
   *
   * @param v 边的起点
   * @param w 边的终点
   * @return 如果存在v->w的边，则返回true；否则false
   */
  boolean hasEdge(int v, int w);

  /**
   * 返回图中一个顶点对应的所有邻边
   */
  Iterable<Edge<Weight>> iterator(int v);

  /**
   * 在控制台打印Graph
   */
  void show();
}
