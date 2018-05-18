package com.foolchen.algorithm.graph;

/**
 * @author chenchong. Created on 2018/5/18.
 */
public class GraphTests {
  public static void main(String[] args) {
    int N = 5; // 顶点数
    int M = 10; // 边数

    //begin 创建一个N个顶点、M条边的无向稀疏图
    Graph graph = new SparseGraph(N, false);
    // 向图中添加M条边
    for (int i = 0; i < M; i++) {
      int a = (int) (rand() * N);
      int b = (int) (rand() * N);
      graph.addEdge(a, b);
    }
    //end 创建一个N个顶点、M条边的无向稀疏图
    print(graph);
    graph = new DenseGraph(N, false);
    // 向图中添加M条边
    for (int i = 0; i < M; i++) {
      int a = (int) (rand() * N);
      int b = (int) (rand() * N);
      graph.addEdge(a, b);
    }
    print(graph);
  }

  private static void print(Graph graph) {
    int n = graph.V();
    StringBuilder builder = new StringBuilder();
    for (int v = 0; v < n; v++) {
      builder.append(": ");
      Iterable<Integer> iterator = graph.iterator(v);
      for (Integer w : iterator) {
        builder.append(w).append(" ");
      }
      builder.append("\n");
    }
    System.out.println(builder.toString());
  }

  private static double rand() {
    return Math.random();
  }
}
