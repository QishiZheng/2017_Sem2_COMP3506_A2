package au.edu.uq.itee.comp3506.assn2.entities;

//import au.edu.uq.itee.comp3506.assn2.entities.MyBSTree.Node;

public interface MyBinarySearchTree {
	
	//Find a node the tree with value n. Its O(lgn)
	boolean isExist(int id);
	
	//Add a node the tree with value n. Its O(lgn);
	void add(int id);
	
//	//Prints the entire tree in increas­ing order. O(n).
//	void dis­play(Node node);
}
