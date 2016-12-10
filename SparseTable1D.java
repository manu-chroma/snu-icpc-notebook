static class SparseTable1D  // < O(Nlog(N)) , O(1) >  0 based indexing
{
	int sparse[][];
	int len;
	static int log(int N) { return 31 - Integer.numberOfLeadingZeros(N); }

	SparseTable1D(int arr[])
	{
		len = arr.length;
		int k = log(len) + 1;
		sparse = new int[k][len];
		for (int i = 0; i < len; i++)
			sparse[0][i] = arr[i];

		for(int i=1;i<k;i++)
			for(int j=0;j+(1<<i) <= len;j++)
				sparse[i][j] = Math.max(sparse[i-1][j],sparse[i-1][j+(1<<(i-1))]);

	}

	int getMax(int L,int R)
	{
		int sz = R - L + 1;
		int k  = log(sz);
		int v1 = sparse[k][L];
		int v2 = sparse[k][L + (sz - (1 << k))];
		return Math.max(v1,v2);
	}

}