static class FenwickTree {  // In this variant of BIT each query is **NOT** a cumulative sum , it is the actual freq
	// Point Query and Range update
	int tree[];
	int len;
	FenwickTree(int len) {
		this.len = len;
		tree = new int[len + 10];
	}
	void update(int idx , int val) {
		for(;idx <= len;idx += (idx & -idx))
			tree[idx] += val;
	}
	int query(int idx) {
		int sum = 0;
		for(;idx > 0;idx -= (idx & -idx))
			sum += tree[idx];

		return sum;
	}
	void update(int L , int R , int val) {
		if(L <= R && L > 0) {
			update(L, val);
			update(R + 1, -val);
		}
	}
}