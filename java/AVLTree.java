static class AVLTree {
	static class Node {
		long key;
		long sum;
		int height;
		int cnt;
		int size;
		Node left, right, parent;

		Node(long key) {
			this.key = key;
			height = 1;
			sum = key;
			cnt = 1;
			size = 1;
			left = right = parent = null;
		}

		@Override
		public String toString() {
			return "[key = " + key + " s = " +sum + "cnt = "+cnt+" sz = "+size+ "]" + " P = " + (parent != null ? parent.key : "null")
					+ " L = " + (left != null ? left.key : "null") + " R = " + (right != null ? right.key : "null");
		}
	}

	public Node root;

	public AVLTree() {
		root = null;
	}

	public int height(Node n) {
		return n == null ? 0 : n.height;
	}

	public void add(long key) {
		root = add(key, root, null);
		Node N = search(root, key);
		root = rebalance(N);
	}

	public boolean find(long key) {
		return search(root, key) != null;
	}

	public long sum(Node n) {
		return n == null ? 0 : n.sum;
	}
	public int size(Node n){
		return n == null ? 0 : n.size;
	}
	public void adjustHeight(Node N) {
		N.height = 1 + Math.max(height(N.left), height(N.right));
		if (N.left != null)
			N.left.parent = N;
		if (N.right != null)
			N.right.parent = N;
		adjustSum(N); // Small hack , so that I need not call it explicitly!!
		adjustSize(N);
	}

	public void adjustSum(Node N) {
		N.sum = (N.key * (long)N.cnt) + sum(N.left) + sum(N.right);
	}
	public void adjustSize(Node N){
		N.size = N.cnt + size(N.left) + size(N.right);
	}
	public Node rotateRight(Node N) {
		Node oldPar = N.parent;
		Node newN = N.left;
		N.left = newN.right;
		if (newN != null && newN.right != null && newN.right.parent != null)
			newN.right.parent = N;
		adjustHeight(N);
		newN.parent = oldPar;
		newN.right = N;
		adjustHeight(newN);
		N.parent = newN;
		if (oldPar != null) {
			if (oldPar.left == N)
				oldPar.left = newN;
			else
				oldPar.right = newN;

			adjustHeight(oldPar);
		}
		return newN;
	}

	public Node rotateLeft(Node N) {
		Node oldPar = N.parent;
		Node newN = N.right;
		N.right = newN.left;
		if (newN != null && newN.left != null && newN.left.parent != null)
			newN.left.parent = N;
		adjustHeight(N);
		newN.parent = oldPar;
		newN.left = N;
		N.parent = newN;
		adjustHeight(newN);
		if (oldPar != null) {
			if (oldPar.left == N)
				oldPar.left = newN;
			else
				oldPar.right = newN;

			adjustHeight(oldPar);
		}
		return newN;
	}

	public Node search(Node root, long key) {
		if (root != null)
			return ((root.key == key) ? root : (key < root.key ? search(root.left, key) : search(root.right, key)));
		else
			return null;
	}

	public Node smallest(Node root) {
		return (root.left == null) ? root : smallest(root.left);
	}

	public Node largest(Node root) {
		return (root.right == null) ? root : largest(root.right);
	}

	public void del(long key) {
		Node toBeRemoved = search(root, key);
		if (toBeRemoved != null) {
			if (toBeRemoved.cnt == 1)
				delete(toBeRemoved);
			else {
				toBeRemoved.cnt--;
				while (toBeRemoved != null) {
					adjustHeight(toBeRemoved);
					toBeRemoved = toBeRemoved.parent;
				}
			}
		}
	}

	public long sumInRange(long L, long R ) {  	    
		return  (sumStrictlyLess(root, R) - sumStrictlyLess(root, L)); // [L,R) 
	}

	public long sumStrictlyLess(Node root, long key) {
		if (root != null) {
			if (root.key == key)
				return sum(root.left);
			else if (key < root.key)
				return sumStrictlyLess(root.left, key);
			else
				return (root.key * (long)(root.cnt)) + sum(root.left) + sumStrictlyLess(root.right, key);
		} else
			return 0;
	}
	public int countGreater(Node root , long  key)
	{
		if(root != null){
			if(root.key == key){
				return size(root.right);
			}
			else if(root.key < key){
				return countGreater(root.right, key);
			}
			else
				return root.cnt + size(root.right) + countGreater(root.left, key);
		}
		else
			return 0;
	}
	public int countGreater(long key){
		return countGreater(root, key);	    
	}
	public void delete(Node N) {

		if (N.left == null && N.right == null) {
			if (N == root)
				root = null;
			else {
				Node par = N.parent;
				if (par.left == N)
					par.left = null;
				else
					par.right = null;

				Node orig_par = par;
				while (par != null) {
					adjustHeight(par);
					par = par.parent;
				}
				root = rebalance(orig_par);
			}
		} else if (N.right == null && N.left != null) {
			Node par = N.parent;
			if (par != null) {
				if (par.left == N)
					par.left = N.left;
				else
					par.right = N.left;

				N.left.parent = par;
				Node orig_par = par;
				while (par != null) {
					adjustHeight(par);
					par = par.parent;
				}

				root = rebalance(orig_par);

			} else {
				root = root.left;
				root.parent = null;
				adjustHeight(root);
			}
		} else if (N.left == null && N.right != null) {
			Node par = N.parent;
			if (par != null) {
				if (par.left == N)
					par.left = N.right;
				else
					par.right = N.right;

				N.right.parent = par;

				Node orig_par = par;
				while (par != null) {
					adjustHeight(par);
					par = par.parent;
				}
				root = rebalance(orig_par);
			} else {
				root = root.right;
				root.parent = null;
				adjustHeight(root);
			}
		} else {
			Node small = smallest(N.right);
			N.key = small.key;
			N.cnt = small.cnt;  // !!! VERY VERY IMPORTANT
			delete(small);
		}
	}

	public Node rebalance(Node N) {
		Node par = N.parent;
		if (height(N.left) - height(N.right) >= 2) {
			Node M = N.left;
			if (height(M.right) > height(M.left))
				M = rotateLeft(M);
			N = rotateRight(N);
		}
		if (height(N.right) - height(N.left) >= 2) {
			Node M = N.right;
			if (height(M.left) > height(M.right))
				M = rotateRight(M);

			N = rotateLeft(N);
		}

		if (par != null)
			return rebalance(par);
		else
			return N;

	}

	public Node add(long key, Node root, Node parent) {
		if (root == null) {
			Node newNode = new Node(key);
			newNode.parent = parent;
			return newNode;
		} else {
			if (key < root.key)
				root.left = add(key, root.left, root);
			else if (key > root.key)
				root.right = add(key, root.right, root);
			else
				root.cnt++;

			adjustHeight(root);
			return root;
		}
	}

	public void print(Node root) {
		if (root != null) {
			print(root.left);
			System.out.println(root);
			print(root.right);
		}
	}
	private StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb, Node root) {

		if (root == null) {
			sb.append("Tree Empty\n");
			return sb;
		}
		if (root.right != null) {
			toString(new StringBuilder().append(prefix).append(isTail ? "|   " : "    "), false, sb, root.right);
		}
		sb.append(prefix).append(isTail ? "|-- " : "|-- ").append(root.key).append("\n");
		if (root.left != null) {
			toString(new StringBuilder().append(prefix).append(isTail ? "    " : "|   "), true, sb, root.left);
		}
		return sb;
	}

	@Override
	public String toString() {
		return this.toString(new StringBuilder(), true, new StringBuilder(), root).toString();
	}
}
