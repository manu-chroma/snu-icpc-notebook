static int[] prefix(String str){
	int N = str.length();
	int border = 0;
	int prefix[] = new int[N];
	
	for(int i=1;i<N;i++){
		
		while(border != 0 && str.charAt(border) != str.charAt(i))
			border = prefix[border - 1];
		
		if(str.charAt(border) == str.charAt(i))
			prefix[i] = ++border;
	
	}
	
	return prefix;
}

static ArrayList<Integer> findOccurence(String text , String pattern) {
	int patLen = pattern.length();
	int textLen = text.length();
	int prefix[] = prefix(pattern.concat("$").concat(text));
	ArrayList<Integer> pos = new ArrayList<>();
	for(int i=0;i<textLen;i++){
		int j = i + patLen + 1;
		if(prefix[j] == patLen)
			pos.add((j - (2 * patLen)));

	return pos;
}
