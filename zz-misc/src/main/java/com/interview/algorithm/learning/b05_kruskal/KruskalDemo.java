package com.interview.algorithm.learning.b05_kruskal;

import java.util.Arrays;

/**
 @author yulshi
 @create 2020/03/04 11:18
 */
public class KruskalDemo {

  private int edgeNum; // 边的个数
  private char[] vertexs; // 顶点的数组
  private int[][] matrix; // 邻接矩阵
  // 使用INF表示两个顶点之间不能直接联通
  private static final int INF = Integer.MAX_VALUE;

  public static void main(String[] args) {

    char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
    int[][] matrix = {
            //A, B, C, D, E, F, G
            {0, 12, INF, INF, INF, 16, 14}, // A
            {12, 0, 10, INF, INF, 7, INF},  // B
            {INF, 10, 0, 3, 5, 6, INF},     // C
            {INF, INF, 3, 0, 4, INF, INF},  // D
            {INF, INF, 5, 4, 0, 2, 8},      // E
            {16, 7, 6, INF, 2, 0, 9},       // F
            {14, INF, INF, INF, 8, 9, 0}    // G
    };

    KruskalDemo kruskal = new KruskalDemo(vertexs, matrix);
    kruskal.kruskal();



  }

  public KruskalDemo(char[] vertexs, int[][] matrix) {

    // 拷贝传入的数组
    int vlen = vertexs.length;
    this.vertexs = Arrays.copyOf(vertexs, vlen);
    this.matrix = new int[vlen][vlen];
    for (int i = 0; i < vlen; i++) {
      for (int j = 0; j < vlen; j++) {
        this.matrix[i][j] = matrix[i][j];
      }
    }

    // 统计边
    for (int i = 0; i < vlen; i++) {
      for (int j = i + 1; j < vlen; j++) {
        if (this.matrix[i][j] != INF) {
          edgeNum++;
        }
      }
    }
  }

  public void print() {
    System.out.println("邻接矩阵：");
    for (int i = 0; i < vertexs.length; i++) {
      for (int j = 0; j < vertexs.length; j++) {
        System.out.printf("%-6s", matrix[i][j] == INF ?
                "INF" : Integer.toString(matrix[i][j]));
      }
      System.out.println();
    }
  }

  /**
   * 执行克鲁斯卡尔算法
   */
  public void kruskal() {
    int index = 0; // 表示最后结果数组的索引
    // 保存"已有最小生成树"中的每个顶点在最小生成树中的终点
    int[] ends = new int[edgeNum];
    // 创建结果数组，保存最终的最小生成树
    EdgeData[] results = new EdgeData[vertexs.length - 1];

    // 获取图中所有的边的集合
    EdgeData[] edges = getEdges();
    System.out.println("图一共有" + edges.length + "条边");

    // 按照边的权值从小到大进行排序
    sortEdges(edges);

    // 遍历edges，将边添加到最小生成树时，判断准备加入的边是否构成了回路
    // 如果没有，就加入，否则不能加入
    for (int i = 0; i < edgeNum; i++) {
      // 获取到第i条边的第一个顶点
      int p1 = getPosition(edges[i].start);
      // 获取第二个顶点
      int p2 = getPosition(edges[i].end);
      // 获取p1这个顶点在已有的最小生成树中的终点
      int m = getEnd(ends, p1);
      // 获取p2这个顶点在已有的最小生成树中的终点
      int n = getEnd(ends, p2);
      // 是否构成回路
      if (m != n) { // 不构成回路
        ends[m] = n; // 设置m在"已有最小生成树"中的终点
        results[index++] = edges[i];
      }
    }

    // 输出"最小生成树"
    System.out.println("最小生成树");
    Arrays.asList(results).forEach(System.out::println);
  }

  /**
   * 使用冒泡法排序边
   */
  private void sortEdges(EdgeData[] edges) {
    for (int i = 0; i < edges.length - 1; i++) {
      for (int j = 0; j < edges.length - 1 - i; j++) {
        if (edges[j].weight > edges[j + 1].weight) {
          EdgeData temp = edges[j];
          edges[j] = edges[j + 1];
          edges[j + 1] = temp;
        }
      }
    }
  }

  /**
   * 根据顶点的值返回其在邻接矩阵中的下标
   * @param vertex
   * @return
   */
  private int getPosition(char vertex) {
    for (int i = 0; i < vertexs.length; i++) {
      if (vertexs[i] == vertex) {
        return i;
      }
    }
    return -1;
  }

  /**
   * 通过邻接矩阵获取图中的边，放到数组中
   * @return
   */
  private EdgeData[] getEdges() {
    int index = 0;
    EdgeData[] edges = new EdgeData[edgeNum];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = i + 1; j < matrix[i].length; j++) {
        if (matrix[i][j] != INF) {
          edges[index++] = new EdgeData(vertexs[i], vertexs[j], matrix[i][j]);
        }
      }
    }
    return edges;
  }

  /**
   * 获取下标为i的顶点的终点，用于判断两个顶点的终点是否相同
   * @param ends
   * @param i
   * @return
   */
  private int getEnd(int[] ends, int i) {
    while (ends[i] != 0) {
      i = ends[i];
    }
    return i;
  }

}

/**
 * 边
 */
class EdgeData {
  final char start; // 边的起点
  final char end;   // 边的终点
  final int weight; // 边的权值

  public EdgeData(char start, char end, int weight) {
    this.start = start;
    this.end = end;
    this.weight = weight;
  }

  @Override
  public String toString() {
    return "EdgeData{" +
            "<" + start +
            ", " + end +
            "> weight=" + weight +
            '}';
  }
}