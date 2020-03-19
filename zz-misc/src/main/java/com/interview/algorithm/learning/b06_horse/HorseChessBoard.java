package com.interview.algorithm.learning.b06_horse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;;

/**
 @author yulshi
 @create 2020/03/04 16:43
 */
public class HorseChessBoard {

  private static int X = 8; // 棋盘的列数
  private static int Y = 8; // 棋盘的行数

  // 创建一个数组，标记棋盘的各个位置是否被访问过
  private static boolean visited[];
  // 标记是否棋盘的所有位置都被访问过了
  private static boolean finished;

  public static void main(String[] args) {

    X = 8;
    Y = 8;
    int row = 3; // 马儿初始位置的行，从1开始编号
    int col = 5; // 马儿初始位置的列，从1开始编号

    // 创建棋盘
    int[][] chessBoard = new int[X][Y];
    visited = new boolean[X * Y];

    long start = System.currentTimeMillis();
    traverseChessBoard(chessBoard, row - 1, col - 1, 1);
    long diff = System.currentTimeMillis() - start;
    System.out.println("Time consuming: " + diff + "ms");

    for (int[] rows : chessBoard) {
      for (int data : rows) {
        System.out.printf("%-5d", data);
      }
      System.out.println();
    }

  }

  /**
   * 完成骑士周游问题的算法实现
   * @param chessBoard
   * @param row 马儿当前位置的行，从0开始计算
   * @param col 马儿当前位置的列，从0开始计算
   * @param step 马儿当前走的第几步，初始位置就是第1步
   */
  public static void traverseChessBoard(int[][] chessBoard, int row, int col, int step) {
    chessBoard[row][col] = step;
    //System.out.println("Step: " + step);
    visited[row * X + col] = true;
    // 获取当前位置可以走的下一个位置的集合
    List<Point> nextPoints = next(new Point(col, row));
    // 这里使用贪心算法，如果去掉这一行代码，速度会变得很慢
    sort(nextPoints);
    while (!nextPoints.isEmpty()) {
      Point p = nextPoints.remove(0); // 取出下一个可以走的位置
      // 判断该点是否已经访问过
      if (!visited[p.y * X + p.x]) {
        traverseChessBoard(chessBoard, p.y, p.x, step + 1);
      }
    }
    // 判断马儿是否完成了任务
    if (step < X * Y && !finished) {
      // 没有走完
      chessBoard[row][col] = 0;
      visited[row * X + col] = false;
    } else {
      finished = true;
    }
  }

  /**
   * 根据当前的位置，计算马儿还能走那些位置
   * @param currPoint
   * @return
   */
  public static List<Point> next(Point currPoint) {
    ArrayList<Point> points = new ArrayList<>();
    Point p1 = new Point();
    if ((p1.x = currPoint.x - 2) >= 0 && (p1.y = currPoint.y - 1) >= 0) {
      points.add(new Point(p1));
    }
    if ((p1.x = currPoint.x - 1) >= 0 && (p1.y = currPoint.y - 2) >= 0) {
      points.add(new Point(p1));
    }
    if ((p1.x = currPoint.x + 1) < X && (p1.y = currPoint.y - 2) >= 0) {
      points.add(new Point(p1));
    }
    if ((p1.x = currPoint.x + 2) < X && (p1.y = currPoint.y - 1) >= 0) {
      points.add(new Point(p1));
    }
    if ((p1.x = currPoint.x + 2) < X && (p1.y = currPoint.y + 1) < Y) {
      points.add(new Point(p1));
    }
    if ((p1.x = currPoint.x + 1) < X && (p1.y = currPoint.y + 2) < Y) {
      points.add(new Point(p1));
    }
    if ((p1.x = currPoint.x - 1) >= 0 && (p1.y = currPoint.y + 2) < Y) {
      points.add(new Point(p1));
    }
    if ((p1.x = currPoint.x - 2) >= 0 && (p1.y = currPoint.y + 1) < Y) {
      points.add(new Point(p1));
    }
    return points;
  }

  /**
   * 根据当前这一步的所有下一步的选择位置，进行非递减排序，减少回溯次数
   * @param ps
   */
  public static void sort(List<Point> ps) {
    ps.sort((p1, p2) -> {
      int count1 = next(p1).size();
      int count2 = next(p2).size();
      if (count1 < count2) {
        return -1;
      } else if (count1 == count2) {
        return 0;
      } else {
        return 1;
      }
    });
  }
}
