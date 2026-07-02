package org.ayushsingh;

import org.ayushsingh.models.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

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
        //    adj.get(e[1]).add(e[0]); // Uncomment for Undirected Graph
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
            List<Integer> neighbors = graph.get(top);
            for (int i = neighbors.size() - 1; i >= 0; i--) { // Check in Reverse Order to match the recursion way
                int neighbor = neighbors.get(i);
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    st.push(neighbor);
                }
            }
        }
    }

    public static void bfs(List<List<Integer>> graph, int start) {
        Deque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[graph.size()];
        q.offer(start);
        visited[start] = true;
        int first;
        while (!q.isEmpty()) {
            first = q.poll();
            System.out.print(first + " ");
            for(int i: graph.get(first)) {
                if(!visited[i]) {
                    visited[i] = true;
                    q.offer(i);
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
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(start);
        visited[start] = true;
        Integer first;

        while (!q.isEmpty()) {
            first = q.poll();
            if(Objects.equals(first, end)) return true;

            for (int node : graph.get(first)) {
                if (!visited[node]) {
                    visited[node] = true;
                    q.offer(node);
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

        visited[start] = true;
        q.offer(start);
        distance[start] = 0;

        while (!q.isEmpty()) {
            int node = q.poll();

            if (node == end) return distance[node];  // early exit

            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    distance[neighbor] = distance[node] + 1;
                    q.offer(neighbor);
                }
            }
        }
        return -1; // not reachable
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
                    count++; // assume each 1 is a component
                    for(int[] dir : dirs) {
                        x = i + dir[0];
                        y = j + dir[1];
                        if(x>=0 && y>=0 && x<row && y < col && grid[x][y] == '1') {
                            int idx = i * col + j; // (elements before this row) + (offset in this row)
                            int idy = x * col + y;
                            int p1 = uf.find(idx);
                            int p2 = uf.find(idy);

                            if(p1 != p2) {
                                uf.union(p1, p2);
                                count--; // remove common component
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

    //Dijkstra's Algorithm ( Shortest Distance in Weighted Graph( Non-negative weight)
    public int shortestDistance(List<List<Graph.Edge>> graph, Integer start, Integer end) {
        boolean[] visited = new boolean[graph.size()];
        int[] distance = new int[graph.size()];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[start] = 0;

        PriorityQueue<Graph.Edge> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));
        pq.add(new Graph.Edge(start, 0));

        while (!pq.isEmpty()) {
            Graph.Edge e = pq.poll();
            int node = e.node;

            if(visited[node]) continue;
            visited[node] = true;

            for(Graph.Edge n : graph.get(node)) {
                if(distance[n.node] > distance[node] + n.weight) {
                    distance[n.node] = distance[node] + n.weight;
                    pq.add(new  Graph.Edge(n.node, distance[n.node]));
                }
            }

        }

        return distance[end] == Integer.MAX_VALUE ? -1 : distance[end];
    }

    // Dijkstra Algorithm for Shortest Path ( Get Entire Shortest Path )
    public List<Integer> shortestPath(List<List<Graph.Edge>> graph, Integer src, Integer end) {
        int[] distance = new int[graph.size()];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[src] = 0;
        int[] parent = new int[graph.size()];
        Arrays.fill(parent, -1);
        PriorityQueue<Graph.Edge> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));
        Set<Graph.Edge> visited = new HashSet<>();
        pq.add(new Graph.Edge(src, 0));
        visited.add(pq.peek());

        while (!pq.isEmpty()) {
            Graph.Edge e = pq.poll();
            int node = e.node;
            if(visited.contains(e)) continue;

            visited.add(e);

            for(Graph.Edge n : graph.get(node)) {
                if(distance[n.node] > distance[node] + n.weight ) {
                    distance[n.node] = distance[node] + n.weight;
                    pq.add(new  Graph.Edge(n.node, distance[n.node]));
                    parent[n.node] = node;
                }
            }
        }

        if (distance[end] == Integer.MAX_VALUE) return List.of(); // no path

        // Reconstructing Path from Parent Array.
        List<Integer> ans = new ArrayList<>();
        while(end != -1) {
            if(parent[end] != -1) ans.add(parent[end]);
            end = parent[end];
        }

        // Reverse it
        Collections.reverse(ans);
        return ans;
    }

    // Prim's Algorithm
    // Answer for LeetCode: 1584. Min Cost to Connect All Points
    record Pair(int edge, int weight) {}
    public int minCostConnectPoints(int[][] points) {
        int ans = 0;
        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));
        boolean[] visited = new boolean[points.length];

        Pair first = new Pair(0, 0); // Start with any Vertex, and its weight will be zero
        pq.add(first);

        while( !pq.isEmpty() ) {
            Pair min = pq.poll();
            int node = min.edge;
            if(visited[min.edge]) continue;
            System.out.println(node);
            ans += min.weight;
            visited[node] = true;

            for(int i = 0 ; i < points.length ; i++) {
                if(!visited[i]) {
                    int distance = Math.abs(points[node][0] - points[i][0]) + Math.abs(points[node][1] - points[i][1]); // Use any other logic to calculat distance here.
                    Pair toAdd = new Pair(i, distance);
                    pq.add(toAdd);
                }
            }
        }

        return ans;
    }



    // Same about question Without PriorityQueue
    public int minCostConnectPoints2(int[][] points) {
        int ans = 0;

        boolean[] isMST = new boolean[points.length];
        int[] minDis = new int[points.length];

        Arrays.fill(minDis, Integer.MAX_VALUE);
        minDis[0] = 0;

        for(int k = 0 ; k < points.length; k++) {
            int top = -1;
            for(int j = 0 ; j < minDis.length ; j++) {
                if(!isMST[j] && (top == -1 || minDis[top] > minDis[j])) top = j;
            }

            isMST[top] = true;
            ans += minDis[top];

            for(int i = 0 ; i < points.length ; i++) {
                if(!isMST[i]) {
                    minDis[i] = Math.min(minDis[i], Math.abs(points[top][0] - points[i][0]) + Math.abs(points[top][1] - points[i][1]));
                }
            }
        }

        return ans;
    }

    // Bellman Ford Algorithm
    // Relax the Edge of the Graph (V-1) Times : V is No. of Vertex
    // After V-1 times, we'll have the shortest path from source to all the node, even in Negative Graphs


    // FloydWarshall Algorithm
    public static void floydWarshall(int[][] edges, int n) {
        int[][] floyd = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(floyd[i], Integer.MAX_VALUE);
            floyd[i][i] = 0; // distances from a node to itself remain should be Zero (IMP)
        }

        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            floyd[u][v] = Math.min(floyd[u][v], w);
            floyd[v][u] = Math.min(floyd[v][u], w); // Remove for Directed Graph
        }

        for (int via = 0; via < n; via++) {
            for (int i = 0; i < n; i++) {
                if (floyd[i][via] == Integer.MAX_VALUE) continue;
                for (int j = 0; j < n; j++) {
                    if (floyd[via][j] == Integer.MAX_VALUE) continue;
                    floyd[i][j] = Math.min(floyd[i][j], floyd[i][via] + floyd[via][j]); // If input has multiple edges between same nodes, we should keep the minimum
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println(floyd[i][j]);
            }
        }

        // Negative Cycle Detection
        for (int i = 0; i < n; i++) {
            if (floyd[i][i] < 0) {
                System.out.println("Negative cycle detected");
            }
        }
    }

    // Kruskal's Algorithm
    //1. Sort all Edges in ascending order by their weight
    //2. Use Union-Find to connect all the edges


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
