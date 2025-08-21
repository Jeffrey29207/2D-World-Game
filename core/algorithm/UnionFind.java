package core.algorithm;

public class UnionFind {

    private int[] parent;

    public UnionFind(int N) {
        parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = -1;
        }
    }

    public int parent(int v) {
        return parent[v];
    }

    public boolean isConnected(int a, int b) {
        return find(a) == find(b);
    }

    public int find(int a) {
        if (a >= parent.length || a < 0) {
            throw new IllegalStateException();
        }
        if (parent[a] < 0) {
            return a;
        }
        parent[a] = find(parent[a]);
        return parent[a];
    }

    public void union(int v1, int v2) {
        int r1 = find(v1);
        int r2 = find(v2);
        if (isConnected(r1, r2)) {
            return;
        }
        int p1 = -parent[r1];
        int p2 = -parent[r2];
        if (p1 > p2) {
            parent[r2] = r1;
            parent[r1] = -(p1 + p2);
        } else {
            parent[r1] = r2;
            parent[r2] = -(p1 + p2);
        }
    }
}
