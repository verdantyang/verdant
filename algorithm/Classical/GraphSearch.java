package Classical;

/**
 * 图遍历算法
 *
 * @author verdant
 * @since 2016/08/25
 */
public class GraphSearch {
    /*
    * 深度优先搜索遍历图的递归实现
    */
    private void DFS(AdjacencyMatrix matrix, int i, boolean[] visited) {

        visited[i] = true;
        System.out.printf("%c ", matrix.mVexs[i]);
        // 遍历该顶点的所有邻接顶点。若是没有访问过，那么继续往下走
        for (int w = matrix.firstVertex(i); w >= 0; w = matrix.nextVertex(i, w)) {
            if (!visited[w])
                DFS(matrix, w, visited);
        }
    }

    /*
     * 深度优先搜索遍历图
     */
    public void DFS(AdjacencyMatrix matrix) {
        boolean[] visited = new boolean[matrix.mVexs.length];       // 顶点访问标记

        // 初始化所有顶点都没有被访问
        for (int i = 0; i < matrix.mVexs.length; i++)
            visited[i] = false;

        System.out.printf("DFS: ");
        for (int i = 0; i < matrix.mVexs.length; i++) {
            if (!visited[i])
                DFS(matrix, i, visited);
        }
        System.out.printf("\n");
    }

    /*
     * 广度优先搜索（类似于树的层次遍历）
     */
    public void BFS(AdjacencyMatrix matrix) {
        int head = 0;
        int rear = 0;
        int[] queue = new int[matrix.mVexs.length];            // 辅组队列
        boolean[] visited = new boolean[matrix.mVexs.length];  // 顶点访问标记

        for (int i = 0; i < matrix.mVexs.length; i++)
            visited[i] = false;

        System.out.printf("BFS: ");
        for (int i = 0; i < matrix.mVexs.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                System.out.printf("%c ", matrix.mVexs[i]);
                queue[rear++] = i;  // 入队列
            }

            while (head != rear) {
                int j = queue[head++];  // 出队列
                for (int k = matrix.firstVertex(j); k >= 0; k = matrix.nextVertex(j, k)) { //k是为访问的邻接顶点
                    if (!visited[k]) {
                        visited[k] = true;
                        System.out.printf("%c ", matrix.mVexs[k]);
                        queue[rear++] = k;
                    }
                }
            }
        }
        System.out.printf("\n");
    }

}
