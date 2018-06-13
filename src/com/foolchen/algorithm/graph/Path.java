package com.foolchen.algorithm.graph;

import java.util.Stack;
import java.util.Vector;

/**
 * Graph的路径
 *
 * @author chenchong. Created on 2018/6/13.
 */
public class Path {
  private Graph mGraph;
  private int mStart;
  private boolean[] mVisited;
  private int[] mFrom; // 用于记录当前顶点的上一个来源顶点

  public Path(Graph graph, int startVertex) {
    mGraph = graph;
    assert startVertex >= 0 && startVertex < mGraph.V();
    mStart = startVertex;
    init();

    // 开始执行寻路算法（深度优先）
    dfs(mStart);
  }

  /**
   * 查询从起点开始，是否有到达w的路径
   */
  public boolean hasPath(int w) {
    assert w >= 0 && w < mGraph.V();
    return mVisited[w];// 如果访问过w节点，则必定有路径到达w
  }

  /**
   * 得到从起点到顶点w的路径
   */
  public Vector<Integer> getPath(int w) {
    assert hasPath(w);
    // 由于mFrom中，当前位置存储元素为来源节点（上一个节点）
    // 故可以通过节点w查找到来源节点，同理也可以不断向前回溯，直到找到开始节点
    Stack<Integer> stack = new Stack<>();
    int vertex = w;
    while (vertex != -1) {
      stack.push(vertex);
      vertex = mFrom[vertex]; // 此处找到来源节点
    }
    // 当vertex == -1时，则证明已经找到了开始节点
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
    for (int i = 0; i < mFrom.length; i++) {
      mFrom[i] = -1;
    }
  }

  // 进行深度优先遍历，并进行寻路
  private void dfs(int vertex) {
    mVisited[vertex] = true;
    // 针对特定顶点生成迭代器，用于迭代遍历其所有连接的顶点
    Iterable<Integer> iterator = mGraph.iterator(vertex);
    for (Integer v : iterator) {
      if (!mVisited[v]) {
        // 如果当前顶点没有访问过，才进行访问
        mFrom[v] = vertex; // 来源节点为上一个节点
        dfs(v);
      }
    }
  }
}
