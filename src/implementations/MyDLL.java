package implementations;

import utilities.ListADT;
import utilities.Iterator;

/**
 * MyDLL - A doubly linked list implementation of the ListADT interface.
 * This class uses a chain of MyDLLNode objects to store elements.
 * Each node has references to both the next and previous nodes,
 * allowing for efficient insertion and deletion at any position.
 *
 * Key Features:
 * - Efficient insertion/deletion at any position (O(1) if you have the node)
 * - Bidirectional traversal (forward and backward)
 * - Dynamic size (grows and shrinks as needed)
 * - No wasted memory (only allocates what's needed)
 *
 * Structure: head <-> node1 <-> node2 <-> ... <-> nodeN <-> tail
 *
 * @param <E> the type of elements stored in this list
 *  @author Rhailyn Cona, Komalpreet Kaur, Anne Marie Ala, Abel Fekadu
 *  @version 1, July 5, 2025.
 */
public class MyDLL<E> implements ListADT<E> {

    /**
     * Reference to the first node in the list
     * null if the list is empty
     */
    private MyDLLNode<E> head;

    /**
     * Reference to the last node in the list
     * null if the list is empty
     */
    private MyDLLNode<E> tail;

    /**
     * The current number of elements in the list
     */
    private int size;

    /**
     * Constructs an empty doubly linked list.
     */
    public MyDLL() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Gets the node at the specified index.
     * This is a private helper method that optimizes traversal
     * by starting from the end closest to the target index.
     *
     * @param index the index of the node to retrieve
     * @return the node at the specified index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    private MyDLLNode<E> getNode(int index) {
        checkIndex(index);

        MyDLLNode<E> current;

        // Optimization: start from the end closest to the target index
        if (index < size / 2) {
            // Start from head and move forward
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            // Start from tail and move backward
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrevious();
            }
        }

        return current;
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
        // Help garbage collection by breaking all links
        MyDLLNode<E> current = head;
        while (current != null) {
            MyDLLNode<E> next = current.getNext();
            current.setData(null);
            current.setNext(null);
            current.setPrevious(null);
            current = next;
        }

        head = null;
        tail = null;
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

        // Create new node
        MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);

        // Case 1: Adding to empty list
        if (size == 0) {
            head = newNode;
            tail = newNode;
        }
        // Case 2: Adding at the beginning (index 0)
        else if (index == 0) {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        // Case 3: Adding at the end (index == size)
        else if (index == size) {
            newNode.setPrevious(tail);
            tail.setNext(newNode);
            tail = newNode;
        }
        // Case 4: Adding in the middle
        else {
            MyDLLNode<E> nodeAtIndex = getNode(index);
            MyDLLNode<E> previousNode = nodeAtIndex.getPrevious();

            // Set up the new node's links
            newNode.setNext(nodeAtIndex);
            newNode.setPrevious(previousNode);

            // Update the surrounding nodes' links
            previousNode.setNext(newNode);
            nodeAtIndex.setPrevious(newNode);
        }

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
        MyDLLNode<E> node = getNode(index);
        return node.getData();
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        checkIndex(index);

        MyDLLNode<E> nodeToRemove = getNode(index);
        E removedData = nodeToRemove.getData();

        // Case 1: Removing the only node
        if (size == 1) {
            head = null;
            tail = null;
        }
        // Case 2: Removing the first node
        else if (index == 0) {
            head = head.getNext();
            head.setPrevious(null);
        }
        // Case 3: Removing the last node
        else if (index == size - 1) {
            tail = tail.getPrevious();
            tail.setNext(null);
        }
        // Case 4: Removing a middle node
        else {
            MyDLLNode<E> previousNode = nodeToRemove.getPrevious();
            MyDLLNode<E> nextNode = nodeToRemove.getNext();

            // Connect the previous and next nodes to each other
            previousNode.setNext(nextNode);
            nextNode.setPrevious(previousNode);
        }

        // Help garbage collection
        nodeToRemove.setNext(null);
        nodeToRemove.setPrevious(null);
        nodeToRemove.setData(null);

        size--;
        return removedData;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null) {
            throw new NullPointerException("Cannot remove null element");
        }

        // Find the element and remove it
        MyDLLNode<E> current = head;
        int index = 0;

        while (current != null) {
            if (toRemove.equals(current.getData())) {
                return remove(index);
            }
            current = current.getNext();
            index++;
        }

        // Element not found
        return null;
    }

    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) {
            throw new NullPointerException("Cannot set element to null");
        }

        MyDLLNode<E> node = getNode(index);
        E oldData = node.getData();
        node.setData(toChange);

        return oldData;
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

        MyDLLNode<E> current = head;
        while (current != null) {
            if (toFind.equals(current.getData())) {
                return true;
            }
            current = current.getNext();
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
        MyDLLNode<E> current = head;
        int index = 0;
        while (current != null) {
            toHold[index++] = current.getData();
            current = current.getNext();
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
        MyDLLNode<E> current = head;
        int index = 0;

        while (current != null) {
            result[index++] = current.getData();
            current = current.getNext();
        }

        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new DLLIterator();
    }

    /**
     * Iterator implementation for MyDLL.
     * Provides a way to traverse the list elements sequentially from head to tail.
     */
    private class DLLIterator implements Iterator<E> {
        /**
         * Current node in the iteration
         */
        private MyDLLNode<E> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() throws java.util.NoSuchElementException {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No more elements in iteration");
            }

            E data = current.getData();
            current = current.getNext();
            return data;
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

        MyDLLNode<E> current = head;
        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) {
                sb.append(", ");
            }
            current = current.getNext();
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
        MyDLLNode<E> current = head;
        int index = 0;

        while (current != null) {
            if (!current.getData().equals(otherArray[index])) {
                return false;
            }
            current = current.getNext();
            index++;
        }

        return true;
    }

    /**
     * Additional utility method: Add element at the beginning of the list.
     * This is very efficient for linked lists (O(1) operation).
     *
     * @param element the element to add at the beginning
     * @throws NullPointerException if element is null
     */
    public void addFirst(E element) throws NullPointerException {
        add(0, element);
    }

    /**
     * Additional utility method: Add element at the end of the list.
     * This is very efficient for doubly linked lists (O(1) operation).
     *
     * @param element the element to add at the end
     * @throws NullPointerException if element is null
     */
    public void addLast(E element) throws NullPointerException {
        add(size, element);
    }

    /**
     * Additional utility method: Remove and return the first element.
     * This is very efficient for linked lists (O(1) operation).
     *
     * @return the first element in the list
     * @throws IndexOutOfBoundsException if the list is empty
     */
    public E removeFirst() throws IndexOutOfBoundsException {
        return remove(0);
    }

    /**
     * Additional utility method: Remove and return the last element.
     * This is very efficient for doubly linked lists (O(1) operation).
     *
     * @return the last element in the list
     * @throws IndexOutOfBoundsException if the list is empty
     */
    public E removeLast() throws IndexOutOfBoundsException {
        return remove(size - 1);
    }

    /**
     * Additional utility method: Get the first element without removing it.
     *
     * @return the first element in the list
     * @throws IndexOutOfBoundsException if the list is empty
     */
    public E getFirst() throws IndexOutOfBoundsException {
        return get(0);
    }

    /**
     * Additional utility method: Get the last element without removing it.
     *
     * @return the last element in the list
     * @throws IndexOutOfBoundsException if the list is empty
     */
    public E getLast() throws IndexOutOfBoundsException {
        return get(size - 1);
    }
}