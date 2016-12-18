static class Edge {
	int v;
	long cost;
	Edge(int vv, long wt) {
		v = vv;
		cost = wt;
	}
}

static class BellmanFordSSSP { // Dependency : Edge class
	
	static final long INF = (long)3e18;
	long distTo[];
	ArrayList<Edge>[] adj;
	int V, st;
	
	public BellmanFordSSSP(ArrayList<Edge>[] adj , int V , int start, boolean oneBased) {
		
		this.V = V;
		this.adj = adj;
		st = oneBased ? 1 : 0;
		distTo = new long[V + st];
		
		Arrays.fill(distTo, INF);
		distTo[start] = 0;
		
		for(int i=1;i<=V-1;i++) 
			for(int u=st;u<V+st;u++)
				for(Edge e : adj[u])
					distTo[e.v] = Math.abs(distTo[u]) == INF ? distTo[e.v] :Math.max(-INF,Math.min(distTo[e.v],distTo[u] + e.cost));
		
	}
	
	// Checks wether the graph contains a negative cycle or not
	
	boolean containsNegativeCycle() {
		for(int u=st;u<V+st;u++)
			for(Edge e : adj[u])
				if(distTo[u] != INF && distTo[u] + e.cost < distTo[e.v])
					return true;
		
		return false;
	}
	
	// Sets true for all vertices which are rechable from any negative cycle or null otherwise
	
	boolean[] reachableFromNegativeCycle() {
		if(!containsNegativeCycle())
			return null;
		else {
			ArrayDeque<Integer> queue = new ArrayDeque<>();
			boolean cycleMarked[] = new boolean[V + st];
			for(int u=st;u<V+st;u++)
				for(Edge e : adj[u])
					if(distTo[u] != INF && distTo[u] + e.cost < distTo[e.v] && !cycleMarked[u]) {
						cycleMarked[u] = true;
						queue.add(u);
					}
			
			while(!queue.isEmpty()) {
				int u = queue.remove();
				for(Edge e : adj[u])
					if(!cycleMarked[e.v]) {
						cycleMarked[e.v] = true;
						queue.add(e.v);
					}
			}
			return cycleMarked;
		}
	}
}