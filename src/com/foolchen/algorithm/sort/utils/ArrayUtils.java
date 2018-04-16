package com.foolchen.algorithm.sort.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author chenchong. Created on 2018/4/14.
 */

public class ArrayUtils {
  public static void swap(int[] arr, int i, int j) {
    int t = arr[i];
    arr[i] = arr[j];
    arr[j] = t;
  }

  public static void swap(Integer[] arr, int i, int j) {
    Integer t = arr[i];
    arr[i] = arr[j];
    arr[j] = t;
  }

  /**
   * 生成长度为n的随机数组
   *
   * @param n 数组的长度
   * @param rangeL 数组内元素的最小值
   * @param rangeR 数组内元素的最大值
   * @return 生成的数组
   */
  public static int[] generateRandomArray(int n, int rangeL, int rangeR) {
    assert rangeL <= rangeR;

    int[] arr = new int[n];

    for (int i = 0; i < n; i++) {
      arr[i] = (int) (Math.random() * (rangeR - rangeL + 1) + rangeL);
    }
    return arr;
  }

  /**
   * 生成长度为n的近乎有序的数组<br/>
   * 该方法的本质为生成一个有序数组，然后交换swapTimes次
   *
   * @param n 数组的长度
   * @param swapTimes 生成有序数组后，随机交换的次数
   * @return 生成的数组
   */
  public static int[] generateNearlyOrderedArray(int n, int swapTimes) {
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) {
      arr[i] = i;
    }
    for (int i = 0; i < swapTimes; i++) {
      int a = (int) (Math.random() * n);
      int b = (int) (Math.random() * n);
      swap(arr, a, b);
    }
    return arr;
  }

  /**
   * 生成一个长度为n的完全倒序的数组
   *
   * @param n 数组的长度
   * @return 生成的数组
   */
  public static int[] generateReversedArray(int n) {
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) {
      arr[i] = n - i;
    }
    return arr;
  }

  public static void testSort(Class clazz, String methodName, int[] arr) {
    testSort(clazz.getName(), methodName, arr);
  }

  public static void testSort(Class clazz, String methodName, int[] arr, int[] result) {
    testSort(clazz.getName(), methodName, arr, result);
  }

  /**
   * 测试算法性能
   *
   * @param className 要测试的类
   * @param methodName 要测试的方法名
   * @param arr 要测试的数组
   */
  public static void testSort(String className, String methodName, int[] arr) {
    try {
      Class<?> clazz = Class.forName(className);
      Method method = clazz.getDeclaredMethod(methodName, int[].class);
      method.setAccessible(true); // 访问私有方法
      // 构建参数列表，此处只有一个参数arr
      Object[] params = new Object[] {arr};
      long start = System.currentTimeMillis();
      method.invoke(null, params);
      long end = System.currentTimeMillis();
      System.out.println(className + "." + methodName + ": " + (end - start) + "ms");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  /**
   * 测试算法性能
   *
   * @param className 要测试的类
   * @param methodName 要测试的方法名
   * @param arr 要测试的数组
   */
  public static void testSort(String className, String methodName, int[] arr, int[] result) {
    try {
      Class<?> clazz = Class.forName(className);
      Method method = clazz.getDeclaredMethod(methodName, int[].class, int[].class);
      method.setAccessible(true); // 访问私有方法
      // 构建参数列表，此处只有一个参数arr
      Object[] params = new Object[] {arr, result};
      long start = System.currentTimeMillis();
      method.invoke(null, params);
      long end = System.currentTimeMillis();
      System.out.println(className + "." + methodName + ": " + (end - start) + "ms");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}