package com.foolchen.algorithm.graph;

import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

/**
 * 用于查找Graph的最短路径
 *
 * @author chenchong. Created on 2018/6/13.
 */
public class ShortestPath {
  private Graph mGraph;
  private int mStart;
  private boolean[] mVisited;
  private int[] mFrom;
  private int[] mOrder; // 用于记录路径中节点的次序

  public ShortestPath(Graph graph, int startVertex) {
    mGraph = graph;
    mStart = startVertex;
    init();

    // 无向图使用广度优先遍历，能够得到两个顶点之间的最短路径
    LinkedList<Integer> queue = new LinkedList<>();
    queue.push(startVertex);
    mVisited[startVertex] = true;
    mOrder[startVertex] = 0;
    while (!queue.isEmpty()) {
      Integer v = queue.pop(); // 当前已访问的顶点s
      // 广度优先遍历，需要将v直接连接的顶点添加到队列中
      for (Integer i : mGraph.iterator(v)) {
        if (!mVisited[i]) {
          queue.push(i);
          mVisited[i] = true; // 已经添加到队列中的顶点一定会被访问，故此处直接将已访问状态置为true，防止重复访问
          mFrom[i] = v;
          mOrder[i] = mOrder[v] + 1;
        }
      }
    }
  }

  /**
   * 判断起始顶点与w之间是否存在路径
   */
  public boolean hasPath(int w) {
    assert w >= 0 && w < mGraph.V();
    return mVisited[w];
  }

  /**
   * 获取起始顶点与w顶点之间的路径
   */
  public Vector<Integer> getPath(int w) {
    assert hasPath(w);
    Stack<Integer> stack = new Stack<>();
    int vertex = w;
    while (vertex != -1) {
      stack.push(vertex);
      vertex = mFrom[vertex];
    }
    Vector<Integer> result = new Vector<>();
    while (!stack.isEmpty()) {
      result.add(stack.pop());
    }
    return result;
  }

  private void init() {
    mVisited = new boolean[mGraph.V()];
    for (int i = 0; i < mVisited.length; i++) {
      mVisited[i] = false;
    }

    mFrom = new int[mGraph.V()];
    mOrder = new int[mGraph.V()];
    for (int i = 0; i < mFrom.length; i++) {
      mFrom[i] = -1;
      mOrder[i] = -1;
    }
  }
}
