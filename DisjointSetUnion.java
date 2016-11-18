	public class DisjointSetUnion {
		private int parent[];
		private int size[];
		private int cnt;

		DSU(int length) {
			this.cnt = length;
			parent = new int[length + 10];
			size = new int[length + 10];
			Arrays.fill(size, 1);
			for (int i = 0; i < parent.length; i++)
				parent[i] = i;
		}

		int root(int p) {
			return (parent[p] == p) ? p : (parent[p] = root(parent[p]));
		}

		int sizeOf(int p) {
			return size[root(p)];
		}

		boolean connected(int u, int v) {
			return root(u) == root(v);
		}

		int components() {
			return cnt;
		}

		void union(int u, int v) {
			if (!connected(u, v)) {
				cnt--;
				int rootU = root(u);
				int rootV = root(v);
				if (size[rootU] < size[rootV]) {
					parent[rootU] = rootV;
					size[rootV] += size[rootU];
				} else {
					parent[rootV] = rootU;
					size[rootU] += size[rootV];
				}
			}
		}
	}
