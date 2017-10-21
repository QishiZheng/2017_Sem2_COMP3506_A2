package au.edu.uq.itee.comp3506.assn2.entities;

public interface MySkipList<T> {

	/**
	 * Find the entry with given number as key
	 * @param numebr, given long number key
	 * @return the corresponding skipList entry
	 */
	T findNode(Long key); 
	
	/**
	 * Insert a new key-value pair to the skip-list
	 * @param key long number key
	 * @param value, a call record 
	 * @return true if insert was successful
	 */
	boolean insert(long key, CallRecord value);
	
}
