package au.edu.uq.itee.comp3506.assn2.entities;

/**
 * Implemetation of Multimap using map and list
 * 
 * Reference: textbook pp.449-450
 * 
 * @author Qishi Zheng
 *
 * @param <K>
 * @param <V>
 */
public class MyMultimap<K,V> {
	MyMap<K,List<V>> map = new MyMap<>(); // the primary map
	int size = 0; // total number of entries in the multimap 
	
	//∗∗ Constructs an empty multimap. ∗/
	public MyMultimap() { }
	
	//Returns the total number of entries in the multimap.
	public int size() { return size; }
	
	//∗∗ Returns whether the multimap is empty. ∗/
	public boolean isEmpty() { return (size == 0); }
	
	//∗∗ Returns a (possibly empty) list of all values associated with the key. ∗/
	@SuppressWarnings("unchecked")
	public List<V> get(K key) {
		List<V> valueList = map.get(key); 
		if (valueList != null) {
			return valueList;
			}
		return new MyList();
	}
	
	// Adds a new entry associating key with value.
	public void put(K key, V value) {
		List<V> valueList = map.get(key); 
		if (valueList == null) {
			valueList = new MyList<>();
			map.put(key, valueList); // begin using new list as secondary structure 
			}
		valueList.add(value);
		size++;
		}
	
//	//∗∗ Returns an iteration of all entries in the multimap. ∗/ 
//	Iterable<MyMap.MyMapEntry<K,V>> entries() {
//		List<Map.Entry<K,V>> result = new LinkedList<>();
//		for (Map.Entry<K,List<V>> secondary : map.entrySet()) {
//		K key = secondary.getKey();
//		for (V value : secondary.getValue())
//		result.add(new AbstractMap.SimpleEntry<K,V>(key,value)); }
//		return result; }
}
