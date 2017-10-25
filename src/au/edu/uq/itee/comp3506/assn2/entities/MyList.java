package au.edu.uq.itee.comp3506.assn2.entities;

/**
 * List implementation using array.
 * Memory Efficiency: O(n) to store n elements.
 * 
 * 
 * 
 * @author Qishi Zheng <Student No. 43759453>.
 * @param <E>, elements store in the list.
 */
public class MyList<E> implements List<E>{
    private int size = 0; //number of elements.
    private static int CAPACITY = 16; // default size of my ArrayList.
    private E elements[]; // generic array used for storage.

    
    /**
     * Constructs a list with default capacity, which is 16.
     * Runtime Efficiency: O(1), because only one operation needed.
     * to construct the list with default capacity.
     */
    public MyList() { this(CAPACITY);} // constructs list with default capacity.
    
    /**
     * constructs a list with given capacity.
     * Runtime Efficiency: O(1), because this method only has constant operations.
     * @param capacity, capacity of this new list.
     */
    @SuppressWarnings("unchecked")
	public MyList(int capacity) {
    	elements = (E[]) new Object[capacity];
    }
    
    /**
     * Runtime Efficiency: O(1), because this method only has constant operations.
     * @return number of elements in the list.
     */
    public int size() {return size;}

    /**
     * Runtime Efficiency: O(1), because this method only has constant operations.
     * @return whether the list is empty.
     */
    public boolean isEmpty() {return size == 0;}
    

    /**
     * returns the element at index i.
     * Runtime Efficiency: O(1), because this method only has constant operations.
     * @param i given index.
     * @return the element at index i.
     */
    public E get(int i) {
        if (i< 0 || i>= size) {
            throw new IndexOutOfBoundsException("Illegal Index: " + i );
        }
        return (E) elements[i];
    }
    
    /**
     * add a new element to the end of list, resize the list if list is full.
     * Runtime Efficiency: O(n). The worst case is that the list is full, so wee need to call resize() method 
     * 					   for resizing the list. 
     * @param e new element to be added to the list.
     */
    public void add(E e) {
        if (size == elements.length) {
        	resize();
        }
        elements[size++] = e;
    }

    /**
     * Double the list capacity.
     * Runtime Efficiency: O(n), because this method needs to create a new array with doubled size. After that,
     * 					   in order to add all elements in old array to the new array, we need to iterate through
     * 					   the old array to get all elements.  
     */
    @SuppressWarnings("unchecked")
	private void resize() {
        int newCapacity = elements.length * 2;
        E[] temp = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
        	temp[i] = elements[i];
        }
        elements = temp;
    }
}
