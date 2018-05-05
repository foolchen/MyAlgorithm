package com.foolchen.algorithm.tree;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 二分搜索树（Binary Search Tree）<br/>
 * {@link #root}为当前节点的根节点，如果当前节点已经是根节点，则{@link #root}为空。<br/>
 * {@link #count}用于记录以当前节点为根的字数中的节点总数。
 *
 * @author chenchong. Created on 2018/5/2.
 */
public class BST<Key extends Comparable<Key>, Value> {
  /*
   二分搜索树由一个个节点(Node)组成
   二分搜索树不一定为完全二叉树
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

    public Node(Node node) {
      this.key = node.key;
      this.value = node.value;
      this.left = node.left;
      this.right = node.right;
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
   * 判断二分搜索树中是否存在键为key的元素
   *
   * @return 如果存在的返回true，否则false
   */
  public boolean contains(Key key) {
    return contains(root, key);
  }

  /**
   * 从二分搜索树中根据key查找对应的值
   *
   * @return 查找成功则返回值，否则返回null
   */
  public Value search(Key key) {
    return search(root, key);
  }

  /**
   * 对二分搜索树进行前序遍历
   */
  public void preOrder() {
    // 此处为前序遍历的递归算法
    System.out.println("recursive preOrderTraverse start");
    preOrder(root);
    System.out.println("iterative preOrderTraverse start");

    // 此处为前序遍历的非递归算法
    if (root != null) {
      // 先序遍历时，需要先遍历父节点，然后依次遍历其左孩子、右孩子
      // 数据结构栈Stack的特点为先进后出
      Stack<Node> stack = new Stack<>();
      // 由于需要从根开始进行遍历，故首先将根节点push到栈中
      stack.push(root);
      while (!stack.isEmpty()) {
        // 首先取出最后存储的节点，进行访问
        // 如果是第一次迭代，则取出的为根节点
        Node node = stack.pop();
        System.out.println(node.key);

        // 然后按照右孩子、左孩子的顺序，将节点push到栈中
        // 以满足node、node.right、node.left的访问顺序
        if (node.right != null) {
          stack.push(node.right);
        }
        if (node.left != null) {
          stack.push(node.left);
        }
      }
    }
  }

  /**
   * 对二分搜索树进行中序遍历
   */
  public void inOrder() {
    System.out.println("recursive inOrderTraverse start");
    inOrder(root);
    System.out.println("iterative inOrderTraverse start");
    // 访问顺序为左子树、根、右子树
    // 在使用Stack的情况下，需要在没有左孩子的情况下对当前节点（子树的根节点）进行访问，然后对右子树进行访问
    if (root != null) {
      Stack<Node> stack = new Stack<>();
      Node node = root;
      while (node != null || !stack.isEmpty()) {
        if (node != null) {
          // 此时根节点不为空
          stack.push(node);
          // 将左孩子节点赋值给node，如果该node为空，则下次循环时进入另一个分支
          node = node.left;
        } else {
          // 子树的左孩子为空，则直接出栈子树的根节点
          // 然后将其右子树进行有入栈
          Node pop = stack.pop();
          System.out.println(pop.key);
          // 将右孩子赋值给node，如果不为空，则下次循环进入另外一个分支
          // 如果为空，则会pop出上一个子树的节点
          node = pop.right;
        }
      }
    }
  }

  /**
   * 对二分搜索树进行后续遍历
   */
  public void postOrder() {
    postOrder(root);
  }

  /**
   * 对二分搜索树进行层序遍历
   */
  public void levelOrder() {
    if (root != null) {
      LinkedList<Node> q = new LinkedList<Node>();
      q.add(root);

      while (!q.isEmpty()) {
        Node node = q.remove();
        System.out.println(node.key);
        if (node.left != null) {
          q.add(node.left);
        }
        if (node.right != null) {
          q.add(node.right);
        }
      }
    }
  }

  /**
   * 从二分搜索树中查找最小键所在节点
   */
  public Key minimum() {
    if (count != 0) {
      return minimum(root).key;
    } else {
      return null;
    }
  }

  /**
   * 从二分搜索树中删除最小键所在的节点
   */
  public void removeMin() {
    if (root != null) {
      root = removeMin(root);
    }
  }

  /**
   * 从二分搜索树中查找最大键所在节点
   */
  public Key maximum() {
    if (count != 0) {
      return maximum(root).key;
    } else {
      return null;
    }
  }

  /**
   * 从二分搜索树中删除最大键所在的节点
   */
  public void removeMax() {
    if (root != null) {
      root = removeMax(root);
    }
  }

  /**
   * 删除key对应的节点
   */
  public void remove(Key key) {
    root = remove(root, key);
  }

  /**
   * 向以node节点为根的二分搜索树中插入新的元素<br/>
   * 该方法使用了递归算法。
   */
  private Node insert(Node node, Key key, Value value) {
    if (node == null) {
      // 如果node为空，则证明以node为根的二分搜索树为空
      // 此时直接将新插入的元素当做该二分搜索树的根，并返回
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
   * 在以node节点为根的二分搜索树中查找是否存在键为key的元素</br>
   * 该方法使用了递归算法。
   *
   * @return 如果存在的返回true，否则false
   */
  private boolean contains(Node node, Key key) {
    if (node == null) {
      // 此时，该二分搜索树为空，不包含任何元素
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
    return result == 0 || (result < 0 ? contains(node.left, key) : contains(node.right, key));
  }

  /**
   * 在以node节点为根的二分搜索树中查找key对应的元素值
   *
   * @return 查找成功则返回其值，否则返回null
   */
  private Value search(Node node, Key key) {
    Value value;
    if (node == null) {
      // 根为空，则证明当前二分搜索树为空，不包含任何元素
      return null;
    }
    int result = key.compareTo(node.key);
    if (result == 0) {
      // 当前二分搜索树的根节点即为要查找的节点
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

  private void preOrder(Node node) {
    if (node != null) {
      // 在节点不为空的情况下，先访问当前节点
      System.out.println(node.key);
      // 然后依次对左右子树进行前序遍历的递归调用
      preOrder(node.left);
      preOrder(node.right);
    }
  }

  private void inOrder(Node node) {
    if (node != null) {
      // 在节点不为空的情况下，先对左子树进行中序遍历的递归调用
      inOrder(node.left);
      // 然后对当前节点进行访问
      System.out.println(node.key);
      // 最后对右子树进行中序遍历的递归调用
      inOrder(node.right);
    }
  }

  private void postOrder(Node node) {
    if (node != null) {
      // 在节点不为空的情况下，先对左右子树分别进行后序遍历的递归调用
      postOrder(node.left);
      postOrder(node.right);
      // 然后对当前节点进行访问
      System.out.println(node.key);
    }
  }

  // 查找以node为根的子二分搜索树的最小节点
  private Node minimum(Node node) {
    // 由于二分搜索树的左孩子一定比当前节点小，故在某个节点没有左孩子时，该节点一定是树中的最小节点
    if (node.left == null) {
      return node;
    }
    return minimum(node.left);
  }

  // 在以node为根的二分搜索树中删除最小节点，并且返回根节点
  private Node removeMin(Node node) {
    if (node.left == null) {
      // 该节点没有左孩子，则此时该节点即为最小节点
      // 由于二分搜索树中的左子树中的值一定比根节点要小
      // 故在将node从树中断开连接后，如果该node还有右孩子
      // 则直接将这个右孩子替换node当做其根节点的左孩子并不会破坏二分搜索树的结构
      Node rightNode = node.right;
      node.right = null; // 取消node对于right的引用，防止内存泄露
      count--;
      return rightNode;
    } else {
      // 如果node存在左孩子，则当前node并不是最小值
      node.left = removeMin(node.left);
      // 则此时直接返回node，该node还是当前子树的根节点
      return node;
    }
  }

  // 查找以node为根的子二分搜索树的最大节点
  private Node maximum(Node node) {
    // 由于二分搜索树的右孩子一定比当前节点大，故在某个节点没有右孩子时，该节点一定是树中的最大节点
    if (node.right == null) {
      return node;
    }
    return maximum(node.right);
  }

  // 在以node为根的二分搜索树中删除最大节点，并且返回根节点
  private Node removeMax(Node node) {
    if (node.right == null) {
      // 在node没有右孩子的时候，则node就是二分搜索树中的最大值
      // 此时需要将node从二分搜索树中断开
      // 此时如果node还有左孩子，根据二分搜索树的结构特点（右子树中的所有节点都比根节点大），node.left一定比node的父节点要大
      // 则node.left可以直接替代node作为node的父节点的右孩子
      Node leftNode = node.left;
      node.left = null;// 取消node对于left的引用，防止内存泄露
      count--;
      return leftNode;
    } else {
      // 此时node存在右孩子，node不是最大值
      node.right = removeMax(node.right);
      // 则此时直接返回node，该node还是当前子树的根节点
      return node;
    }
  }

  // 在以node为根的二分搜索树中删除key对应的节点
  private Node remove(Node node, Key key) {
    if (node == null) {
      // 如果该二分搜索树为空，则直接中断操作
      return null;
    }

    if (key.compareTo(node.key) < 0) {
      // 要删除的节点在node的左子树中，则对左子树进行递归调用删除
      node.left = remove(node.left, key);
      return node;
    } else if (key.compareTo(node.key) > 0) {
      // 要删除的节点在node的右子树中，则对右子树进行递归调用删除
      node.right = remove(node.right, key);
      return node;
    } else {
      // 此时，node就是要被删除的节点
      if (node.left == null) {
        // 此时仅存在右子树，则在删除后，使右孩子替代node
        Node rightNode = node.right;
        node.right = null;
        count--;
        return rightNode;
      }
      if (node.right == null) {
        // 此时仅存在左子树，则在删除后，使左孩子替代node
        Node leftNode = node.left;
        node.left = null;
        count--;
        return leftNode;
      }
      // 执行到此处时，表明node存在左右孩子，则需要挑选前驱节点（node的左子树中的最大节点）或者后继节点（node的右子树中的最小节点）来替代node
      // 此处使用后继节点
      Node successor = new Node(minimum(node.right)); // 为了防止后继节点被错误释放，此处进行拷贝
      count++; // 由于增加了新的节点，故需要对节点数量进行增加

      // 此处将successor与node的左右孩子进行连接
      successor.left = node.left;
      successor.right = removeMin(node.right);// 此处连接的是删除后继节点后的右子树，防止节点重复

      node.left = node.right = null; // 解除被删除节点的连接，防止内存泄露
      count--;
      // 返回新的节点用于取代node成为该子树的根节点
      return successor;
    }
  }
}
