package com.foolchen.algorithm.graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * 该类用于读取文件并生成{@link Graph}
 *
 * @author chenchong. Created on 2018/6/12.
 */
public class GraphScanner {

  public Graph createGraph(String fileName, boolean isDense, boolean isDirected) {
    // 创建读取数据的scanner
    Scanner scanner = createScanner(fileName);
    int vertexNum = scanner.nextInt();
    if (vertexNum < 0) {
      throw new IllegalArgumentException("count of vertexes in a graph must be non-negative.");
    }
    int edgeNum = scanner.nextInt();
    if (edgeNum < 0) {
      throw new IllegalArgumentException("count of edges in a graph must be non-negative.");
    }

    Graph graph;
    if (isDense) {
      graph = new DenseGraph(vertexNum, isDirected);
    } else {
      graph = new SparseGraph(vertexNum, isDirected);
    }

    // 根据顶点的数量来读取数据
    for (int i = 0; i < vertexNum; i++) {
      // 读取一条边的两个顶点
      int v = scanner.nextInt();
      int w = scanner.nextInt();
      assert v >= 0 && v < vertexNum && w >= 0 && w < vertexNum;
      graph.addEdge(v, w);
    }

    // 数据读取完毕后，关闭scanner
    scanner.close();
    return graph;
  }

  public WeightedGraph<Double> createWeightedGraph(String fileName, boolean isDense,
      boolean isDirected) {
    // 创建读取数据的scanner
    Scanner scanner = createScanner(fileName);
    int vertexNum = scanner.nextInt();
    if (vertexNum < 0) {
      throw new IllegalArgumentException("count of vertexes in a graph must be non-negative.");
    }
    int edgeNum = scanner.nextInt();
    if (edgeNum < 0) {
      throw new IllegalArgumentException("count of edges in a graph must be non-negative.");
    }

    WeightedGraph<Double> graph;
    if (isDense) {
      graph = new DenseWeightedGraph<>(vertexNum, isDirected);
    } else {
      graph = new SparseWeightedGraph<>(vertexNum, isDirected);
    }

    // 根据顶点的数量来读取数据
    for (int i = 0; i < vertexNum; i++) {
      // 读取一条边的两个顶点
      int v = scanner.nextInt();
      int w = scanner.nextInt();
      double weight = scanner.nextDouble();
      assert v >= 0 && v < vertexNum && w >= 0 && w < vertexNum;
      graph.addEdge(new Edge<>(v, w, weight));
    }

    // 数据读取完毕后，关闭scanner
    scanner.close();
    return graph;
  }

  private Scanner createScanner(String fileName) {
    if (fileName == null || fileName.isEmpty()) {
      throw new IllegalArgumentException("file name should not be null or empty.");
    }

    File file = new File(fileName);
    if (!file.exists()) throw new IllegalArgumentException(fileName + " doesn't exists");

    try {
      return new Scanner(new FileInputStream(file));
    } catch (IOException e) {
      throw new IllegalArgumentException("could not open " + fileName + ".\n" + e.getMessage());
    }
  }
}
