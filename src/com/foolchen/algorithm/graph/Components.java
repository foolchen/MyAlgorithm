package com.foolchen.algorithm.graph;

/**
 * 使用该类来求取一个Graph的联通分量
 *
 * @author chenchong. Created on 2018/6/12.
 */
public class Components {
  private Graph mGraph;// 要求联通分量的Graph的引用
  private boolean[] mVisited;// 记录一个节点是否已经被访问过
  private int mCount;// 联通分量的数量
  private int[] mIds;// 记录每个节点对应的联通分量标记（在同一个联通分量中的节点拥有共同的id）

  public Components(Graph graph) {
    this.mGraph = graph;
    mVisited = new boolean[mGraph.V()]; // visited数组的长度与Graph的顶点数量相同
    mIds = new int[mGraph.V()]; // 同上
    init();

    // 初始化完成后，求Graph的联通分量
    int vertexNum = mGraph.V();
    for (int i = 0; i < vertexNum; i++) {
      if (!mVisited[i]) {
        // 针对未访问过的顶点进行深度优先遍历
        dfs(i);
        // 由于是深度优先遍历，则每次dfs会把一个联通分量中的所有节点全部遍历完成
        // 此时应该将联通分量的数量+1
        mCount++;
      }
    }
  }

  /**
   * 获取联通分量的数量
   */
  public int getCount() {
    return mCount;
  }

  /**
   * 判断两个顶点是否联通
   */
  public boolean isConnected(int v, int w) {
    assert v >= 0 && v < mGraph.V();
    assert w >= 0 && w < mGraph.V();
    return mVisited[v] == mVisited[w];
  }

  private void init() {
    for (int i = 0; i < mVisited.length; i++) {
      mVisited[i] = false;
    }

    for (int i = 0; i < mIds.length; i++) {
      mIds[i] = -1;
    }
  }

  // 对节点v所在的联通分量进行深度优先遍历
  private void dfs(int v) {
    mVisited[v] = true;
    // 每个联通分量遍历完成时mCount会+1，则在dfs过程中（同一个联通分量遍历过程中）mCount的值是一致的，可以作为分量标记
    mIds[v] = mCount;
    Iterable<Integer> iterator = mGraph.iterator(v);
    for (Integer i : iterator) {
      if (!mVisited[i]) {
        dfs(i);
      }
    }
  }
}
