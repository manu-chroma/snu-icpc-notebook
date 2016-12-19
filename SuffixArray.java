class SuffixArray {  
	// Implemented for Upper case alphabets automatically appends ' $ ' 
	// use ' ` ' for small case alphabets ($ should be smaller than all other characters)
	final char $ = '@';  
	int eqv[];
	int pos[];		
	int N;

	int add(int a, int b){ return (a + b) % N; }
	int sub(int a ,int b){ return (N + a - b) % N; }

	int[] sortDoubled(int L){

		int newPos[] = new int[N];
		int cnt[] = new int[N];
		for(int i=0;i<N;i++){
			int idx = sub(pos[i], L);
			cnt[eqv[idx]]++;
		}
		for(int i=1;i<N;i++)
			cnt[i] += cnt[i - 1];
		for(int i=N-1;i>=0;i--){
			int idx = sub(pos[i], L);
			newPos[--cnt[eqv[idx]]] = idx;
		}

		return newPos;

	}
	int[] updateEquiClass(int L , int newPos[]){
		int newEqv[] = new int[N];
		newEqv[newPos[0]] = 0;
		for(int i=1;i<N;i++){
			int f1 = eqv[newPos[i - 1]];
			int s1 = eqv[add(newPos[i - 1], L)];
			int f2 = eqv[newPos[i]];
			int s2 = eqv[add(newPos[i], L)];
			newEqv[newPos[i]] = newEqv[newPos[i - 1]] + (f1 == f2 && s1 == s2 ? 0 : 1); 
		}
		return newEqv;
	}

	int[] getSuffixArray(){

		for(int L = 1;L < N;L <<= 1){
			int[] newPos = sortDoubled(L);
			int[] newEqv = updateEquiClass(L, newPos);
			pos = newPos;
			eqv = newEqv;
		}
		return pos;
	}

	SuffixArray(String str){

		str = str.concat(String.valueOf($));
		N = str.length();
		eqv = new int[N];
		pos = new int[N];


		int cnt[] = new int[27];
		for(int i=0;i<N;i++)
			cnt[str.charAt(i) - $]++;
		for(int i=1;i<27;i++)
			cnt[i] += cnt[i - 1];
		for(int i=N-1;i>=0;i--)
			pos[--cnt[str.charAt(i) - $]] = i;
		eqv[pos[0]] = 0;
		for(int i=1;i<N;i++)
			eqv[pos[i]] = eqv[pos[i - 1]] + (str.charAt(pos[i]) == str.charAt(pos[i - 1]) 
				? 0 : 1 ); 
	}
}

static String str;
static int length;
static int[] suffix;

static char charAt(int i){ return i < length ? str.charAt(i) : '$';}

static int[] findRange(int left , int right , char ch , int offset){
	
	boolean flag = false;
	int mid = -1;
	int range[] = new int[2];
	int l  = left , r = right;
	int ub = -1 , lb = -1;
	while(l <= r){
		mid = (l + r) / 2;
		if(charAt(suffix[mid] + offset) >= ch){
			lb = mid;
			r = mid - 1;
			flag = !flag ? charAt(suffix[mid] + offset) == ch : flag;
		}
		else
			l = mid + 1;
	}
	range[0] = lb;
	l = left;
	r = right;
	while(l <= r){
		mid = (l + r) / 2;
		if(charAt(suffix[mid] + offset) <= ch){
			ub = mid;
			l = mid + 1;
		}
		else
			r = mid - 1;
	}
	range[1] = ub;
	// System.out.println("range " + Arrays.toString(range));
	return flag ? range : null;
}

static int[] findOccurence(String pattern){
	
	int[] range = {1 , length};
	for(int i=0 , len = pattern.length(); i < len && range != null;i++)
		range = findRange(range[0], range[1], pattern.charAt(i), i);
	
	return range;
}

private static void solve(FastScanner s1, PrintWriter out) {
	
	str = s1.nextLine();
	length = str.length();
	suffix = new SuffixArray(str).getSuffixArray();

	int cnt[] = new int[length + 5];
	
	int Q = s1.nextInt();

	while(Q-->0){
		int[] range = findOccurence(s1.next());
		if(range != null){
			cnt[range[0]]++;
			cnt[range[1] + 1]--;
		}
	}
	for(int i=1;i<=length;i++)
		cnt[i] += cnt[i - 1];
	
	for(int i=1;i<=length;i++)
		if(cnt[i] != 0)
			out.print(suffix[i] + " ");
}

