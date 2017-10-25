package au.edu.uq.itee.comp3506.assn2.entities;


/**
 * Implementation of Map using array of key-value pairs 
 * Memory Efficiency: O(n), for storing n map entries.
 * 
 * @author Qishi Zheng <Student No. 43759453>.
 *
 * @param <K> Key
 * @param <V> Value
 */
public class MyMap<K, V> implements Map<K, V> {
    private int size;	//indicating how many elements in map
    private int CAPACITY = 16;	//indicating capacity of map
    @SuppressWarnings("unchecked")
    private MyMapEntry<K, V>[] values = new MyMapEntry[CAPACITY];

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
    @Override
	public V get(K key) {
        for (int i = 0; i < size; i++) {
            if (values[i] != null) {
                if (values[i].getKey().equals(key)) {
                    return values[i].getValue();
                }
            }
        }
        return null;
    }

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
    @Override
	public void put(K key, V value) {
        boolean insert = true;
        for (int i = 0; i < size; i++) {
            if (values[i].getKey().equals(key)) {
                values[i].setValue(value);
                insert = false;
            }
        }
        if (insert) {
        	ensureCapacity();
            values[size++] = new MyMapEntry<K, V>(key, value);
        }
    }

//    private void ensureCapacity() {
//        if (size == values.length) {
//            int newSize = values.length * 2;
//            values = Arrays.copyOf(values, newSize);
//        }
//    }

    /**
     * Ensure the capacity of array in the map. Doubles the capacity 
     * if size of map is equal to the length of entry array.
     * Runtime Efficiency: O(n), needs to iterate through old array in order to add all elements to new array.
     */
    @SuppressWarnings("unchecked")
	private void ensureCapacity() {
    	if (size == values.length) {
    		int newCapa = values.length * 2;
        	MyMapEntry<K, V>[] temp = new MyMapEntry[newCapa];
        	for (int i = 0; i < size; i++) {
        		temp[i] = values[i];
        	} 
        	values = temp;
    	} 
    }

    /**
	 * Returns the number of map entries in map
	 * Runtime Efficiency: O(1), because this method only has constant operations.
	 * @return number of elements in the map.
	 */
    @Override
	public int size() {
        return size;
    }

    /**
     * Nested entry class for implementation of MyMap class.
     *
     * @author Qishi Zheng <Student No. 43759453>.
     *
     * @param <K>	key
     * @param <V>	value
     */
    @SuppressWarnings("hiding")
	private class MyMapEntry<K, V> {
        private K key;
        private V value;
        
        /**
         * Constructs a mapEntry with a key-value pair
         * @param key	given key
         * @param value	given value
         */
        private MyMapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Get the key in this entry.
         * 
         * Runtime Efficiency: O(1), because this method only has constant operations.
         * 
         * @return	the key in this entry
         */
        public K getKey() {
            return key;
        }

        /**
         * Get the value in this entry.
         * 
         * Runtime Efficiency: O(1), because this method only has constant operations.
         * 
         * @return	the value in this entry
         */
        public V getValue() {
            return value;
        }

        /**
         * Set the value in this entry to the given value.
         * 
         * Runtime Efficiency: O(1), because this method only has constant operations.
         * 
         */
        public void setValue(V value) {
            this.value = value;
        }
    }
}