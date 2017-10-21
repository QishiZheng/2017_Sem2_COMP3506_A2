package au.edu.uq.itee.comp3506.assn2.entities;

import java.util.Random;

public class SkipList implements MySkipList{

	private static class Node {
		private long key;
		private List<CallRecord> callRecs;
		private Node up, down, left, right;
		
		private static long negInfi = Long.MIN_VALUE;//use minimum value of long as negative infinity
		private static long posInfi = Long.MAX_VALUE;//use maximum value of long as negative infinity
		
		public Node(long key, Node node) {
			// TODO Auto-generated constructor stub
		}
	}
	
	 private Node head;    // First element of the top level
     private Node tail;    // Last element of the top level
    
     private  int n;                 // number of entries in the Skip List    
    
     private int h;       // Height
     private Random r;    // Coin toss
    
     public SkipList() { 
    	 Node p1 = new Node(Node.negInfi, null);
    	 Node p2 = new Node(Node.posInfi, null);
    
    	 head = p1;
    	 tail = p2;
    
    	 p1.right = p2;
    	 p2.left = p1;
    
    	 n = 0;
    	 h = 0;
    	 r = new Random();
     }
	
	/**
	 * Find the node with given number as key
	 * @param numebr, given long number key
	 * @return the corresponding skipList entry
	 */
	@Override
	public Node findNode(Long key) {
		Node p;
	     /* -----------------
	        Start at "head"
	        ----------------- */
	     p = head;

	     while ( true )
	     {
	        /* ------------------------------------------------
	           Search RIGHT until you find a LARGER entry

	           E.g.: k = 34

	                     10 ---> 20 ---> 30 ---> 40
	                                      ^
	                                      |
	                                      p must stop here
			p.right.key = 40
	           ------------------------------------------------ */           
	        while ( (p.right.key) != Node.posInfi &&
	                 p.right.key > key)
	        {
	           p = p.right;         // Move to right
	        }

	        /* ---------------------------------
	           Go down one level if you can...
	           --------------------------------- */
	        if ( p.down != null )
	        {  
	           p = p.down;          // Go downwards
	        }
	        else
		{
	           break;       // We reached the LOWEST level... Exit...
	        }
	     }

	     return(p);         // Note: p.key <= k
	}
	

	 
	  /** Returns the list of callRecs that store in the node associated with a key. */               

	  public List<CallRecord> get (Long key)
	  {
	     Node p;

	     p = findNode(key);

	     if ( key.equals( p.key ) )
	        return(p.callRecs);
	     else
	        return(null);
	  }

	@Override
	public boolean insert(long number, CallRecord callRec) {
		// TODO Auto-generated method stub
		return false;
	}

}
