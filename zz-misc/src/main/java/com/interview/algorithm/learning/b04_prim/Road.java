package com.interview.algorithm.learning.b04_prim;

import java.util.Arrays;

/**
 * 使用普里姆算法解决修路问题
 *
 @author yulshi
 @create 2020/03/04 08:58
 */
public class Road {


  public static void main(String[] args) {
    char[] data = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
    int vertexCount = data.length;
    int[][] weight = new int[][]{  // 使用10000代表两个顶点不能联通
            // A,   B, C, D,     E,     F,     G
            {10000, 5, 7, 10000, 10000, 10000, 2},     // A
            {5, 10000, 10000, 9, 10000, 10000, 3},     // B
            {7, 10000, 10000, 10000, 8, 10000, 10000}, // C
            {10000, 9, 10000, 10000, 10000, 4, 10000}, // D
            {10000, 10000, 8, 10000, 10000, 5, 4},     // E
            {10000, 10000, 10000, 4, 5, 10000, 6},     // F
            {2, 3, 10000, 10000, 4, 6, 10000}          // G
    };

    MGraph graph = new MGraph(vertexCount);

    MinTree minTree = new MinTree();
    minTree.createGraph(graph, vertexCount, data, weight);
    minTree.showGraph(graph);
    System.out.println("~~~~~~~~~~~");
    minTree.prim(graph, 0);
  }

}

/**
 * 最小生成树
 */
class MinTree {

  public void createGraph(MGraph graph, int vertexCount, char[] data, int[][] weight) {
    int i, j;
    for (i = 0; i < vertexCount; i++) {
      graph.data[i] = data[i]; // 顶点
      for (j = 0; j < vertexCount; j++) {
        graph.weight[i][j] = weight[i][j];
      }
    }
  }

  public void showGraph(MGraph graph) {
    for (int[] row : graph.weight) {
      System.out.println(Arrays.toString(row));
    }
  }

  /**
   * 得到最小生成树
   * @param graph
   * @param v 表示从图的第几个顶点开始生成
   */
  public void prim(MGraph graph, int v) {
    // 标记顶点是否被访问过
    int[] visited = new int[graph.vertexCount];
    // 把当前节点标记成已访问
    visited[v] = 1;
    // 记录相邻顶点距离最近的两个下标
    int h1 = -1;
    int h2 = -1;
    int minWeight = 10000;
    for (int k = 1; k < graph.vertexCount; k++) {
      // 有k个顶点，经过prim算法，会有k-1条边
      for (int i = 0; i < graph.vertexCount; i++) {
        for (int j = 0; j < graph.vertexCount; j++) {
          if (visited[i] == 1 && visited[j] == 0
                  && graph.weight[i][j] < minWeight) {
            minWeight = graph.weight[i][j];
            h1 = i;
            h2 = j;
          }
        }
      }
      // 找到了一条权重最小的边
      System.out.println("边<" + graph.data[h1] + "," + graph.data[h2]
              + "> 权值：" + minWeight);
      visited[h2] = 1;
      minWeight = 10000;
    }
  }

}

/**
 * 图
 */
class MGraph {

  final int vertexCount; // 图的节点的个数
  final char[] data;     // 存放节点数据
  final int[][] weight;  // 邻接矩阵，用来存放边及其权重

  public MGraph(int vertexCount) {
    this.vertexCount = vertexCount;
    data = new char[this.vertexCount];
    weight = new int[this.vertexCount][this.vertexCount];
  }

}
