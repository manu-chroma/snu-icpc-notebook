static class SCC {

	/*
	 * Kosaraju-Sharir Algorithm
	 * 
	 * Identify sinks (reverse post order in inverse graph)
	 * Start normal dfs from the above order , the resulting components form SCC
	 * 
	 * If you want to use 1 based indexing set onBased flag to true
	 * 
	 */

	private ArrayList<Integer>[] invGraph;
	private ArrayDeque<Integer> stack;
	private Iterable<Integer>[] adj;
	private int group[];
	private boolean marked[];
	private int numOfComponents;
	private int st , V;


	@SuppressWarnings("unchecked")
	SCC(Iterable<Integer>[] adj , boolean oneBased) {
		st = oneBased ? 1 : 0;
		V = adj.length - st; 
		group = new int[V + st];
		this.adj = adj;
		invGraph = new ArrayList[V + st];
		for(int i=st;i<V + st;i++)
			invGraph[i] = new ArrayList<>();
		for(int i=st;i<V + st;i++)
			for(int j : adj[i])
				invGraph[j].add(i);

		marked = new boolean[V + st];
		stack = new ArrayDeque<>();
		for(int i=st;i<V + st;i++)
			if(!marked[i])
				reversePostOrder(i);

		marked = new boolean[V + st];
		int grp = 0;
		for(int i : stack)
			if(!marked[i])
				dfs(i, grp++);

		numOfComponents = grp;
		stack = null;
	}

	private void reversePostOrder(int u) {
		marked[u] = true;
		for(int v : invGraph[u])
			if(!marked[v])
				reversePostOrder(v);
		stack.push(u);
	}

	private void dfs(int u , int grp) {
		marked[u] = true;
		group[u] = grp;
		for(int v : adj[u])
			if(!marked[v])
				dfs(v, grp);
	}
	public int[] getSCC() {
		return group;
	}
	public int numberOfComponents() {
		return numOfComponents;
	}

	@SuppressWarnings("unchecked")
	public HashSet<Integer>[] getDAG() {
		HashSet<Integer>[] dag = new HashSet[numOfComponents];
		for(int i=0;i<numOfComponents;i++)
			dag[i] = new HashSet<>();
		for(int i=st;i<V + st;i++)
			for(int v : adj[i])
				if(group[i] != group[v])
					dag[group[i]].add(group[v]);

		return dag;
	}
}
