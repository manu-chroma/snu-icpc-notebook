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

static long[] dijkstra(int start , int V) {  // Dependency adj (adjacency list of type Edge) , vertex count V 
	PriorityQueue<Edge> pq = new PriorityQueue<>();
	pq.add(new Edge(start, 0));
	boolean marked[] = new boolean[V + 1]; 
	long distTo[] = new long[V + 1];
	while (!pq.isEmpty()) {
		Edge min = pq.remove();
		int u = min.v;
		if(!marked[u]){
			marked[u] = true;
			distTo[u] = min.cost;
			for (Edge e : adj[u])
				if (!marked[e.v])
					pq.add(new Edge(e.v, e.cost + distTo[u]));
		}
	}

	return distTo;
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

