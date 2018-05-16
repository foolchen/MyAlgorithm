package com.foolchen.algorithm.graph;

/**
 * @author chenchong. Created on 2018/5/16.
 */
public interface Graph {
  /** 返回图的节点数 */
  int V();

  /** 返回图的边数 */
  int E();

  /**
   * 向图中添加一条从v指向w的边（如果是无向图，则v->w，并且w->v）
   *
   * @param v 边的起点（无向图时，v同时也为终点）
   * @param w 边的终点（无向图时，w同时也为起点）
   */
  void addEdge(int v, int w);

  /**
   * 判断图中是否有从v->w的边
   *
   * @param v 边的起点
   * @param w 边的终点
   * @return 如果存在v->w的边，则返回true；否则false
   */
  boolean hasEdge(int v, int w);
}
