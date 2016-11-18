	static boolean marked[]; // Part of dijkstra
	static long distTo[];
	static ArrayList<Edge>[] adj;

	static class Edge implements Comparable<Edge> {
		int v;
		long cost;

		Edge(int v, long cost) {
			this.v = v;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge o) {
			return Long.compare(this.cost, o.cost);
		}
	}

	static void dijkstra(int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		pq.add(new Edge(start, 0));

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
	}

	static class Edge_MST implements Comparable<Edge_MST> // For kruskal MST
	{
		int u, v;
		long cost;

		Edge_MST(int u, int v, long cost) {
			this.u = u;
			this.v = v;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge_MST o) {
			return Long.compare(this.cost, o.cost);
		}
	}

	static long KruskalMST(Edge_MST arr[], int V) {
		Arrays.sort(arr);
		DSU dsu = new DSU(V);
		long minCost = 0;
		for (int i = 0; i < arr.length && dsu.components() != 1; i++) {
			if (!dsu.connected(arr[i].u, arr[i].v)) {
				dsu.union(arr[i].u, arr[i].v);
				minCost += arr[i].cost;
			}
		}
		return minCost;
	}

