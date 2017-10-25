package au.edu.uq.itee.comp3506.assn2.entities;

public interface MultiMap<K, V> {

	/**
	 * Get the total number of entries in the multimap.
	 * 
	 * Runtime Efficiency: O(1), because this method only has constant operations.
	 * 
	 * @return	the total number of entries in the multimap.
	 */
	int size();

	/**
	 * Indicates whether the multimap is empty.
	 * 
	 * Runtime Efficiency: O(1), because this method only has constant operations.
	 * 
	 * @return	true if the multimap is empty.
	 */
	boolean isEmpty();

	/**
	 * Returns a (possibly empty) list of all values associated with the key.
	 * 
	 * Runtime Efficiency: O(n), because MyMap needs to iterate through all entries
	 * 					   in order to get correct key.
	 * 
	 * @param key	the given key to search.
	 * @return	a (possibly empty) list of all values associated with the key.
	 */
	List<V> get(K key);

	/**
	 * Adds a new entry associating key with value.
	 * 
	 * Runtime Efficiency: O(n), because MyMap needs to iterate through all entries
	 * 					   in order to find if there is the given key exists in the map.
	 * 					   Then add the value to the valueList associating the key if the 
	 * 					   given key exists in the map. 
	 * 
	 * @param key	given key.	
	 * @param value	the value associating the given key.
	 */
	void put(K key, V value);

}