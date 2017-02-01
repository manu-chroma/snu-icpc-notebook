static class MyFreqMap  // Order statistics on elements
{
	HashMap<Integer,Integer> freq;
	TreeMap<Integer,TreeSet<Integer>> inverse;
	MyFreqMap(){
		freq = new HashMap<>();
		inverse = new TreeMap<>();
	}

	void addIntoMap(int key,int val){
		TreeSet<Integer> set = inverse.get(key);
		if(set == null)
			set = new TreeSet<>();
		set.add(val);
		inverse.put(key, set);
	}
	void removeFromMap(int key1,int key2){
		TreeSet<Integer> set = inverse.get(key1);
		set.remove(key2);
		if(set.size() == 0)
			inverse.remove(key1);
		else
			inverse.put(key1, set);
	}
	void add(int elem){
		int oldFreq = freq.getOrDefault(elem, 0);
		if(oldFreq == 0){
			freq.put(elem, 1);
			addIntoMap(1, elem);
		}
		else{
			freq.put(elem, oldFreq + 1);
			removeFromMap(oldFreq, elem);
			addIntoMap(oldFreq + 1, elem);
		}

	}
	void remove(int elem){
		int oldFreq = freq.get(elem);
		if(oldFreq == 1){
			freq.remove(elem);
			removeFromMap(1, elem);
		}
		else{
			freq.put(elem, oldFreq - 1);
			removeFromMap(oldFreq, elem);
			addIntoMap(oldFreq - 1, elem);
		}
	}
	int getMaxFreqElem(){
		return inverse.lastEntry().getValue().first();
	}
	@Override
	public String toString() {
		return " freq = " + freq + " inv " + inverse;
	}
}
