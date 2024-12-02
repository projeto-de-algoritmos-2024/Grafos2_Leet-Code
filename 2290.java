class Solution {
        public int minimumObstacles(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;

            int[][] cost = new int[m][n];
            for (int[] row : cost) {
                Arrays.fill(row, Integer.MAX_VALUE);
            }
            cost[0][0] = 0;

            List<int[]> queue = new LinkedList<>();
            queue.add(new int[]{0, 0, 0});

            int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

            while (!queue.isEmpty()) {
                int minIndex = 0;
                for (int i = 1; i < queue.size(); i++) {
                    if (queue.get(i)[2] < queue.get(minIndex)[2]) {
                        minIndex = i;
                    }
                }
                int[] current = queue.remove(minIndex);

                int x = current[0], y = current[1], currentCost = current[2];

                for (int[] dir : directions) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];


                    if (nx >= 0 && ny >= 0 && nx < m && ny < n) {
                        int newCost = currentCost + grid[nx][ny];

                        if (newCost < cost[nx][ny]) {
                            cost[nx][ny] = newCost;
                            queue.add(new int[]{nx, ny, newCost});
                        }
                    }
                }
            }

            return cost[m - 1][n - 1];
        }
    }

