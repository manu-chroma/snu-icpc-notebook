static class Edge implements Comparable<Edge> {
	int u , v;
	long cost;
	Edge(int u , int v, long cost) {
		this.u = u;
		this.v = v;
		this.cost = cost;

	}
	Edge(int v , long cost) {
		this(-1 , v , cost);
	}
	@Override
	public int compareTo(Edge o) {
		return Long.compare(this.cost, o.cost);
	}
}

private static long distTo[];  // Should be filled with infinity	   
private static void dijkstra(int start) {
    PriorityQueue<Edge> pq = new PriorityQueue<>();
    pq.add(new Edge(start, 0));
    distTo[start] = 0;
    while (!pq.isEmpty()) {
        Edge min = pq.remove();
        int u = min.v;
        if (distTo[u] < min.cost)
            continue;

        for (Edge e : adj[u])
            if (distTo[e.v] > distTo[u] + e.cost) {
                distTo[e.v] = distTo[u] + e.cost;
                pq.add(new Edge(e.v, distTo[e.v]));
            }
    }
}


static long KruskalMST(Edge arr[], int V) { // Dependency : array of edges (Type Edge) , vertex count V , DisjointSetUnion data structure
	Arrays.sort(arr);
	DisjointSetUnion dsu = new DisjointSetUnion(V);
	long minCost = 0;
	for (int i = 0; i < arr.length && dsu.components() != 1; i++) {
		if (!dsu.connected(arr[i].u, arr[i].v)) {
			dsu.union(arr[i].u, arr[i].v);
			minCost += arr[i].cost;
		}
	}
	return minCost;
}

