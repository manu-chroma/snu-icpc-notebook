public static boolean[] isPrimeArray(int N) // Sieve of Erathanoses
{
	boolean num[] = new boolean[N + 1];
	Arrays.fill(num, true);
	num[1] = num[0]=  false;
	for (int i = 2; i * i <= N; i++)
		if (num[i])  // i is prime
			for (int j = i * i; j <= N; j += i)
				num[j] = false;


	return num;
}

public static int[] sieve(int N) // Sieve of Erathanoses dependency : isPrimeArray()
{

	boolean isPrime[] = isPrimeArray(N);
	int sz = 0;
	for(boolean b : isPrime)
		sz += b ? 1 : 0;
	int arr[] = new int[sz];
	int ptr = 0;
	for (int i = 2; i <= N; i++)
		if (isPrime[i])
			arr[ptr++] = i;

	return arr;
}

static BitSet segmentedSieve(long L , long R) {  // Dependency : Primes till sqrt(R) 
	int len = (int) (R - L + 1);
	BitSet bitSet = new BitSet(len);
	bitSet.set(0, len);
	for(long p : primes)
		for(long i = L % p == 0 ? L : L + (p - (L % p));i <= R;i += p)
			if(i != p)
				bitSet.set((int) (i - L) , false);
	
	if(L == 1)
		bitSet.set(0, false);
	
	return bitSet;
}
static int primes[];