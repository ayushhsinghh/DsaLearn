package org.ayushsingh;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

public class GraphQuestions {

    public static Graph getGraph() {
        Graph graph = new Graph();
        int V = 5;
        List<List<Integer>> adj = new ArrayList<>();

        // Initialize adjacency list
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        // Define edges (undirected, non-cyclic)
        int[][] edges = { {0, 1}, {1, 2}, {3, 4}, {4, 4} };

        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
        //    adj.get(e[1]).add(e[0]);
        }

        graph.setGraph(adj);
        return graph;
    }


    public static void dfs(List<List<Integer>> graph,int start, boolean[] visited) {
        visited[start] = true;

        for(int i: graph.get(start)) {
            if(!visited[i]) {
                dfs(graph,i,visited);
            }
        }
    }

    public static void dfsI(List<List<Integer>> graph,int start, boolean[] visited) {
        Deque<Integer> st = new ArrayDeque<>();
        st.push(start);
        visited[start] = true;
        int top;

        while (!st.isEmpty()) {
            top = st.pop();
            visited[top] = true;
            System.out.print(top + " ");
            for(int i: graph.get(top)) {
                if(!visited[i]) {
                    visited[i] = true;
                    st.push(i);
                }
            }
        }
    }

    public static void bfs(List<List<Integer>> graph, int start) {
        Deque<Integer> st = new ArrayDeque<>();
        boolean[] visited = new boolean[graph.size()];
        st.offer(start);
        visited[start] = true;
        int first;
        while (!st.isEmpty()) {
            first = st.poll();
            System.out.print(first + " ");
            for(int i: graph.get(first)) {
                if(!visited[i]) {
                    visited[i] = true;
                    st.offer(i);
                }
            }
        }
    }

    public static boolean hasPathdfs(List<List<Integer>> graph, int start, int end,  boolean[] visited) {
        if(start == end) return true;
        visited[start] = true;

        for(int node : graph.get(start)) {
            if(!visited[node]) {
                visited[node] = true;
                if(hasPathdfs(graph, node, end, visited)) return true;
            }
        }
        return false;
    }

    public static boolean hasPathbfs(List<List<Integer>> graph, Integer start, Integer end) {
        boolean[] visited = new boolean[graph.size()];
        Deque<Integer> st = new ArrayDeque<>();
        st.offer(start);
        visited[start] = true;
        Integer first;

        while (!st.isEmpty()) {
            first = st.poll();
            System.out.println(first + " ");
            if(Objects.equals(first, end)) return true;

            for (int node : graph.get(first)) {
                if (!visited[node]) {
                    visited[node] = true;
                    st.offer(node);
                }
            }
        }

        return false;
    }

    /*
    * It goes deep into the graph marking nodes visited(DFS).
    * If it ever finds a visited node that isn’t its parent, a cycle exists.
    * */
    public static boolean hasCycleUd(List<List<Integer>> graph, int start, int parent, boolean[] visited) {
        visited[start] = true;

        for(int node : graph.get(start)) {
            if(!visited[node]) {
                if (hasCycleUd(graph, node, start, visited)) {
                    return true;
                }
            } else {
                if(node != parent) return true;
            }
        }
        return false;
    }

    /*
    * It tracks the recursion path using the state array. {0,1,2}
    * If during DFS you revisit a node that’s still being visited (state = 1), you’ve found a cycle in a directed graph.
    *  */
    public static boolean hasCycleD(List<List<Integer>> graph, int start, int[] state) {
        state[start] = 1; // visiting

        for(int node : graph.get(start)) {
            if(state[node] == 0) {
                boolean test = hasCycleD(graph, node, state);
                if(test) return true;
            } else if(state[node] == 1) {
                return true;
            }
        }
        state[start] = 2;
        return false;
    }

    public static int shortestPathLengthBfs(List<List<Integer>> graph, int start, int end) {
        boolean[] visited = new boolean[graph.size()];
        int[] distance = new int[graph.size()];
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(start);
        distance[start] = 0;
        int first;

        while (!q.isEmpty()) {
            first = q.poll();
            int size = graph.get(first).size();
            int i = 0;

            if(first == end) return distance[first];

            while (i < size) {
                if(!visited[graph.get(first).get(i)]) {
                    visited[graph.get(first).get(i)] = true;
                    q.offer(graph.get(first).get(i));
                    distance[graph.get(first).get(i)] = distance[first] + 1;
                }
                i++;
            }
        }
        return -1;
    }

    // Basic Logic is
    // 1. Maintain two “frontiers” (s1, s2)
    // 2. Always expand the smaller one
    // 3. Meet in the middle
    public static int shortestPathBiBFS(List<List<Integer>> graph, int start, int end) {
        boolean[] visited = new boolean[graph.size()];
        int distance = 0;
        Set<Integer> s1 = new HashSet<>();
        Set<Integer> s2 = new HashSet<>();

        s1.add(start);
        s2.add(end);

        while (!s1.isEmpty() && !s2.isEmpty()) {

            if(s1.size() >  s2.size()) {
                var tmp = s1;
                s1 = s2;
                s2 = tmp;
            }

            Set<Integer> nextL = new HashSet<>();
            for(Integer n : s1) {
                for (Integer node : graph.get(n)) {
                    if (s2.contains(node)) {
                        return distance + 1;
                    }
                    if (!visited[node]) {
                        visited[node] = true;
                        nextL.add(node);
                    }
                }
            }
            distance++;
            s1 = nextL;
        }

        return -1;
    }

