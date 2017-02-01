static long mod = 1000000007;

static class Matrix  { // Don't use ModMath class with Matrix (very slow in matrix exponentiation) 
	long e00, e01, e10, e11;

	Matrix(long a, long b, long c, long d) {
		e00 = a;
		e01 = b;
		e10 = c;
		e11 = d;
	}

	Matrix multiply(Matrix t) {
		long a = (((e00 * t.e00) % mod) + ((e01 * t.e10) % mod)) % mod;
		long b = (((e00 * t.e01) % mod) + ((e01 * t.e11) % mod)) % mod;
		long c = (((e10 * t.e00) % mod) + ((e11 * t.e10) % mod)) % mod;
		long d = (((e10 * t.e01) % mod) + ((e11 * t.e11) % mod)) % mod;
		return new Matrix(a, b, c, d);
	}
	public String toString() {
		return e00 + " " + e01 + "\n" + e10 + " " + e11 + "\n";
	}

	static long mul(long a, long b) {
		return ((a % mod) * (b % mod)) % mod;
	}

	static long add(long a, long b) {
		return ((a % mod) + (b % mod)) % mod;
	}
}

public static Matrix pow(Matrix m, int b) {
	if (b == 1)
		return m;
	else {
		if ((b & 1) == 0)
			return pow(m.multiply(m), b >> 1);
		else
			return m.multiply(pow(m.multiply(m), b >> 1));
	}
}