import java.util.ArrayDeque;

class RabinKarp {

	static final long p1 = (long) (1e9) + 7L;  // mod1
	static final long p2 = (long) (1e9) + 13L; // mod2
	static final long x1 = 46399; 				   // base1
	static final long x2 = 18757;				   // base2
	
	static long sub(long a, long b , long mod) {return ((a % mod) - (b % mod) + mod) % mod;}
	static long mul(long a, long b , long mod) {return ((a % mod) * (b % mod)) % mod;}
	static long add(long a, long b , long mod) {return ((a % mod) + (b % mod)) % mod;}
	
	private long hash(String str , long mod , long x) {
		long hash = 0;
		for (int i = str.length() - 1; i >= 0; i--)
			hash = add(mul(hash, x , mod), str.charAt(i) , mod);

		return hash;
	}
	
	String text;
	int t_len;

	RabinKarp(String text) {
		this.text = text;
		this.t_len = text.length();
	}

	ArrayDeque<Integer> findOccurence(String pat) { // Finds all occurence of pattern in O(|text| + |pat|)
		
		long pHash1 = hash(pat , p1 , x1);
		long pHash2 = hash(pat,  p2 , x2);
		ArrayDeque<Integer> stack = new ArrayDeque<>();
		int p_len = pat.length();
		long pow1 = 1 , pow2 = 1;
		
		for(int i=1;i<=p_len;i++) {
			pow1 = mul(pow1, x1, p1);
			pow2 = mul(pow2, x2, p2);
		}
		long curr_hash1 = 0 , curr_hash2 = 0;
		for (int i = t_len - 1; i >= t_len - p_len; i--) {
			curr_hash1 = add(mul(curr_hash1, x1 , p1), text.charAt(i) , p1);
			curr_hash2 = add(mul(curr_hash2, x2 , p2), text.charAt(i) , p2);
		}
		
		if(curr_hash1 == pHash1 && curr_hash2 == pHash2)
			stack.push(t_len - p_len);
		
		for (int i = t_len - p_len - 1; i >= 0; i--) {
			curr_hash1 = add(sub(mul(curr_hash1, x1 , p1), mul(text.charAt(i + p_len), pow1 , p1) , p1), text.charAt(i) , p1);
			curr_hash2 = add(sub(mul(curr_hash2, x2 , p2), mul(text.charAt(i + p_len), pow2 , p2) , p2), text.charAt(i) , p2);
			if (curr_hash1 == pHash1 && curr_hash2 == pHash2)
				stack.push(i);
		}
		
		return stack;
		
	}
	
	public static void main(String[] args) {
		RabinKarp rabinKarp = new RabinKarp("banana");
		System.out.println(rabinKarp.findOccurence("na"));
	}

}
