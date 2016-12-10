static class SegmentTreeLazyProp {

	long tree[];
	int lazy[];
	int len;
	int size;
	SegmentTreeLazyProp(int arr[]) { // one based index
		len = arr.length - 1;
		size = 1 << (32 - Integer.numberOfLeadingZeros(len - 1) + 1);  // 2 ^ (ceil(log(len)) + 1)
		tree = new long[size];
		lazy = new  int[size];
		build(1, 1, len, arr);
	}

	long build(int n , int nl , int nr , int arr[]) {
		if(nl == nr) 
			return tree[n] = 1L << arr[newToOld[nl]];
		else {
			int mid = (nl + nr) / 2;
			return tree[n] = build(2*n, nl, mid, arr) | build(2*n + 1, mid + 1, nr, arr);
		}
	}

	boolean isInternal(int n) {
		return 2*n < size && 2*n + 1 < size;
	}

	void upd(int n) {
		if(lazy[n] > 0) {
			if(isInternal(n)) {
				lazy[2*n] = lazy[n];
				lazy[2*n + 1] = lazy[n];
			}
			tree[n] = 1L << lazy[n];
			lazy[n] = 0;
		}
	}

	long update(int n , int nl , int nr , int L ,int R, int c) {

		if(nl == L && nr == R) {
			if(isInternal(n)) {
				lazy[2*n] = c;
				lazy[2*n + 1] = c; 
			}
			lazy[n] = 0;
			return tree[n] = 1L << c;
		}
		else {
			upd(n);
			if(isInternal(n)) {
				upd(2*n);
				upd(2*n + 1);
			}

			long left = tree[2*n];
			long right = tree[2*n + 1];
			int mid = (nl + nr) / 2;

			if(R <= mid)
				left = update(2*n, nl, mid, L, R, c);
			else if(L > mid)
				right = update(2*n + 1, mid + 1, nr, L, R, c);
			else {
				left = update(2*n, nl, mid, L, mid, c);
				right = update(2*n + 1, mid + 1, nr, mid + 1, R, c);
			}

			return tree[n] = left | right;

		}
	}

	void update(int v , int c) {
		update(1, 1, len, L[v], R[v], c);
	}

	long query(int n , int nl , int nr , int L , int R) {
		upd(n);
		if(nl == L && nr == R)
			return tree[n];
		else {
			int mid = (nl + nr) / 2;
			if(R <= mid)
				return query(2*n, nl, mid, L, R);
			else if(L > mid)
				return query(2*n + 1, mid + 1, nr, L, R);
			else
				return query(2*n, nl, mid, L, mid) | query(2*n + 1, mid + 1, nr, mid + 1, R);

		}
	}

	int query(int v) {
		return Long.bitCount(query(1, 1, len, L[v], R[v]));
	}

}