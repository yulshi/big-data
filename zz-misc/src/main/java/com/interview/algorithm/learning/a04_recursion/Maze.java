package com.interview.algorithm.learning.a04_recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 求出最短路径，思路，遍历所有的策略，找到那个走过节点数最少的策略
 *
 @author yulshi
 @create 2020/02/25 08:58
 */
public class Maze {

  public static void main(String[] args) {

    Maze maze = new Maze(new Strategy(
            new Direction[]{
                    Direction.DOWN,
                    Direction.RIGHT,
                    Direction.UP,
                    Direction.LEFT}));
    System.out.println(maze.walk(1, 1));
    maze.print();
    System.out.println("attempted times: " + maze.getAttemptTimes());
    System.out.println("walked paths: " + maze.getNumOfWalkedPath());

  }

  private final Strategy strategy;
  private final int[][] map = new int[8][7];
  private final Counter attemptTimes = new Counter();

  public Maze(Strategy strategy) {
    this.strategy = strategy;
    for (int i = 0; i < map[0].length; i++) {
      map[0][i] = 1;
      map[7][i] = 1;
    }
    for (int i = 0; i < map.length; i++) {
      map[i][0] = 1;
      map[i][6] = 1;
    }
    map[3][1] = 1;
    map[3][2] = 1;
    map[2][2] = 1;
    print();
  }

  /**
   * map[i][j] = 0 : 该点还没有走过
   * map[i][j] = 1 : 墙
   * map[i][j] = 2 : 该点可以走
   * map[i][j] = 1 : 该点已经走过，但是不通
   *
   * 在走迷宫时，需要确定一个策略，我们的策略是 下->右->上->走
   * 如果该点走不通，再回溯
   *
   * @param i
   * @param j
   * @return
   */
  private boolean walk(int i, int j) {

    attemptTimes.increment();

    // 终点已经到达，退出
    if (map[6][5] == 2) {
      return true;
    }

    if (map[i][j] == 0) {

      map[i][j] = 2;
      for (Direction directions : strategy.getDirections()) {
        if (walk(i + directions.row, j + directions.col)) {
          return true;
        }
      }
      map[i][j] = 3;
      return false;
//            if (walk(map, i + 1, j)) { // move down
//                return true;
//            } else if (walk(map, i, j + 1)) { // move right
//                return true;
//            } else if (walk(map, i - 1, j)) { // move up
//                return true;
//            } else if (walk(map, i, j - 1)) { // move left
//                return true;
//            } else {
//                map[i][j] = 3;
//                return false;
//            }
    } else {
      return false;
    }

  }

  public int getAttemptTimes() {
    return attemptTimes.getCount();
  }

  public int getNumOfWalkedPath() {
    int numPaths = 0;
    for (int[] row : map) {
      for (int item : row) {
        if (item > 1) {
          numPaths++;
        }
      }
    }
    return numPaths;
  }

  private void print() {
    for (int row[] : map) {
      for (int item : row) {
        System.out.printf("%d ", item);
      }
      System.out.println();
    }
  }

  private static class Strategy {

    private final Direction[] directions;

    public Strategy(Direction[] directions) {
      this.directions = directions;
    }

    public Direction[] getDirections() {
      return directions;
    }
  }

  private static final class Direction {

    public static final Direction UP = new Direction(-1, 0);
    public static final Direction DOWN = new Direction(1, 0);
    public static final Direction LEFT = new Direction(0, -1);
    public static final Direction RIGHT = new Direction(0, 1);

    private final int row;
    private final int col;

    private Direction(int row, int col) {
      this.row = row;
      this.col = col;
    }

    public static Direction[] all() {
      return new Direction[]{UP, DOWN, LEFT, RIGHT};
    }
  }

  private static class Counter {
    private int count;

    public Counter() {
      this.count = 0;
    }

    public void increment() {
      count++;
    }

    public int getCount() {
      return count;
    }
  }

  private static Strategy[] createStrategies() {

    Direction[] directions = Direction.all();

    for (int i = 0; i < directions.length; i++) {

    }

    return null;

  }

}