//    In multi-source BFS, you start from multiple source nodes at once, all treated as being at distance 0.
//    All of them are enqueued initially together.
//    🧠 Think of it like dropping multiple pebbles in a pond at once — all waves expand simultaneously.
    public static int multiSourceBFS(List<List<Integer>> graph, List<Integer> start, int end) {
        boolean[] visited = new boolean[graph.size()];
        int distance = 0;
        Deque<Integer> q = new ArrayDeque<>();

        for(int node : start) {
            q.offer(node);
            visited[node] = true;
        }

        while (!q.isEmpty()) {
            int size = q.size();
            for(int i = 0; i < size; i++) {
                int node = q.poll();

                if(node == end) return distance;

                for(Integer n : graph.get(node)) {
                    if (!visited[n]) {
                        visited[n] = true;
                        q.offer(n);
                    }
                }
            }
            distance++;
        }


        return distance;
    }

    // You start from a node, and using DFS or BFS, you can explore all nodes reachable from it.
    //Each time you start a new DFS/BFS from an unvisited node →
    //that means you’ve found a new connected component.
    public static int connectedComponentDFS(List<List<Integer>> graph, int n) {
           boolean[] visited = new boolean[n+1];
           int count = 0;

           for(int i = 0; i < n; i++) {
               if(!visited[i]) {
                   dfs(graph, i, visited);
                   count++;
               }
           }
           return count;
    }

    // Topological Sort using BFS (Kahn's Algorithm)
    // Calculate InDegree of each Element
    // Add all elements of Indegree=0 to queue.
    // After we poll the element from queue, explore its neighbours
    // For every neighbour decrease the indegree by 1
    // if the indegree of neighbour is 0 add it to the queue.
    // In the end your have the topological sort( the order in which elements have been pooled from the queue.)
    public boolean courseSchedule(int numCourses, int[][] prerequisites) {
        int ans = 0;
        int[] inD = new int[numCourses];
        List<List<Integer>> adL = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adL.add(new ArrayList<>());
        }

        for (int[] arr : prerequisites) {
            adL.get(arr[1]).add(arr[0]);
            inD[arr[0]]++;
        }
        Deque<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inD[i] == 0) q.offer(i);
        }

        while (!q.isEmpty()) {
            int f = q.poll();
            ans++;

            for (int n : adL.get(f)) {
                inD[n]--;
                if (inD[n] == 0) {
                    q.offer(n);
                }

            }
        }
        return ans == numCourses;
    }
    // The Above Problem can Also be solved using DFS, by just checking for Cycle in Directed Graph.
    // Use the 3 visiting state way to solve using DFS Like (check hasCycleD function above)


    //Union Find on Number of Islands problem
    // UF only works on UnDirected Graph when Problem asks for following
    // You just care about connected components or detecting cycles in that undirected structure.
    // This Problem shows how to find connected components in a grid
    public int numIslands(char[][] grid) {
        int row = grid.length, col = grid[0].length;
        Graph.UnionFind uf = new Graph.UnionFind(row*col);
        int[][] dirs = new int[][] { {1,0},{0,1},{-1,0},{0,-1} };
        int count = 0;
        int x,y;

        for(int i = 0; i< row;i++) {
            for(int j = 0; j < col; j++) {
                if(grid[i][j] == '1') {
                    count++;
                    for(int[] dir : dirs) {
                        x = i + dir[0];
                        y = j + dir[1];
                        if(x>=0 && y>=0 && x<row && y < col && grid[x][y] == '1') {
                            int idx = i * col + j;
                            int idy = x * col + y;
                            int p1 = uf.find(idx);
                            int p2 = uf.find(idy);

                            if(p1 != p2) {
                                uf.union(p1, p2);
                                count--;
                            }
                        }
                    }
                }
            }
        }

        return count;
    }


    // Detecting Cycle Using Union Find.
    // For an undirected graph, a cycle exists if:
    // When we process an edge (u, v), both u and v are already in the same connected component (i.e., they have the same parent).
    // Given edges = [[u1,v1],[u2,v2],...]:
    // Initialize Union-Find for all nodes.
    // For each edge (u, v):
    // If find(u) == find(v) → cycle detected ✅
    //  (both belong to same set → connecting them would form a loop)
    //  Else, union(u, v).
    public boolean hasCycle(int n, int[][] edges) {
        Graph.UnionFind uf = new Graph.UnionFind(n);
        for (int[] e : edges) {
            if(uf.find(e[0]) == uf.find(e[1])) {
                return true; // same set → cycle
            } else {
                uf.union(e[0], e[1]);
            }
        }
        return false;
    }

    //Dijkstra's Algorithm
    //Bellman Ford Algorithm

    //Prim's Algorithm
    //Kruskal's Algorithm
    // Eulerian Problem
    // Strongly Connected Components
    // Lowest common ancestor of DAG & Shortest common Ancestral path
    // Travelling sales problem
    // Graph Coloring







    public static void main(String[] args) {
        Graph gf = getGraph();
        Graph.UnionFind uf = new Graph.UnionFind(5);
        boolean[] visited = new boolean[gf.size()];
        int[] state = new int[gf.size()];

        int connectedComponentDFS = connectedComponentDFS(gf.getGraph(), 4);
        System.out.println(connectedComponentDFS);

        System.out.println(gf.getGraph());
//        boolean hasPath = hasCycleD(gf.getGraph(), 0, state);

//        int shortestPath = multiSourceBFS(gf.getGraph(), List.of(0), 3);
//        System.out.println(shortestPath);
//        System.out.println(hasPath);
    }
}
