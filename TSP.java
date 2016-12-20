/*
 * Solves the TSP problem in O(N^2 * 2^N)
 * memo[mask][prev] the shortest path using the set integers in mask where the last vertex visited in prev
 */
static int cost[][];
static int memo[][];
static int V;
static int TSP(int mask , int prev) {

	if(memo[mask][prev] != -1)
		return memo[mask][prev];
	else {
		int dist = Integer.MAX_VALUE;
		for(int i = 0;i < V;i++)
			if(i != prev && (mask & (1 << i)) != 0)  // Also check for edge between i and prev
				dist = Math.min(dist,cost[prev][i] + TSP(mask ^ (1 << prev), i));

		return memo[mask][prev] = dist;
	}

}
