package au.edu.uq.itee.comp3506.assn2.entities;


public class MyList<E> implements List<E>{
    private int size = 0; //number of elements
    private static final int CAPACITY = 16; // default size of my ArrayList
    private E elements[]; // generic array used for storage

    
    //constructors 
    // constructs list with default capacity
    public MyList() { this(CAPACITY);} // constructs list with default capacity
    //constructs a list with given capacity
    @SuppressWarnings("unchecked")
	public MyList(int capacity) {
    	elements = (E[]) new Object[capacity];
    }
    
    /**
     * 
     * @return number of elements in the list
     */
    public int size() {return size;}

    /**
     * 
     * @return whether the list is empty
     */
    public boolean isEmpty() {return size == 0;}
    

    /**
     * returns the element at index i
     * @param i given index
     * @return the element at index i
     */
    public E get(int i) {
        if (i< 0 || i>= size) {
            throw new IndexOutOfBoundsException("Illegal Index: " + i );
        }
        return (E) elements[i];
    }
    
    /**
     * add a new element to the end of list, resize the list if list is full
     * @param e new element to be added to the list
     */
    public void add(E e) {
        if (size == elements.length) {
        	resize();
        }
        elements[size++] = e;
    }

    /**
     * Double the list size
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
