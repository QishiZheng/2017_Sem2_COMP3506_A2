package au.edu.uq.itee.comp3506.assn2.entities;

import au.edu.uq.itee.comp3506.assn2.entities.MyBSTree.Node;

public class MyBSTreeMap<K extends Comparable<K>, V> {
	private Node root;
	private int size;
	
	public MyBSTreeMap() {
		root = null;
		size = 0;
	}
	
	public void add(K key, V value) {
		//Create a new node to store key and value
		Node newNode = new Node(key, value);
		//if root is null, new node become root
		if (root == null) {
			root = newNode;
			size++;
		} 
	}
		
		
	
	
	
	/**
	 * Nested node class for MYBSTreeMap 
	 * @author VinceZ
	 *
	 * @param <K> Key of node
	 * @param <V> value stored in this node
	 */
	class Node<K, V> {
		private K key;
		private List<V> valueList;
		private Node parent, left, right;
		
		public Node(K key, V value) {
			this.key = key;
			this.valueList.add(value);
			this.left = null;
			this.right = null;
			this.parent = null;
		} 
		
		public Node getLeft() {
			return left;
		}
		public void setLeft(Node left) {
			this.left = left;
		}
		
		public Node getRight() {
			return right;
		}
		
		public void setRight(Node right) {
			this.right = right;
		}
		
		public Node getParent() {
			return parent;
		}
		public void setParent(Node parent) {
			this.parent = parent;
		}
		
		public K getKey() {
			return key;
		}
		
		public List<V> getValue() {
			return valueList;
		}
		public void addValue(V value) {
			this.valueList.add(value);
		}
		
	}
}
