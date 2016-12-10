static class SparseTable2D  // <O (MNlog(M)log(N)) , O(1) >  
{		
	/*
	 * Dont use Math.log its very slow for a lot of queries (10^6), 
	 * instead precompute the log values . or use log(n) = (31 - Integer.numberOfLeadingZeros(n))
	 * Costed me several days
	 * 
	 * In a sparse table you break the query rectangle into four smaller rects each with 
	 * dimensions log(height) X log(width)
	 * 
	 * So the query time is O(1) , but the build time is O(M * N * log(M) * log(N))
	 * 
	 * 	********|****|******
	 * 	********|****|******
	 * 	********|****|******
	 * 	--------|----|------
	 * 	******************
	 * 	******************
	 */

	int sparse[][][][];
	static int log(int N) { return 31 - Integer.numberOfLeadingZeros(N); }
	int R,C;

	int getMax(int x1,int y1,int x2,int y2, boolean build){
		int x_sz = x2 - x1 + 1;
		int y_sz = y2 - y1 + 1;
		int k1 = (x_sz == 1) ? 0 : log(build ? (x_sz - 1) : x_sz);
		int k2 = (y_sz == 1) ? 0 : log(build ? (y_sz - 1) : y_sz);
		int NW = sparse[k1][k2][x1][y1]; // North-West
		int NE = sparse[k1][k2][x1][y_sz - (1 << k2) + y1]; // North-East
		int SW = sparse[k1][k2][x_sz - (1 << k1) + x1][y1]; // South-West
		int SE = sparse[k1][k2][x_sz - (1 << k1) + x1][y_sz - (1 << k2) + y1]; // South-East
		return (int) Math.max(Math.max(NW, NE), Math.max(SW, SE));
	}

	SparseTable2D(int arr[][],int R,int C)
	{
		this.R = R;
		this.C = C;
		int k1 = log(R) + 1;
		int k2 = log(C) + 1;
		sparse = new int[k1][k2][R][C];
		for (int i = 0; i < R; i++)
			for (int j = 0; j < C; j++)
				sparse[0][0][i][j] = arr[i][j];

		for(int h=0;h<k1;h++)
		{
			for(int v=0;v<k2;v++)
			{
				if(!(h == 0 && v == 0))
				{
					for(int i=0;i+(1<<h) <= R;i++)
					{
						for(int j=0;j+(1<<v) <= C;j++)
						{
							sparse[h][v][i][j] = getMax(i, j, i + (1<<h) - 1, j + (1<<v) - 1, true);
						}
					}
				}
			}
		}

	}
}
