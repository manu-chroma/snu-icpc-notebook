// Use a vector to creates a dynamic array 
// and initialize it in one statement.
// Creates a vector of size N and values as 0.
vector<int> v(N, 0); // OR vi v(N, 0); 

/*
Use depth-first search (DFS) for all connected components as visited:
dfs(node u)
  for each node v connected to u :
    if v is not visited :
      visited[v] = true
      dfs(v)


for each node u:
  if u is not visited :
    visited[u] = true
    connected_component += 1
    dfs(u)
*/

- Bit Manipulation.
int  __builtin_popcount(unsigned int)

It returns the numbers of set bits in an integer 
(the number of ones in the binary representation of the integer).