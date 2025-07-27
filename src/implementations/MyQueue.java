package implementations;

import exceptions.EmptyQueueException;
import utilities.QueueADT;
import utilities.Iterator;

import java.util.NoSuchElementException;

/**
 * Array-based implementation of the Queue Abstract Data Type (ADT).
 * This implementation uses a circular array to efficiently manage space
 * and provides O(1) enqueue and dequeue operations (amortized).
 *  @author Rhailyn Cona, Komalpreet Kaur, Anne Marie Ala, Abel Fekadu Samuel Braun
 *  @version 1, July 5, 2025.
 * @param <E> the type of elements stored in this queue
 */
public class MyQueue<E> implements QueueADT<E> {

    /**
     * Default initial capacity of the queue
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Array to store queue elements
     */
    private E[] queue;

    /**
     * Index of the front element in the queue
     */
    private int front;

    /**
     * Index where the next element will be inserted (rear + 1)
     */
    private int rear;

    /**
     * Current number of elements in the queue
     */
    private int size;


    /**
     * Constructs an empty queue with default capacity.
     */
    @SuppressWarnings("unchecked")
    public MyQueue() {
        queue = (E[]) new Object[DEFAULT_CAPACITY];
        front = 0;
        rear = 0;
        size = 0;
    }

    /**
     * Constructs an empty queue with specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the queue
     * @throws IllegalArgumentException if initialCapacity is less than 1
     */
    @SuppressWarnings("unchecked")
    public MyQueue(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Initial capacity must be at least 1");
        }
        queue = (E[]) new Object[initialCapacity];
        front = 0;
        rear = 0;
        size = 0;
    }

    /**
     * Adds an element to the rear (back) of the queue.
     * Resizes array if necessary.
     *
     * @param element element to add
     * @throws NullPointerException if element is null
     */
    @Override
    public void enqueue(E element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException("Cannot enqueue null element");
        }

        if (size == queue.length) {
            resize();
        }

        queue[rear] = element;
        rear = (rear + 1) % queue.length;
        size++;
    }

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return the front element
     * @throws EmptyQueueException if queue is empty
     */
    @Override
    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Cannot dequeue from empty queue");
        }

        E element = queue[front];
        queue[front] = null; // Help garbage collection
        front = (front + 1) % queue.length;
        size--;

        return element;
    }
    /**
     * Compares this queue to another QueueADT for equality.
     * Two queues are equal if they contain the same elements in the same order.
     *
     * @param that the QueueADT to compare to
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(QueueADT<E> that) {
        if (this == that) return true;
        if (that == null || this.size() != that.size()) return false;

        Iterator<E> thisIter = this.iterator();
        Iterator<E> thatIter = that.iterator();

        while (thisIter.hasNext() && thatIter.hasNext()) {
            E e1 = thisIter.next();
            E e2 = thatIter.next();
            if (e1 == null ? e2 != null : !e1.equals(e2)) {
                return false;
            }
        }
        return true;
    }



    /**
     * Returns the element at the front without removing it.
     *
     * @return the front element
     * @throws EmptyQueueException if queue is empty
     */
    @Override
    public E peek() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Cannot peek at empty queue");
        }
        return queue[front];
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return number of elements
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes all elements from the queue.
     */
    public void clear() {
        for (int i = 0; i < queue.length; i++) {
            queue[i] = null;
        }
        front = 0;
        rear = 0;
        size = 0;
    }

    /**
     * Removes all elements from the queue.
     * Same as clear().
     */
    @Override
    public void dequeueAll() {
        clear();
    }

    /**
     * Returns an array containing all elements in the queue.
     * The front element is at index 0 of the array.
     *
     * @return array of queue elements
     */
    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = queue[(front + i) % queue.length];
        }
        return result;
    }

    /**
     * Converts the queue to an array of the same runtime type as the specified array.
     * The front element is at index 0.
     *
     * @param holder the array into which the elements of the queue are to be stored,
     *               if it is big enough; otherwise, a new array of the same runtime type is allocated.
     * @return an array containing the elements of the queue
     * @throws NullPointerException if the holder array is null
     */
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] holder) {
        if (holder == null) {
            throw new NullPointerException("Holder array cannot be null");
        }

        if (holder.length < size) {
            // Create a new array of the same runtime type as holder with size equal to queue size
            holder = (E[]) java.lang.reflect.Array.newInstance(holder.getClass().getComponentType(), size);
        }

        // Copy elements from queue in order
        for (int i = 0; i < size; i++) {
            holder[i] = queue[(front + i) % queue.length];
        }

        if (holder.length > size) {
            holder[size] = null; // null-terminate if bigger than needed
        }

        return holder;
    }


    /**
     * Returns a custom iterator over this queue.
     * Traverses elements from front to rear.
     *
     * @return an iterator for this queue
     */
    @Override
    public Iterator<E> iterator() {
        return new QueueIterator();
    }

    /**
     * Custom iterator implementation for MyQueue.
     */
    private class QueueIterator implements Iterator<E> {
        private int current = front;
        private int remaining = size;

        @Override
        public boolean hasNext() {
            return remaining > 0;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in queue");
            }
            E element = queue[current];
            current = (current + 1) % queue.length;
            remaining--;
            return element;
        }
    }

    /**
     * Returns a string representation of the queue.
     * Elements are listed from front to rear.
     *
     * @return string representation of queue
     */
    @Override
    public String toString() {
        if (isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(queue[(front + i) % queue.length]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Resizes the internal array when full.
     * Doubles the capacity and rearranges elements starting at index 0.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = queue.length * 2;
        E[] newQueue = (E[]) new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[(front + i) % queue.length];
        }

        queue = newQueue;
        front = 0;
        rear = size;
    }

    /**
     * Returns true if the queue is full.
     * Since this queue resizes dynamically, this always returns false.
     *
     * @return false always
     */
    public boolean isFull() {
        return false;
    }

    /**
     * Returns true if the queue contains the specified element.
     * Uses equals() to check for equality.
     *
     * @param target the element to search for
     * @return true if the element is found, false otherwise
     */
    public boolean contains(E target) {
        if (target == null) {
            throw new NullPointerException("Cannot search for null element");
        }
        for (int i = 0; i < size; i++) {
            E current = queue[(front + i) % queue.length];
            if (target.equals(current)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the 1-based position of the element in the queue starting from front.
     * Returns -1 if the element is not found.
     *
     * @param target the element to search for
     * @return position from front (1-based), or -1 if not found
     */
    public int search(E target) {
        if (target == null) {
            throw new NullPointerException("Cannot search for null element");
        }
        for (int i = 0; i < size; i++) {
            E current = queue[(front + i) % queue.length];
            if (target.equals(current)) {
                return i + 1;
            }
        }
        return -1;
    }



}
