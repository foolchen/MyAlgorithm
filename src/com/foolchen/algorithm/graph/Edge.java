package com.foolchen.algorithm.graph;

/**
 * 带权边，用于带权图
 *
 * @author chenchong. Created on 2018/6/14.
 */
public class Edge<Weight extends Number & Comparable> implements Comparable<Edge<Weight>> {
  private int a, b; // 边的两个顶点
  private Weight weight; // 边的权值

  /**
   * 构造函数
   *
   * @param a 边的一个顶点
   * @param b 边的另一个顶点
   * @param weight 边的权值
   */
  public Edge(int a, int b, Weight weight) {
    this.a = a;
    this.b = b;
    this.weight = weight;
  }

  /**
   * 构造函数，根据一条边的属性创建另一条属性一致的边
   *
   * @param edge 要创建的边的来源边
   */
  public Edge(Edge<Weight> edge) {
    this.a = edge.a;
    this.b = edge.b;
    this.weight = edge.weight;
  }

  /**
   * 获取边的一个顶点
   */
  public int v() {
    return a;
  }

  /**
   * 获取边的另一个顶点
   */
  public int w() {
    return b;
  }

  /**
   * 获取边的权值
   */
  public Weight weight() {
    return weight;
  }

  @Override public String toString() {
    return a + "-" + b + "(" + weight + ")";
  }

  @Override public int compareTo(Edge<Weight> o) {
    //noinspection unchecked
    return weight.compareTo(o.weight);
  }

  /**
   * 给定一个顶点，返回当前边的另一个顶点
   */
  public int other(int x) {
    assert x == a || x == b;
    return x == a ? b : a;
  }
}
