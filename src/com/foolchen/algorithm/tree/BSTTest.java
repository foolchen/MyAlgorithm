package com.foolchen.algorithm.tree;

import com.foolchen.algorithm.utils.FileOperations;
import java.util.Vector;

/**
 * @author chenchong. Created on 2018/5/2.
 */
public class BSTTest {
  public static void main(String[] args) {
    /*int N = 1000000;

    // 创建一个数组，包含[0...N)的所有元素
    Integer[] arr = new Integer[N];
    for (int i = 0; i < N; i++)
      arr[i] = i;

    // 打乱数组顺序
    ArrayUtils.disorderArr(arr, 1000000);
    // 由于我们实现的二分搜索树不是平衡二叉树，
    // 所以如果按照顺序插入一组数据，我们的二分搜索树会退化成为一个链表

    // 我们测试用的的二分搜索树的键类型为Integer，值类型为String
    // 键值的对应关系为每个整型对应代表这个整型的字符串
    BST<Integer, String> bst = new BST<Integer, String>();
    for (int i = 0; i < N; i++)
      bst.insert(arr[i], Integer.toString(arr[i]));

    // 对[0...2*N)的所有整型测试在二分搜索树中查找
    // 若i在[0...N)之间，则能查找到整型所对应的字符串
    // 若i在[N...2*N)之间，则结果为null
    for (int i = 0; i < 2 * N; i++) {
      String res = bst.search(i);
      if (i < N) {
        assert res.equals(Integer.toString(i));
      } else {
        assert res == null;
      }
    }*/

    //testSearch();
    testTraverse();
  }

  private static void testSearch() {
    // 使用圣经作为我们的测试用例
    String filename = "./assets/bible.txt";
    Vector<String> words = new Vector<String>();
    if (FileOperations.readFile(filename, words)) {
      System.out.println("There are totally " + words.size() + " words in " + filename);
      System.out.println();

      // 测试 BST
      long startTime = System.currentTimeMillis();

      // 统计圣经中所有词的词频
      // 注: 这个词频统计法相对简陋, 没有考虑很多文本处理中的特殊问题
      // 在这里只做性能测试用
      BST<String, Integer> bst2 = new BST<String, Integer>();
      for (String word : words) {
        Integer res = bst2.search(word);
        if (res == null) {
          bst2.insert(word, 1);
        } else {
          bst2.insert(word, res + 1);
        }
      }

      // 输出圣经中god一词出现的频率
      if (bst2.contains("god")) {
        System.out.println("'god' : " + bst2.search("god"));
      } else {
        System.out.println("No word 'god' in " + filename);
      }

      long endTime = System.currentTimeMillis();
      System.out.println("BST , time: " + (endTime - startTime) + "ms.");

      System.out.println();

      // 测试顺序查找表 SST
      startTime = System.currentTimeMillis();

      // 统计圣经中所有词的词频
      // 注: 这个词频统计法相对简陋, 没有考虑很多文本处理中的特殊问题
      // 在这里只做性能测试用
      SST<String, Integer> sst = new SST<String, Integer>();
      for (String word : words) {
        Integer res = sst.search(word);
        if (res == null) {
          sst.insert(word, 1);
        } else {
          sst.insert(word, res + 1);
        }
      }

      System.out.println("SST insert complete");
      // 输出圣经中god一词出现的频率
      if (sst.contain("god")) {
        System.out.println("'god' : " + sst.search("god"));
      } else {
        System.out.println("No word 'god' in " + filename);
      }

      endTime = System.currentTimeMillis();
      System.out.println("SST , time: " + (endTime - startTime) + "ms.");
    }
  }

  private static void testTraverse() {
    BST<Integer, Integer> bst = new BST<Integer, Integer>();

    // 取n个取值范围在[0...m)的随机整数放进二分搜索树中
    int N = 10;
    int M = 100;
    for (int i = 0; i < N; i++) {
      Integer key = (int) (Math.random() * M);
      // 为了后续测试方便,这里value值取和key值一样
      bst.insert(key, key);
      System.out.print(key + " ");
    }
    System.out.println();

    // 测试二分搜索树的size()
    System.out.println("size: " + bst.size());
    System.out.println();

    // 测试二分搜索树的前序遍历 preOrder
    System.out.println("preOrder: ");
    bst.preOrder();
    System.out.println();

    // 测试二分搜索树的中序遍历 inOrder
    System.out.println("inOrder: ");
    bst.inOrder();
    System.out.println();

    // 测试二分搜索树的后序遍历 postOrder
    System.out.println("postOrder: ");
    bst.postOrder();
    System.out.println();

    System.out.println("levelOrder: ");
    bst.levelOrder();
    System.out.println();
  }
}
