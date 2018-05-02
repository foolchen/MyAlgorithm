package com.foolchen.algorithm.tree;

import com.foolchen.algorithm.utils.FileOperations;
import java.util.Vector;

/**
 * 二叉搜索树（Binary Search Tree）<br/>
 * {@link #root}为当前节点的根节点，如果当前节点已经是根节点，则{@link #root}为空。<br/>
 * {@link #count}用于记录以当前节点为根的字数中的节点总数。
 *
 * @author chenchong. Created on 2018/5/2.
 */
public class BST<Key extends Comparable<Key>, Value> {
  /*
   二叉搜索树由一个个节点(Node)组成
   二叉搜索树不一定为完全二叉树
  */

  /**
   * 节点<br/>
   * 该类为私有的内部类，对外屏蔽其内部实现，防止在使用过程中对Node中的值进行修改<br/>
   * 其内部包含{@link Node#key}，用于对树进行排序和查找，{@link Node#value}为{@link Node#key}对应的值，
   * {@link Node#left}和{@link Node#right}分别为当前节点的左孩子和右孩子。
   */
  private class Node {
    private Key key;
    private Value value;
    private Node left, right;

    public Node(Key key, Value value) {
      this.key = key;
      this.value = value;
      this.left = this.right = null;
    }
  }

  private Node root;
  private int count;

  public BST() {
    this.root = null;
    this.count = 0;
  }

  /**
   * 获取当前二分搜索树中的节点个数
   */
  public int size() {
    return count;
  }

  /**
   * 判断当前二分搜索树是否为空
   */
  public boolean isEmpty() {
    return count == 0;
  }

  /**
   * 向二分搜索树中插入新的元素
   */
  public void insert(Key key, Value value) {
    root = insert(root, key, value);
  }

  /**
   * 判断二叉搜索树中是否存在键为key的元素
   *
   * @return 如果存在的返回true，否则false
   */
  public boolean contains(Key key) {
    return contains(root, key);
  }

  /**
   * 从二叉搜索树中根据key查找对应的值
   *
   * @return 查找成功则返回值，否则返回null
   */
  public Value search(Key key) {
    return search(root, key);
  }

  /**
   * 向以node节点为根的二叉搜索树中插入新的元素<br/>
   * 该方法使用了递归算法。
   */
  private Node insert(Node node, Key key, Value value) {
    if (node == null) {
      // 如果node为空，则证明以node为根的二叉搜索树为空
      // 此时直接将新插入的元素当做该二叉搜索树的根，并返回
      // 由于是递归调用，故调用最终会进入该分支中，仅在此处进行count++操作即可
      count++;
      return new Node(key, value);
    }
    int result = key.compareTo(node.key);
    if (result < 0) {
      // 新元素的key<根节点的key，则应该对node的左子树进行递归调用
      node.left = insert(node.left, key, value);
    } else if (result > 0) {
      // 新元素的key>根节点的key，则应该对node的右子树进行递归调用
      node.right = insert(node.right, key, value);
    } else {
      // 新元素的key与根节点的key相同，则将新元素的value赋值给根节点
      node.value = value;
    }
    return node;
  }

  /**
   * 在以node节点为根的二叉搜索树中查找是否存在键为key的元素</br>
   * 该方法使用了递归算法。
   *
   * @return 如果存在的返回true，否则false
   */
  private boolean contains(Node node, Key key) {
    if (node == null) {
      // 此时，该二叉搜索树为空，不包含任何元素
      return false;
    }

    /*if (key.compareTo(node.key) == 0) {
      // 此时查找到了目标元素
      return true;
    } else if (key.compareTo(node.key) < 0) {
      // 此时key可能在左子树中
      return contains(node.left, key);
    } else {
      // 此时key可能在右子树中
      return contains(node.right, key);
    }*/

    // 该运算的结果等同于上述代码的运行结果
    int result = key.compareTo(node.key);
    return result == 0 || (result < 0 ? contains(node.left, key)
        : contains(node.right, key));
  }

  /**
   * 在以node节点为根的二叉搜索树中查找key对应的元素值
   *
   * @return 查找成功则返回其值，否则返回null
   */
  private Value search(Node node, Key key) {
    Value value;
    if (node == null) {
      // 根为空，则证明当前二叉搜索树为空，不包含任何元素
      return null;
    }
    int result = key.compareTo(node.key);
    if (result == 0) {
      // 当前二叉搜索树的根节点即为要查找的节点
      value = node.value;
    } else if (key.compareTo(node.key) < 0) {
      // 此时，要查找的值可能在左子树中
      value = search(node.left, key);
    } else {
      // 此时，要查找的值可能在右子树中
      value = search(node.right, key);
    }
    return value;
  }


}
