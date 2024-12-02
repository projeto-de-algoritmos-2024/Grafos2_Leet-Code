class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        int[][] graph = new int[n + 1][n + 1];
        for (int[] row : graph) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        for (int[] time : times) {
            graph[time[0]][time[1]] = time[2];
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[] { k, 0 });

        boolean[] processed = new boolean[n + 1];

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0];
            int time = curr[1];

            if (processed[node])
                continue;
            processed[node] = true;

            for (int neighbor = 1; neighbor <= n; neighbor++) {
                if (graph[node][neighbor] != Integer.MAX_VALUE) {
                    int newTime = time + graph[node][neighbor];
                    if (newTime < dist[neighbor]) {
                        dist[neighbor] = newTime;
                        pq.offer(new int[] { neighbor, newTime });
                    }
                }
            }
        }

        int maxTime = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                return -1;
            }
            maxTime = Math.max(maxTime, dist[i]);
        }

        return maxTime;

    }
}
