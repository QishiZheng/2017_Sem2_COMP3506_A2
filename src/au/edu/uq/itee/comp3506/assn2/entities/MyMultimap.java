package au.edu.uq.itee.comp3506.assn2.entities;

/**
 * Implementation of Multimap using MyMap and MyList
 * 
 * Memory Efficiency: O(n), for storing n map entries in the worst case.
 * 					  It would take less space if multiple map entries have same key
 * 					  since all values associate with same key will be put into the value list of that key.
 * 
 * @author Qishi Zheng <Student No. 43759453>.
 *
 * @param <K>	key
 * @param <V>	value
 */
public class MyMultimap<K,V> implements MultiMap<K, V> {
	Map<K, List<V>> map = new MyMap<>(); // the primary map
	int size = 0; // total number of entries in the multimap 
	
	/**
	 * Constructs an empty multimap.
	 * Runtime Efficiency: O(1), because this method only has constant operations.
	 */
	public MyMultimap() { }
	
	/**
	 * Get the total number of entries in the multimap.
	 * 
	 * Runtime Efficiency: O(1), because this method only has constant operations.
	 * 
	 * @return	the total number of entries in the multimap.
	 */
	@Override
	public int size() { return size; }
	
	/**
	 * Indicates whether the multimap is empty.
	 * 
	 * Runtime Efficiency: O(1), because this method only has constant operations.
	 * 
	 * @return	true if the multimap is empty.
	 */
	@Override
	public boolean isEmpty() { return (size == 0); }
	
	/**
	 * Returns a (possibly empty) list of all values associated with the key.
	 * 
	 * Runtime Efficiency: O(n), because MyMap needs to iterate through all entries
	 * 					   in order to get correct key.
	 * 
	 * @param key	the given key to search.
	 * @return	a (possibly empty) list of all values associated with the key.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<V> get(K key) {
		List<V> valueList = map.get(key); 
		if (valueList != null) {
			return valueList;
			}
		return new MyList();
	}
	
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
	@Override
	public void put(K key, V value) {
		List<V> valueList = map.get(key); 
		if (valueList == null) {
			valueList = new MyList<>();
			map.put(key, valueList); // begin using new list as secondary structure 
			}
		valueList.add(value);
		size++;
		}
}
