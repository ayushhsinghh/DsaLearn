package org.ayushsingh;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<List<Integer>> graph = new ArrayList<>();

    public static class UnionFind {
        int[] parent;
        int[] rank;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
        }

        public int find(int u) {
            if (parent[u] != u) {
                return parent[u] = find(parent[u]);
            }
            return parent[u];
        }

        public void union(int u, int v) {
            int p = find(u);
            int q = find(v);
            if (p == q) return;

            if (rank[p] < rank[q]) {
                parent[p] = q;
            } else if (rank[p] > rank[q]) {
                parent[q] = p;
            } else {
                parent[q] = p;
                rank[p]++;
            }
        }
    }

    public void setGraph(List<List<Integer>> graph) {
        this.graph = graph;
    }

    public List<List<Integer>> getGraph() {
        return graph;
    }

    public int size() {
        return graph.size();
    }
}
