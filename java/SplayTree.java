static class SplayTree /** The following implementation does not allow duplicates **/{
	private Vertex root = null;

	static class Vertex {
		int key;
		// Sum of all the keys in the subtree - remember to update
		// it after each operation that changes the tree.
		long sum;
		Vertex left;
		Vertex right;
		Vertex parent;

		Vertex(int key, long sum, Vertex left, Vertex right, Vertex parent) {
			this.key = key;
			this.sum = sum;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}

		@Override
		public String toString() {
			return "[key = " + key + "]" + " p = " + (parent != null ? parent.key : "null") + " l = "
					+ (left != null ? left.key : "null") + " r = " + (right != null ? right.key : "null");
		}
	}

	static class VertexPair {
		Vertex left;
		Vertex right;

		VertexPair() {
		}

		VertexPair(Vertex left, Vertex right) {
			this.left = left;
			this.right = right;
		}
	}

	private void update(Vertex v) {
		if (v == null)
			return;
		v.sum = (v.key + (v.left != null ? v.left.sum : 0) + (v.right != null ? v.right.sum : 0));
		if (v.left != null) {
			v.left.parent = v;
		}
		if (v.right != null) {
			v.right.parent = v;
		}
	}

	private void smallRotation(Vertex v) {
		Vertex parent = v.parent;
		if (parent == null) {
			return;
		}
		Vertex grandparent = v.parent.parent;
		if (parent.left == v) {
			Vertex m = v.right;
			v.right = parent;
			parent.left = m;
		} else {
			Vertex m = v.left;
			v.left = parent;
			parent.right = m;
		}

		update(parent);
		update(v);
		v.parent = grandparent;
		if (grandparent != null) {
			if (grandparent.left == parent) {
				grandparent.left = v;
			} else {
				grandparent.right = v;
			}
		}
		update(grandparent);

	}

	private void bigRotation(Vertex v) {
		if (v.parent.left == v && v.parent.parent.left == v.parent) {
			// Zig-zig left
			/*       a
			 *      /
			 *     /
			 *    b  
			 */
			smallRotation(v.parent);
			smallRotation(v);
		} else if (v.parent.right == v && v.parent.parent.right == v.parent) {
			// Zig-zig right
			/*
			 * 	a
			 * 	 \
			 * 	  \
			 * 	   b
			 */
			smallRotation(v.parent);
			smallRotation(v);
		} else {
			/* Zig-zag
			 *    a    a
			 *    /    \
			 *    \	   /
			 *     b   b
			 */
			smallRotation(v);
			smallRotation(v);
		}
	}

	private Vertex splay(Vertex v) {
		if (v == null)
			return null;
		while (v.parent != null) {
			if (v.parent.parent != null)
				bigRotation(v);
			else
				smallRotation(v);
		}
		return v;
	}

	// Searches for the given key in the tree with the given root
	// and calls splay for the deepest visited node after that.
	// Returns pair of the result and the new root.
	// If found, result is a pointer to the node with the given key.
	// Otherwise, result is a pointer to the node with the smallest
	// bigger key (next value in the order).
	// If the key is bigger than all keys in the tree,
	// then result is null.
	private VertexPair find(Vertex root, int key) {  //searches for a elem and splays the last touched node.
		Vertex v = root;	// Returns a vertex pair with 
		Vertex last = root; // Left node  = ceil of the search
		Vertex next = null; // Right node = last touched node in the search
		while (v != null) {
			if (v.key >= key && (next == null || v.key < next.key)) {
				next = v;
			}
			last = v;
			if (v.key == key) {
				break;
			}
			if (v.key < key) {
				v = v.right;
			} else {
				v = v.left;
			}
		}
		root = splay(last);
		return new VertexPair(next, root);
	}

	private static void print(Vertex root) {
		if (root != null) {
			print(root.left);
			System.out.println(root);
			print(root.right);
		}
	}

	private VertexPair split(Vertex root, int key) {
		VertexPair result = new VertexPair();
		VertexPair findAndRoot = find(root, key);
		root = findAndRoot.right;
		result.right = findAndRoot.left;
		if (result.right == null) { // Key is largest in the tree
			result.left = root;
			return result;
		}
		result.right = splay(result.right);
		result.left = result.right.left;
		result.right.left = null;
		if (result.left != null)
			result.left.parent = null;

		update(result.left);
		update(result.right);

		return result;
	}

	private Vertex merge(Vertex left, Vertex right) {
		if (left == null)
			return right;
		if (right == null)
			return left;
		left = find(left, Integer.MAX_VALUE).right; // root after the find and splay , Find the max in left tree							 
		left.right = right;
		left.parent = null;
		update(left);
		return left;
	}

	public void insert(int x) {    // Split the tree at x and then the left tree has all elements less
		if (!find(x)) {            // than x and right tree has all elements greater now create a new node
			Vertex left = null;    // and merge the left-->new node-->right
			Vertex right = null;
			Vertex new_vertex = null;
			VertexPair leftRight = split(root, x);
			left = leftRight.left;
			right = leftRight.right;
			if (right == null || right.key != x) {
				new_vertex = new Vertex(x, x, null, null, null);
			}
			root = merge(merge(left, new_vertex), right);
			root.parent = null;
			update(root);
		}
	}

	public void erase(int x) {
		VertexPair vp = find(root, x);
		root = vp.right;
		if (root != null)
			root.parent = null;
		update(root);
		if (vp.right != null && vp.right.key == x) {
			if (vp.right.right == null) {
				root = vp.right.left;
				if (root != null)
					root.parent = null;
				update(root);
			} else {   		//First splay the ceil of x then splay x now the ceil of x is in the right , 		    				
				find(root, x + 1);  //so we can just redirect the root = root.right 
				vp = find(root, x);
				root = vp.right.right;
				root.left = vp.right.left;
				root.parent = null;
				update(root);
			}
		}
	}

	public boolean find(int x) {
		VertexPair vp = find(root, x);
		root = vp.right;
		return root != null && root.key == x;
	}

	public long sum(int from, int to) {
		VertexPair leftMiddle = split(root, from);
		Vertex left = leftMiddle.left;
		Vertex middle = leftMiddle.right;
		VertexPair middleRight = split(middle, to + 1);
		middle = middleRight.left;
		Vertex right = middleRight.right;
		long ans = middle != null ? middle.sum : 0;
		root = merge(left, merge(middle, right));
		return ans;
	}

	private StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb, Vertex root) {

		if (root == null) {
			sb.append("Tree Empty");
			return sb;
		}

		if (root.right != null) {
			toString(new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb, root.right);
		}
		sb.append(prefix).append(isTail ? "└── " : "┌── ").append(root.key).append("\n");
		if (root.left != null) {
			toString(new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb, root.left);
		}
		return sb;
	}

	@Override
	public String toString() {
		return this.toString(new StringBuilder(), true, new StringBuilder(), root).toString();
	}
}
