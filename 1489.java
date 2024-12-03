class Solution {
    public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
        int m = edges.length;
        int[][] newEdges = new int[m][4];
        for (int i = 0; i < m; i++)
            newEdges[i] = new int[] { edges[i][0], edges[i][1], edges[i][2], i };

        Arrays.sort(newEdges, (a, b) -> a[2] - b[2]);

        UnionFind uf = new UnionFind(n);
        int stdWeight = 0;
        for (int[] e : newEdges)
            if (uf.union(e[0], e[1]))
                stdWeight += e[2];

        List<Integer> critical = new ArrayList<>(), pseudo = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            UnionFind uf1 = new UnionFind(n);
            int w1 = 0;
            for (int[] e : newEdges)
                if (e[3] != newEdges[i][3] && uf1.union(e[0], e[1]))
                    w1 += e[2];

            if (uf1.count > 1 || w1 > stdWeight) {
                critical.add(newEdges[i][3]);
                continue;
            }

            UnionFind uf2 = new UnionFind(n);
            int w2 = newEdges[i][2];
            uf2.union(newEdges[i][0], newEdges[i][1]);

            for (int[] e : newEdges)
                if (e[3] != newEdges[i][3] && uf2.union(e[0], e[1]))
                    w2 += e[2];

            if (w2 == stdWeight)
                pseudo.add(newEdges[i][3]);
        }

        return Arrays.asList(critical, pseudo);
    }

    class UnionFind {
        int[] parent, rank;
        int count;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            count = n;
            for (int i = 0; i < n; i++)
                parent[i] = i;
        }

        int find(int x) {
            return parent[x] == x ? x : (parent[x] = find(parent[x]));
        }

        boolean union(int x, int y) {
            int px = find(x), py = find(y);
            if (px == py)
                return false;
            count--;
            if (rank[px] < rank[py])
                parent[px] = py;
            else if (rank[px] > rank[py])
                parent[py] = px;
            else {
                parent[py] = px;
                rank[px]++;
            }
            return true;
        }
    }
}