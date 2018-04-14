package com.foolchen.algorithm.sort;

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

  public static int[] generateRandomArray(int n, int rangeL, int rangeR) {
    assert rangeL <= rangeR;

    int[] arr = new int[n];

    for (int i = 0; i < n; i++) {
      arr[i] = (int) (Math.random() * (rangeR - rangeL + 1) + rangeL);
    }
    return arr;
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
      Method method = clazz.getMethod(methodName, int[].class);
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
}