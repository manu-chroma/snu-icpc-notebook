	class SuffixArray {  // Implemented for Upper case alphabets automatically appends ' $ ' 

		final char $ = '@';  // use ' ` ' for small case alphabets ($ should be smaller than all other characters)
		int equiClass[];
		int order[];		
		int N;

		int add(int a, int b){ return (a + b) % N; }
		int sub(int a ,int b){ return (N + a - b) % N; }

		int[] sortDoubled(int L){

			int newOrder[] = new int[N];
			int cnt[] = new int[N];
			for(int i=0;i<N;i++){
				int idx = sub(order[i], L);
				cnt[equiClass[idx]]++;
			}
			for(int i=1;i<N;i++)
				cnt[i] += cnt[i - 1];
			for(int i=N-1;i>=0;i--){
				int idx = sub(order[i], L);
				newOrder[--cnt[equiClass[idx]]] = idx;
			}

			return newOrder;

		}
		int[] updateEquiClass(int L , int newOrder[]){
			int newClass[] = new int[N];
			newClass[newOrder[0]] = 0;
			for(int i=1;i<N;i++){
				int f1 = equiClass[newOrder[i - 1]];
				int s1 = equiClass[add(newOrder[i - 1], L)];
				int f2 = equiClass[newOrder[i]];
				int s2 = equiClass[add(newOrder[i], L)];
				newClass[newOrder[i]] = newClass[newOrder[i - 1]] + (f1 == f2 && s1 == s2 ? 0 : 1); 
			}
			return newClass;
		}

		int[] getSuffixArray(){

			for(int L = 1;L < N;L <<= 1){
				int[] newOrder = sortDoubled(L);
				int[] newClass = updateEquiClass(L, newOrder);
				order = newOrder;
				equiClass = newClass;
			}
			return order;
		}

		SuffixArray(String str){

			str = str.concat(String.valueOf($));
			N = str.length();
			equiClass = new int[N];
			order = new int[N];


			int cnt[] = new int[27];
			for(int i=0;i<N;i++)
				cnt[str.charAt(i) - $]++;
			for(int i=1;i<27;i++)
				cnt[i] += cnt[i - 1];
			for(int i=N-1;i>=0;i--)
				order[--cnt[str.charAt(i) - $]] = i;
			equiClass[order[0]] = 0;
			for(int i=1;i<N;i++)
				equiClass[order[i]] = equiClass[order[i - 1]] + (str.charAt(order[i]) == str.charAt(order[i - 1]) 
				? 0 : 1 ); 
		}

	}
