/* LCA <NlogN , logN> dependency : level , log , V , DP = new int[log(V) + 1][V + 1];, parent (for the first level of DP) */
static int DP[][]; // One based vertices
static int level[];
static int parent[];
static int log(int N){
	return 31 - Integer.numberOfLeadingZeros(N);
}
static void buildSparse() {
	
	for(int i=1;i<=V;i++)
		DP[0][i] = parent[i];
	
	for (int i = 1; i < DP.length; i++) 
		for (int j = 1; j <= V; j++) 
			DP[i][j] = DP[i - 1][DP[i - 1][j]];

}

static int LCA(int u , int v){

	if(level[v] < level[u]){
		int temp = u;
		u = v;
		v = temp;
	}
	int diff = level[v] - level[u];
	while(diff > 0){        // Bring v to the same level as u
		int log = log(diff);
		v = DP[log][v];
		diff -= (1 << log);
	}
	while(u != v){
		int i = log(level[u]);
		for(;i > 0 && DP[i][u] == DP[i][v];)
			i--;

		u = DP[i][u];
		v = DP[i][v];
	}

	return u;
}