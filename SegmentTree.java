
	static class SegmentTree  // Implemented to store min in a range , point update and range query
	{
		int tree[];
		int len;
		int size;
		SegmentTree(int arr[]) // arr should be a 1 based array
		{
			len = arr.length - 1;
			for(size=1;size<len;size <<= 1)
				;
			size <<= 1;
			tree = new int[size];
			build(arr, 1, 1, len);
		}
		void update(int node,int idx,int val,int nl,int nr)
		{
			if(nl == nr && nl == idx)
				tree[node] = val;
			else {
				int mid = (nl + nr) / 2;
				if(idx <= mid)
					update(2*node, idx , val ,nl , mid);
				else
					update((2*node) + 1, idx ,val , mid + 1, nr);
					
				tree[node] = Math.min(tree[2*node],tree[(2 * node) + 1]);
			}
		}
		void update(int idx , int val){
			update(1, idx, val, 1, len);
		}
		int query(int L , int R){
			return query(1, L, R, 1, len);
		}
		int query(int node , int L , int R, int nl, int nr)
		{
			int mid = (nl + nr) / 2;
			if(nl == L && nr == R)
				return tree[node];
			else if(R <= mid)
				return query(2 * node, L, R, nl, mid);
			else if(L > mid)
				return query((2*node)+1, L, R, mid + 1 , nr);
			else
				return Math.min(query(2*node, L, mid , nl , mid) ,  query((2*node)+1, mid+1, R , mid+1,nr));
		}
		void build(int arr[],int node,int L,int R)
		{
			if(L == R)
				tree[node] = arr[L];
			else
			{
				int mid = L + ((R-L)/2);
				build(arr, 2 * node, L, mid);
				build(arr, (2 * node) + 1, mid + 1, R);
				tree[node] = Math.min(tree[2*node] , tree[(2 * node) + 1]);
			}
		}
	}
