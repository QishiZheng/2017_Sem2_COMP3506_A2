package au.edu.uq.itee.comp3506.assn2.entities;

public class MyBSTree implements MyBinarySearchTree {
	private Node root;
	private int size;

	public MyBSTree() {
		this.root = null;
		this.size = 0;
	}

	// Add a node the tree with value n. Its O(lgn);
	@Override
	public void add(int id) {
		// Create a new node to store this id
		Node newNode = new Node(id);

		// if root is null, which means the tree is empty, add the new node as
		// root
		if (root == null) {
			root = newNode;
			size++;
		} else {
			// start from root
			Node current = root;
			Node currentParent = null;
			while (current != null) {
				currentParent = current;
				if (id > current.id) {
					current = current.right;
					if (current == null) {
						currentParent.right = newNode;
						size++;
					}
				} else if (id < current.id) {
					current = current.left;
					if (current == null) {
						currentParent.left = newNode;
						size++;
					}
				}
			}
		}
	}

	// Check if the id exist in the tree. Its O(lgn)
	@Override
	public boolean isExist(int id) {
		// start at root;
		Node current = root;
		while (current != null) {
			if (id == current.id) { // if current node has the id we are looking
									// for
				return true;
			} else if (id > current.id) { // if id is greater than current.id
				current = current.right; // drop to right
			} else if (id < current.id) { // if id is smaller than current.id
				current = current.left; // drop to left
			}
		}
		return false;
	}

	// get the node with given id. Its O(lgn)
	public Node getNode(int id) {
		// start at root;
		Node current = root;
		while (current != null) {
			if (id == current.id) { // if current node has the id we are looking for
				return current;
			} else if (id > current.id) { // if id is greater than current.id
				current = current.right; // drop to right
			} else if (id < current.id) { // if id is smaller than current.id
				current = current.left; // drop to left
			}
		}
		return null;
	}

	/**
	 * increase the count of node with given id
	 * 
	 * @param id
	 */
	public void increaseCount(int id) {
		Node target = getNode(id);
		if (target != null) {
			target.count++;
		}
	}

//	/* To get max value in a binary tree . time: O(n)*/ 
//	public int getMaxCount(Node node) {
//		int max = node.count;
//		if (node.left != null) {
//			max = Math.max(max, getMaxCount(node.left));
//		}
//		if (node.right != null) {
//			max = Math.max(max, getMaxCount(node.right));
//		}
//		return max;
//	}
	
	/**
	 * Get the node with max count in tree. time: O(n)
	 * @param node
	 * @return the node with max count in tree.
	 */
	public Node getMaxCountNode(Node node) {
		Node maxNode = node;
		if (node.left != null) {
			maxNode = nodeWithMax(maxNode, getMaxCountNode(node.left));
		}
		if (node.right != null) {
			maxNode = nodeWithMax(maxNode, getMaxCountNode(node.right));
		}
		return maxNode;
	}
	
	/**
	 * Get the node with min count in tree. time: O(n)
	 * @param node
	 * @return the node with min count in tree.
	 */
	public Node getMinCountNode(Node node) {
		Node minNode = node;
		if (node.left != null) {
			minNode = nodeWithMin(minNode, getMinCountNode(node.left));
		}
		if (node.right != null) {
			minNode = nodeWithMin(minNode, getMinCountNode(node.right));
		}
		return minNode;
	}
	
	/**
	 * Returns the node with more counts between two nodes. 
	 * If two nodes have same count, then returns the one with greater ID number
	 * @param node1 first node
	 * @param node2 second node
	 * @return the node with more counts between two nodes
	 */
	private Node nodeWithMax(Node node1, Node node2) {
		int minID;
		if (node1.count > node2.count) {
			return node1;
		} else if (node1.count < node2.count){
			return node2;
		} else {
			minID = Math.min(node1.id, node2.id);
			if (node1.id == minID) {
				return node1;
			} else {
				return node2;
			}
		}
	}
	
	/**
	 * Returns the node with less counts between two nodes, 
	 * exclude node with count equals to 0. 
	 * if two nodes have same count, then returns the one with smaller ID number
	 *
	 * @param node1 first node
	 * @param node2 second node
	 * @return the node with less counts between two nodes. If two nodes have same count, then returns the one with smaller ID number
	 */
	private Node nodeWithMin(Node node1, Node node2) {
		int minID;
		if (node1.count == 0) {
			return node2;
		} else if (node2.count == 0) {
			return node1;
		} else {
			if (node1.count < node2.count) {
				return node1;
			} else if (node1.count > node2.count){
				return node2;
			} else {
				minID = Math.min(node1.id, node2.id);
				if (node1.id == minID) {
					return node1;
				} else {
					return node2;
				}
			}
		}
	}

	/**
	 * 
	 * @return size of the tree, that is the number of elements stored in the
	 *         tree
	 */
	public int size() {
		return this.size;
	}

	/**
	 * 
	 * @return the root node of this tree
	 */
	public Node getRoot() {
		return this.root;
	}

	/**
	 * Nested Node class for My Binary Search Tree
	 * 
	 * @author
	 *
	 * @param id
	 *            integer stored in this node
	 */
	public class Node {
		private int id;
		private Node left;
		private Node right;
		private int count;

		/**
		 * Constructs a node with data stored in it
		 * 
		 * @param data
		 *            data stored in this node
		 */
		public Node(int id) {
			this.id = id;
			left = null;
			right = null;
			count = 0;
		}
		
		/**
		 * 
		 * @return id of this node
		 */
		public int getID() {
			return this.id;
		}
		
		/**
		 * 
		 * @return count stored in this node.
		 */
		public int getCount() {
			return this.count;
		}
	}

//	public static void main(String arg[]) {
//		MyBSTree b = new MyBSTree();
//		b.add(3);
//		b.add(8);
//		b.add(1);
//		b.add(4);
//		b.add(6);
//		b.add(2);
//		b.add(10);
//		b.add(9);
//		b.add(20);
//		b.add(25);
//		b.add(15);
//		b.add(16);
//		System.out.println("Original Tree Size : ");
//		// b.dis­play(b.root);
//		System.out.println("\n" + b.size());
//		System.out.println("");
//		System.out.println("Check whether Node with value 45 exists : " + b.isExist(4));
//		b.increaseCount(3);
//		b.increaseCount(15);b.increaseCount(15);
//		b.increaseCount(15);
//		b.increaseCount(20);b.increaseCount(20);b.increaseCount(20);b.increaseCount(20);
//		System.out.println("Max Cout Node is: " + b.getMaxCountNode(b.root).id);
//	}

}