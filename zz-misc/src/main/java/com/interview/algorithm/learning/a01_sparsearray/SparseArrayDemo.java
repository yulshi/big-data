package com.interview.algorithm.learning.a01_sparsearray;

import java.io.*;
import java.util.Arrays;

/**
 * 当一个数组的大部分元素为0，或者为同一个值的数组时，可以使用
 * 稀疏数组来保存数组
 * <p>
 * 稀疏数组的处理方法：
 * 1）记录数组一共有几行几列，有多少个不同的值
 * 2）把具有不同值的元素的行、列、值记录在一个小规模的数组中，从而
 * 缩小程序的规模
 * row col Value
 * 0   11  11  2       // 原来数组的行数、列数以及有多少个有效值
 * 1   1   2   1       // 第一个值所在的行、列、值
 * 2   2   3   2       // 第二个值所在的行、列、值
 * <p>
 * 二维数组转稀疏数组
 * 1）遍历原始的二维数组，得到有效值的个数sum
 * 2）根据sum创建稀疏数组int[sum+1][3]
 * 3）将二维数组的有效值及其所在的行和列的信息存入稀疏数组
 * <p>
 * 稀疏数组转二维数组
 * 1）先读取稀疏数组的第一行，创建原始的二维数组，int[11][11]
 * 2）在读取稀疏数组后几行的数据，并赋给原始的二维数组
 *
 * @author yulshi
 * @create 2020/02/22 16:56
 */
public class SparseArrayDemo {

  public static void main(String[] args) {

    // 创建一个二维数组
    // 0: 没有棋子 1：黑子 2：蓝子
    int[][] chessArray = new int[11][11];
    chessArray[1][2] = 1;
    chessArray[2][3] = 2;

    // 原始的二维数组
    traverseArray(chessArray);

    int[][] sparseArray = normalToSparse(chessArray);
    System.out.println("~~~~~~~~~~~~~~~~");
    sparseToNormal(sparseArray);

    System.out.println("####### save then load ########");
    save(sparseArray);
    load();

  }

  private static int[][] normalToSparse(int[][] chessArray) {
    // 转成稀疏数组
    // 1) 先遍历所有的有效值个数
    int sum = 0;
    for (int[] row : chessArray) {
      for (int data : row) {
        if (data != 0) {
          sum++;
        }
      }
    }
    System.out.println("sum=" + sum);

    // 2) 创建对应的稀疏数组
    int[][] sparseArray = new int[sum + 1][3];
    sparseArray[0][0] = chessArray.length;
    sparseArray[0][1] = chessArray.length;
    sparseArray[0][2] = sum;

    // 3) 赋值
    int sparseArrayRowNum = 1;
    for (int i = 0; i < chessArray[0].length; i++) {
      for (int j = 0; j < chessArray[i].length; j++) {
        if (chessArray[i][j] != 0) {
          sparseArray[sparseArrayRowNum][0] = i;
          sparseArray[sparseArrayRowNum][1] = j;
          sparseArray[sparseArrayRowNum][2] = chessArray[i][j];
          sparseArrayRowNum++;
        }
      }
    }
    traverseArray(sparseArray);
    return sparseArray;
  }

  private static int[][] sparseToNormal(int[][] sparseArray) {

    traverseArray(sparseArray);

    int rowNum = sparseArray[0][0];
    int colNum = sparseArray[0][1];
    int validValueCount = sparseArray[0][2];

    int[][] arr = new int[rowNum][colNum];
    for (int i = 1; i <= validValueCount; i++) {
      int row = sparseArray[i][0];
      int col = sparseArray[i][1];
      int val = sparseArray[i][2];
      arr[row][col] = val;
    }

    traverseArray(arr);
    return arr;

  }

  private static void save(int[][] sparseArray) {
    try (FileOutputStream fos = new FileOutputStream("map.data")) {

      PrintWriter out = new PrintWriter(fos);
      for (int[] row : sparseArray) {
        StringBuilder sb = new StringBuilder();
        for (int data : row) {
          sb.append(data).append(",");
        }
        String line = sb.toString();
        out.println(line.substring(0, line.length() - 1));
      }
      out.flush();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private static int[][] load() {
    try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(
                    new FileInputStream("map.data")))) {

      String firstLine = reader.readLine();
      String[] fields = firstLine.split(",");
      int totalRow = Integer.parseInt(fields[0]);
      int totalCol = Integer.parseInt(fields[1]);
      int totalCnt = Integer.parseInt(fields[2]);

      int[][] sparseArray = new int[totalCnt + 1][3];
      sparseArray[0][0] = totalRow;
      sparseArray[0][1] = totalCol;
      sparseArray[0][2] = totalCnt;

      for (int i = 1; i <= totalCnt; i++) {
        fields = reader.readLine().split(",");
        int row = Integer.parseInt(fields[0]);
        int col = Integer.parseInt(fields[1]);
        int val = Integer.parseInt(fields[2]);
        sparseArray[i][0] = row;
        sparseArray[i][1] = col;
        sparseArray[i][2] = val;
      }

      traverseArray(sparseArray);
      return sparseArray;

    } catch (IOException e) {
      return null;
    }
  }

  private static void traverseArray(int[][] arr) {
    for (int[] row : arr) {
      for (int data : row) {
        System.out.printf("%d\t", data);
      }
      System.out.println();
    }
  }


}
