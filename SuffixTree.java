import java.util.*;
import java.io.*;
public class SuffixTree {

	String text;
	int len;
	ArrayList<HashMap<Character,Pair>> tree;
	SuffixTree(String text) {
		this.text = text;
		this.len  = text.length();
		this.tree = new ArrayList<>();
		tree.add(new HashMap<>());
		for(int i = len - 1;i >= 0;i--)
			add(i, 0);	
		
		PrintWriter out = new PrintWriter(System.out);
		print(0 , out);
		out.close();
	}

	class Pair {
		int index , start , length;
		Pair(int index , int start,int length){
			this.index = index;
			this.start = start;
			this.length = length;
		}
		@Override
		public String toString() {
			return "start " + start + " len " + length + " s = " + text.substring(start, start + length) + " i " + index;
		}
	}
	
	void add(int idx_text,int idx_tree){

		Pair p = tree.get(idx_tree).get(text.charAt(idx_text));
		if(p == null){
			p = new Pair(tree.size(), idx_text, len - idx_text);
			tree.add(new HashMap<>());
			tree.get(idx_tree).put(text.charAt(idx_text), p);
		}
		else{
			for(int i = idx_text , j = p.start;i < len && j < p.start + p.length;i++,j++){
				if(text.charAt(i) != text.charAt(j)){
					Pair p1 = new Pair(tree.size(), j, p.start + p.length - j);
					tree.add(null);
					tree.set(p1.index, tree.get(p.index));
					tree.set(p.index, new HashMap<>());
					Pair p2 = new Pair(tree.size(), i, len - i);
					p.length = j - p.start;
					tree.add(new HashMap<>());
					tree.get(p.index).put(text.charAt(j), p1);
					tree.get(p.index).put(text.charAt(i), p2);
					return;
				}	
			}
			add(idx_text + p.length, p.index);
		}
	}

	void print(int idx_tree,PrintWriter out){
		for(Map.Entry<Character, Pair> e : tree.get(idx_tree).entrySet()){
			Pair p = e.getValue();
			out.println(text.substring(p.start, p.start + p.length));
			print(p.index,out);
		}
	}

	public static void main(String[] args) throws IOException {

		Scanner s1 = new Scanner(System.in);
		SuffixTree st = new SuffixTree(s1.nextLine()); // Input should include $ character
		/***** Since the implementation uses recursion to build the tree , it's safe to run it as thread and increase the stack size *****/
		s1.close();
	}

}

