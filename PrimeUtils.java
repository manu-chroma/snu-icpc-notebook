public static boolean[] isPrimeArray(int N) { // Sieve of Erathanoses
	boolean num[] = new boolean[N + 1];
	Arrays.fill(num, true);
	num[1] = num[0]=  false;
	for (int i = 2; i * i <= N; i++)
		if (num[i])  // i is prime
			for (int j = i * i; j <= N; j += i)
				num[j] = false;


	return num;
}

public static int[] sieve(int N) { // Sieve of Erathanoses dependency : isPrimeArray()

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

static int[] loPrimeSieve(int N) { // stores lowest prime factor of each number used for calculating phi
	int[] loPrime = new int[N + 1];
	int pr[] = new int[N];
	int sz = 0;
	for (int i = 2; i <= N; ++i) {
		if (loPrime[i] == 0) {
			loPrime[i] = i;
			pr[sz] = i;
			sz++;
		}
		for (int j = 0; j < sz && pr[j] <= loPrime[i] && i * pr[j] <= N; ++j)
			loPrime[i * pr[j]] = pr[j];
	}

	return loPrime;
}

static int lp[];
static int fi[]; // euler totient function

static void fastFiSieve(int N) {
	lp = new int[N + 1];
	fi = new int[N + 1];
	int pr[] = new int[N];
	int sz = 0;
	fi[1] = 1;
	for (int i = 2; i <= N; ++i) {
		if (lp[i] == 0) {
			lp[i] = i;
			fi[i] = i - 1;
			pr[sz] = i;
			sz++;
		} else {
			// Calculating phi
			if (lp[i] == lp[i / lp[i]])
				fi[i] = fi[i / lp[i]] * lp[i];
			else
				fi[i] = fi[i / lp[i]] * (lp[i] - 1);
		}
		for (int j = 0; j < sz && pr[j] <= lp[i] && i * pr[j] <= N; ++j)
			lp[i * pr[j]] = pr[j];
	}
}

static HashMap<Integer, Integer> primeFactorize(int N) { // Dependency : A sieve (loPrime[]) which contains the lowest prime divisor for each number
	HashMap<Integer, Integer> mp = new HashMap<>();
	int ct, prime;
	while (N != 1) {
		prime = loPrime[N];
		ct = 0;
		while (N % prime == 0) {
			N /= prime;
			ct++;
		}
		mp.put(prime, ct);
	}
	return mp;
}
static int[] bigPrimeSieve(int N) { // Runs in less than a sec for 10^7
	int bigPrime[] = new int[N + 1];
	for(int i=2;i*i<=N;i++)
		if(bigPrime[i] == 0)
			for(int j=i*i;j<=N;j+=i)
				bigPrime[j] = i;
	
	for(int i=1;i<=N;i++)
		bigPrime[i] = bigPrime[i] == 0 ? i : bigPrime[i];
	
	return bigPrime;
}