package com.foolchen.algorithm.graph;

import java.util.Vector;

/**
 * @author chenchong. Created on 2018/5/18.
 */
public class GraphTests {
  public static void main(String[] args) {
    //createGraphs();
    //createGraphsByScanner();
    findPathDFS();
  }

  private static void findPathDFS() {
    GraphScanner scanner = new GraphScanner();
    Graph graph = scanner.createGraph("testG1.txt", false, false);
    graph.show();

    Path path = new Path(graph, 0);
    Vector<Integer> v = path.getPath(6);
    System.out.println("path : " + v);

    ShortestPath shortestPath = new ShortestPath(graph, 0);
    v = shortestPath.getPath(6);
    System.out.println("shortest path : " + v);
  }

  private static void createGraphsByScanner() {
    GraphScanner scanner = new GraphScanner();
    System.out.println("Graph1：");
    Graph graph = scanner.createGraph("testG1.txt", true, false);
    graph.show();
    graph = scanner.createGraph("testG1.txt", false, false);
    graph.show();
    System.out.println("Graph1，Component count is " + new Components(graph).getCount());
    System.out.println("Graph2：");
    graph = scanner.createGraph("testG2.txt", true, false);
    graph.show();
    graph = scanner.createGraph("testG2.txt", false, false);
    graph.show();
    System.out.println("Graph2，Component count is " + new Components(graph).getCount());
  }

  private static void createGraphs() {
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
      builder.append(v).append(": ");
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
