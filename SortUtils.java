static int arr[];
static int N;
static Random rand = new Random();

static void swap(int i , int j) {
	int temp = arr[i];
	arr[i] = arr[j];
	arr[j] = temp;
}

// Finds the kth largest element using quicksort's linear selection 
// Dependency : swap , java.util.Random , arr[] , N (length)
static void selection(int L , int R , int k) { // [L , R] left inclusive , right inclusive , 0 based indexing
	int lt , gt , pt; 
	while(L <= R) {
		swap(L, L + rand.nextInt(R - L + 1));
		int pivot = arr[L];
		lt = pt = L;
		gt = R;
		while(pt <= gt) {
			if(arr[pt] < pivot)
				swap(pt++, lt++);
			else if(arr[pt] > pivot)
				swap(pt, gt--);
			else
				pt++;
		}
		if(k >= lt && k <= gt)
			return;
		else if(k < lt)
			R = lt - 1;
		else
			L = gt + 1;
	}
}

// Finds the number of inversions in an array using merge sort's merge subroutine in O(NlogN)
// Dependency : aux array of auxillary storage , arr[] , N (length) of arr
static int aux[] , arr[] , N;
static long merge(int L , int M , int R ) {
	long inv = 0;
	System.arraycopy(arr, L, aux, L, R - L);
	int k = L;
	for(int i=L,j=M;i<M || j<R;) {
		if(i < M && j < R && aux[i] > aux[j])
			inv += (M - i);
		if(i < M && (j >= R || aux[i] <= aux[j]))
			arr[k++] = aux[i++];
		else if(j < R && (i >= M || aux[j] < aux[i]))
			arr[k++] = aux[j++];
	}
	return inv;
}

static long sort(int L , int R) { // [L , R) left inclusive , right exclusive , 0 based indexing
	if(L + 1 < R) {
		int M = (L + R) / 2;
		return (sort(L, M) + sort(M, R) + merge(L, M, R));
	}
	else
		return 0;
}