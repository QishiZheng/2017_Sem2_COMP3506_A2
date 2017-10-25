package au.edu.uq.itee.comp3506.assn2.entities;

/**
 * Implementation of MyBinarySearchTree.
 * 
 * Memory Efficiency: O(n), it would take n space in memory to store n elements in the binary search tree.
 * 					  Each node stored an ID and a count of that id, so to more exactly it is 2n.
 * 
 * @author Qishi Zheng <Student No. 43759453>.
 *
 */
public class MyBSTree implements MyBinarySearchTree {
	private Node root;
	private int size;

	/**
	 * Constructs an empty MyBSTree.
	 * 
	 *  Runtime Efficiency: O(1), because this method only has constant operations.
	 */
	public MyBSTree() {
		this.root = null;
		this.size = 0;
	}


	/**
	 * Add a node into the tree with value n.
	 * 
	 * Runtime Efficiency: O(logn), it takes O(logn) time to find correct place in the tree to insert the new id.
	 *
	 *@param id,	the id to insert.
	 */
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

	/**
	 * Check if the id exist in the tree. 
	 * 
	 * Runtime Efficiency: O(logn), it takes O(logn) time to find the given id from root.
	 * 
	 * @param id,	the given id to check.
	 * @return trure if the given id is found in the tree.
	 */
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

	/*
	 * Get the node with given id. Its O(logn)
	 * 
	 * Runtime Efficiency: O(logn), it takes O(logn) time to find the node with given id from root.
	 * 
	 * @param id,  given id of node.
	 * @return	the node with given id.
	 */
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
	 * Increase the count of node with given id
	 * 
	 * Runtime Efficiency: O(logn), it takes O(logn) time to find the node with given id from root.
	 * 
	 * @param id,	the id of node that we want to increase count
	 */
	public void increaseCount(int id) {
		Node target = getNode(id);
		if (target != null) {
			target.count++;
		}
	}

	
	/**
	 * Get the node with max count in tree.
	 * 
	 * Runtime Efficiency: O(n),  because we need to iterate through
	 *  					the whole tree in order to find the node with greatest count.
	 * 
	 * @param node	the top node in the tree/subtree to search from.
 	 * @return the node with max count in tree/subtree from node.
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
	 * Get the node with min counts in tree.
	 * 
	 *  Runtime Efficiency: O(n),  because we need to iterate through
	 *  					the whole tree in order to find the node with smallest count.
	 * 
	 * @param node	the top node in the tree/subtree to search from.
 	 * @return the node with min count in tree/subtree from node.
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
	 * Returns the node with greater counts between two nodes. 
	 * If two nodes have same count, then returns the one with smaller ID number.
	 * 
	 * Runtime Efficiency: O(1), because this method only has constant operations.
	 * 
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
	 * Runtime Efficiency: O(1), because this method only has constant operations.
	 *
	 * @param node1 first node
	 * @param node2 second node
	 * @return the node with less counts between two nodes.
	 * 
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
	 * Return the number of elements in the tree.
	 * 
	 * Runtime Efficiency: O(1), because this method only has constant operations.
	 * 
	 * @return size of the tree, that is the number of elements stored in the tree.
	 *         
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Returns the root node in the tree. 
	 * 
	 * Runtime Efficiency: O(1), because this method only has constant operations.
	 * 
	 * @return the root node of this tree.
	 */
	public Node getRoot() {
		return this.root;
	}

	/**
	 * Nested Node class for My Binary Search Tree.
	 *
	 * Memory Efficiency: O(n), it would take 3n space in memory to store n node 
	 * 							since each node has left and right node.
	 * 
	 * @author Qishi Zheng <Student No. 43759453>.
	 *
	 */
	public class Node {
		private int id;
		private Node left;
		private Node right;
		private int count;

		/**
		 * Constructs a node with id stored in it.
		 * 
		 * Runtime Efficiency: O(1), because this method only has constant operations.
		 * 
		 * @param id	id stored in this node.
		 */
		public Node(int id) {
			this.id = id;
			left = null;
			right = null;
			count = 0;
		}
		
		/**
		 * Get the ID stored in this node.
		 *
		 * Runtime Efficiency: O(1), because this method only has constant operations.
		 * 
		 * @return id of this node
		 */
		public int getID() {
			return this.id;
		}
		
		/**
		 * Get the counts of ID in the node.
		 * 
		 * Runtime Efficiency: O(1), because this method only has constant operations.
		 * 
		 * @return count stored in this node.
		 */
		public int getCount() {
			return this.count;
		}
	}
}