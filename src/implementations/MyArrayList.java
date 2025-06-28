package implementations;

import utilities.ListADT;
import utilities.Iterator;

/**
 * MyArrayList - A resizable array implementation of the ListADT interface.
 *
 * This class uses an underlying array to store elements. When the array becomes
 * full, it automatically resizes to accommodate more elements.
 *
 * Key Features:
 * - Dynamic resizing (grows automatically when needed)
 * - Index-based access (fast random access)
 * - Maintains insertion order
 *
 * @param <E> the type of elements stored in this list
 * @author Your Group Name
 * @version 1.0
 */
public class MyArrayList<E> implements ListADT<E> {

    /**
     * Default initial capacity of the array
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * The array that stores the elements
     */
    private E[] array;

    /**
     * The current number of elements in the list
     */
    private int size;

    /**
     * Constructs an empty list with default initial capacity.
     */
    @SuppressWarnings("unchecked")
    public MyArrayList() {
        // Create array of Object type and cast to E[]
        // This is necessary because we can't create arrays of generic types
        array = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Constructs an empty list with specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if initialCapacity is negative
     */
    @SuppressWarnings("unchecked")
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity cannot be negative: " + initialCapacity);
        }
        array = (E[]) new Object[initialCapacity];
        size = 0;
    }

    /**
     * Ensures that the array has enough capacity to hold more elements.
     * If the array is full, it doubles the capacity.
     */
    private void ensureCapacity() {
        if (size >= array.length) {
            // Double the capacity (or set to 1 if current capacity is 0)
            int newCapacity = array.length == 0 ? 1 : array.length * 2;

            // Use Arrays.copyOf to create a new larger array
            array = java.util.Arrays.copyOf(array, newCapacity);
        }
    }

    /**
     * Checks if the given index is valid for accessing elements.
     *
     * @param index the index to check
     * @throws IndexOutOfBoundsException if index is invalid
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * Checks if the given index is valid for adding elements.
     * Valid range is 0 to size (inclusive).
     *
     * @param index the index to check
     * @throws IndexOutOfBoundsException if index is invalid
     */
    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        // Set all elements to null to help garbage collection
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        // Check for null element
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null element to list");
        }

        // Check if index is valid for adding
        checkIndexForAdd(index);

        // Ensure we have enough capacity
        ensureCapacity();

        // Shift elements to the right to make room for new element
        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }

        // Insert the new element
        array[index] = toAdd;
        size++;

        return true;
    }

    @Override
    public boolean add(E toAdd) throws NullPointerException {
        // Add at the end of the list
        return add(size, toAdd);
    }

    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null list");
        }

        // Convert toAdd to array and add each element
        Object[] elementsToAdd = toAdd.toArray();
        boolean modified = false;

        for (Object element : elementsToAdd) {
            @SuppressWarnings("unchecked")
            E e = (E) element;
            add(e);
            modified = true;
        }

        return modified;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        checkIndex(index);
        return array[index];
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        checkIndex(index);

        E removedElement = array[index];

        // Shift elements to the left to fill the gap
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }

        // Clear the last element and decrease size
        array[size - 1] = null;
        size--;

        return removedElement;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null) {
            throw new NullPointerException("Cannot remove null element");
        }

        // Find the element and remove it
        for (int i = 0; i < size; i++) {
            if (toRemove.equals(array[i])) {
                return remove(i);
            }
        }

        // Element not found
        return null;
    }

    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) {
            throw new NullPointerException("Cannot set element to null");
        }

        checkIndex(index);

        E oldElement = array[index];
        array[index] = toChange;

        return oldElement;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null element");
        }

        for (int i = 0; i < size; i++) {
            if (toFind.equals(array[i])) {
                return true;
            }
        }

        return false;
    }

    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException("Destination array cannot be null");
        }

        // If the provided array is too small, create a new one
        if (toHold.length < size) {
            @SuppressWarnings("unchecked")
            E[] newArray = (E[]) java.lang.reflect.Array.newInstance(
                    toHold.getClass().getComponentType(), size);
            toHold = newArray;
        }

        // Copy elements to the destination array
        for (int i = 0; i < size; i++) {
            toHold[i] = array[i];
        }

        // If destination array is larger, set the next element to null
        if (toHold.length > size) {
            toHold[size] = null;
        }

        return toHold;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = array[i];
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    /**
     * Iterator implementation for MyArrayList.
     * Provides a way to traverse the list elements sequentially.
     */
    private class ArrayListIterator implements Iterator<E> {
        /**
         * Current position in the iteration
         */
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public E next() throws java.util.NoSuchElementException {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No more elements in iteration");
            }

            return array[currentIndex++];
        }
    }

    /**
     * Returns a string representation of this list.
     * Format: [element1, element2, element3, ...]
     *
     * @return a string representation of this list
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < size; i++) {
            sb.append(array[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    /**
     * Compares this list with another object for equality.
     * Two lists are equal if they have the same size and contain
     * the same elements in the same order.
     *
     * @param obj the object to compare with
     * @return true if the lists are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof ListADT)) return false;

        @SuppressWarnings("unchecked")
        ListADT<E> other = (ListADT<E>) obj;

        if (this.size() != other.size()) return false;

        Object[] otherArray = other.toArray();
        for (int i = 0; i < size; i++) {
            if (!array[i].equals(otherArray[i])) {
                return false;
            }
        }

        return true;
    }
}