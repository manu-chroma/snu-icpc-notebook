static class Edge {
	int v , cost;
	Edge(int v, int cost) {
		this.v = v;
		this.cost = cost;
	}
}

// SegmentTree goes Here

static int LCAHLD(int u , int v) {
	if(head[u] == head[v])
		return level[u] < level[v] ? u : v;
	else
		return level[head[u]] < level[head[v]] ? LCAHLD(u, parent[head[v]]) : LCAHLD(v, parent[head[u]]);
}

static void buildHLD(int u , int par , int hd , int cost) {
	
	head[u] = hd;
	stamp[u] = time++;
	edgeCost[stamp[u]] = cost;

	int maxSize = 0;
	Edge next = null;
	for(Edge e : adj[u]) 
		if(e.v != par && size[e.v] > maxSize) {
			maxSize = size[e.v];
			next = e;
		}
	
	if(next != null)
		buildHLD(next.v, u, hd, next.cost); // Continue the current chain for the maximum branch
	
	for(Edge e : adj[u]) 
		if(e.v != par && e.v != next.v) 
			buildHLD(e.v, u, e.v, e.cost); // start a new chain for others
	
}

static int precompute(int u , int par ,int lev) {
	size[u] = 1;
	parent[u] = par;
	level[u] = lev;
	for(Edge v : adj[u])
		if(v.v != par)
			size[u] += precompute(v.v, u , lev + 1);
	
	return size[u];
}

static ArrayList<Edge>[] adj;
static int size[] , head[] , parent[] , level[] , stamp[] , time = 1 , edgeCost[] , V;
static SegmentTree segTree;

static int queryUp(int from , int to) {
	int sum = 0;
	while(head[from] != head[to]) {
		sum += segTree.query(stamp[head[from]], stamp[from]);
		from = parent[head[from]];
	}
	sum += segTree.query(stamp[to] + 1, stamp[from]);
	return sum;
}

static int dist(int u , int v) {
	int lca = LCAHLD(u, v);
	return queryUp(u, lca) + queryUp(v, lca);
}

static void updateEdgeCost(int u , int v , int cost) {
	segTree.update((level[u] < level[v]) ? stamp[v] : stamp[u], cost);
}
