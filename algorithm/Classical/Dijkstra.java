package Classical;


public class Dijkstra {
    private static int INF = Integer.MAX_VALUE;

    /*
     * Dijkstra最短路径。
     *     统计图中"顶点vs"到其它各个顶点的最短路径。
     *
     * 参数说明：
     *     vs -- 起始顶点(start vertex)。即计算"顶点vs"到其它顶点的最短路径。
     *     prev -- 前驱顶点数组。即，prev[i]的值是"顶点vs"到"顶点i"的最短路径所经历的全部顶点中，位于"顶点i"之前的那个顶点。
     *     dist -- 长度数组。即，dist[i]是"顶点vs"到"顶点i"的最短路径的长度。
     */
    public static void dijkstra(AdjacencyMatrix matrix, int vs, int[] prev, int[] dist) {
        // flag[i]=true表示"顶点vs"到"顶点i"的最短路径已成功获取
        boolean[] flag = new boolean[matrix.mVexs.length];

        // 初始化
        for (int i = 0; i < matrix.mVexs.length; i++) {
            flag[i] = false;          // 顶点i的最短路径还没获取到。
            prev[i] = 0;              // 顶点i的前驱顶点为0。
            dist[i] = matrix.mMatrix[vs][i];  // 顶点i的最短路径为"顶点vs"到"顶点i"的权。
        }

        // 对"顶点vs"自身进行初始化
        flag[vs] = true;
        dist[vs] = 0;

        // 遍历mVexs.length-1次；每次找出一个顶点的最短路径。
        int k = 0;
        for (int i = 1; i < matrix.mVexs.length; i++) {
            // 寻找当前最小的路径；
            // 即，在未获取最短路径的顶点中，找到离vs最近的顶点(k)。
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < matrix.mVexs.length; j++) {
                if (flag[j] == false && dist[j] < min) {
                    min = dist[j];
                    k = j;
                }
            }
            // 标记"顶点k"为已经获取到最短路径
            flag[k] = true;

            // 修正当前最短路径和前驱顶点
            // 即，当已经"顶点k的最短路径"之后，更新"未获取最短路径的顶点的最短路径和前驱顶点"。
            for (int j = 0; j < matrix.mVexs.length; j++) {
                int tmp = (matrix.mMatrix[k][j] == Integer.MAX_VALUE ? Integer.MAX_VALUE : (min + matrix.mMatrix[k][j]));
                if (flag[j] == false && (tmp < dist[j])) {
                    dist[j] = tmp;
                    prev[j] = k;
                }
            }
        }

        // 打印dijkstra最短路径的结果
        System.out.printf("dijkstra(%c): \n", matrix.mVexs[vs]);
        for (int i = 0; i < matrix.mVexs.length; i++)
            System.out.printf("  shortest(%c, %c)=%d\n", matrix.mVexs[vs], matrix.mVexs[i], dist[i]);
    }

    public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int matrix[][] = {
                 /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
          /*A*/ {0, 12, INF, INF, INF, 16, 14},
          /*B*/ {12, 0, 10, INF, INF, 7, INF},
          /*C*/ {INF, 10, 0, 3, 5, 6, INF},
          /*D*/ {INF, INF, 3, 0, 4, INF, INF},
          /*E*/ {INF, INF, 5, 4, 0, 2, 8},
          /*F*/ {16, 7, 6, INF, 2, 0, 9},
          /*G*/ {14, INF, INF, INF, 8, 9, 0}};
        AdjacencyMatrix pG;

        // 自定义"图"(输入矩阵队列)
        //pG = new MatrixUDG();
        // 采用已有的"图"
        pG = new AdjacencyMatrix(vexs, matrix);

        int[] prev = new int[pG.mVexs.length];
        int[] dist = new int[pG.mVexs.length];
        // dijkstra算法获取"第4个顶点"到其它各个顶点的最短距离
        Dijkstra.dijkstra(pG, 3, prev, dist);
    }
}
