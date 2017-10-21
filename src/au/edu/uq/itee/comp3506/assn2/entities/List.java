package au.edu.uq.itee.comp3506.assn2.entities;

public interface List<E> {

	/**
	 * 
	 * @return the number of elements in the list
	 */
	int size();

	/**
	 * 
	 * @return whether the list is empty
	 */
	boolean isEmpty();
	
	/**
     * add a new element to the end of list, resize the list if list is full
     * @param e new element to be added to the list
     */
    void add(E e);
    
    /**
     * returns the element at index i
     * @param i given index
     * @return the element at index i
     */
    E get(int i);

}
