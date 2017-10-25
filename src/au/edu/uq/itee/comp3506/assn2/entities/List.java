package au.edu.uq.itee.comp3506.assn2.entities;

public interface List<E> {

	/**
     * Runtime Efficiency: O(1), because this method only has constant operations.
     * @return number of elements in the list.
     */
	int size();

	/**
     * Runtime Efficiency: O(1), because this method only has constant operations.
     * @return whether the list is empty.
     */
	boolean isEmpty();
	
	 /**
     * add a new element to the end of list, resize the list if list is full.
     * Runtime Efficiency: O(n). The worst case is that the list is full, so wee need to call resize() method 
     * 					   for resizing the list. 
     * @param e new element to be added to the list.
     */
    void add(E e);
    
    /**
     * returns the element at index i.
     * Runtime Efficiency: O(1), because this method only has constant operations.
     * @param i given index.
     * @return the element at index i.
     */
    E get(int i);

}
