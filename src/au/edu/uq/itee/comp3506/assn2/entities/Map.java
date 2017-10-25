package au.edu.uq.itee.comp3506.assn2.entities;

public interface Map<K, V> {

	/**
	 * Get the value associate with the given key.
	 * 
	 * Runtime efficiency: O(n), because this method needs to iterate through the map to compare the given key
	 * 					   and keys in array. So that if the key match, it returns the value associate with 
	 * 					   the given key, otherwise returns null.
	 * 
	 * @param key, the given key.
	 * @return the value associate with the given key.
	 */
	V get(K key);

	/**
	 * Add new key-value pair to the map. Duplicates are not allowed, the new map entry only can be added to map if
	 * there is no identical key in map.
	 * 
	 * Runtime Efficiency: O(n), because this method needs to iterate through the map to compare the given key with
	 * 					   keys in map so that duplicates will not be added to map. If length of the array is not
	 * 					   enough for adding the new entry, then we also need to call ensureCapacity() to increase
	 * 					   the length of entry array, which takes O(n) too.
	 * 					  
	 * @param key, given key.
	 * @param value, the value associate with the given key.
	 */
	void put(K key, V value);

	/**
	 * Returns the number of map entries in map
	 * Runtime Efficiency: O(1), because this method only has constant operations.
	 * @return number of elements in the map.
	 */
	int size();

}