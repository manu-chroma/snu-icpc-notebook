
/*
Use depth-first search (DFS) for all connected components as visited:
dfs(node u)
  for each node v connected to u :
    if v is not visited :
      visited[v] = true
      dfs(v)

dfs_all(graph)
  for each node u:
    if u is not visited :
      visited[u] = true
      connected_component += 1
      dfs(u)
*/

bool visited[max] = {0};
void dfs(vector < vector <int> > & graph, int start) {
	for(int i = 0, size = graph[start].size() ; i < size; i++) {
			if(!visited[graph[start][i]]) {
				visited[graph[start][i]] = 1;
				dfs(graph, graph[start][i]);
		}
	}	
}

void dfs_all(vector < vector <int> > & graph) {
  int connected_comp = 0;
	for(int i = 1; i <= vertex; i++) {
		if(!visited[i]) {
      connected_comp++;
			visited[i] = 1;
			dfs(graph, i);
	}
}