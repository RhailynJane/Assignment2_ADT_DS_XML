package utilities;

import exceptions.EmptyQueueException;

/**
 * Queue Abstract Data Type (ADT) Interface
 *
 * A queue is a First-In-First-Out (FIFO) data structure where elements are added
 * at one end (called the "rear" or "back") and removed from the other end
 * (called the "front").
 *
 * Think of it like a line of people waiting - the first person in line is the
 * first person to be served and leave the line.
 *
 * @param <E> the type of elements stored in this queue
 */
public interface QueueADT<E> {

    /**
     * Adds an element to the rear (back) of the queue.
     *
     * @param element the element to be added
     * @throws NullPointerException if element is null
     */
    void enqueue(E element) throws NullPointerException;

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return the front element
     * @throws EmptyQueueException if the queue is empty
     */
    E dequeue() throws EmptyQueueException;

    /**
     * Returns the element at the front without removing it.
     *
     * @return the front element
     * @throws EmptyQueueException if the queue is empty
     */
    E peek() throws EmptyQueueException;

    /**
     * Checks if the queue is empty.
     *
     * @return true if empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the queue.
     *
     * @return the size of the queue
     */
    int size();

    /**
     * Removes all elements from the queue.
     */
    void clear();

    /**
     * Returns an array containing all elements, front at index 0.
     *
     * @return array of elements
     */
    Object[] toArray();

    /**
     * Compares this queue with another object for equality.
     *
     * @param obj the object to compare
     * @return true if equal
     */
    boolean equals(Object obj);

    /**
     * Returns an iterator over the elements in the queue.
     *
     * @return an iterator from front to rear
     */
    utilities.Iterator<E> iterator();

    /**
     * Removes all elements from the queue.
     * Same as clear().
     */
    void dequeueAll();
}
