package utilities;

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
 * @author Your Group Name
 * @version 1.0
 */
public interface QueueADT<E> {

    /**
     * Adds an element to the rear (back) of the queue.
     * This operation is also called "enqueue".
     *
     * @param element the element to be added to the rear of the queue
     * @throws NullPointerException if the specified element is null
     * @post the element is added to the rear of the queue
     * @post the size of the queue increases by 1
     */
    void enqueue(E element) throws NullPointerException;

    /**
     * Removes and returns the element at the front of the queue.
     * This operation is also called "dequeue".
     *
     * @return the element that was at the front of the queue
     * @throws EmptyQueueException if the queue is empty
     * @post the front element is removed from the queue
     * @post the size of the queue decreases by 1
     */
    E dequeue() throws EmptyQueueException;

    /**
     * Returns the element at the front of the queue without removing it.
     * This operation is also called "peek" or "front".
     *
     * @return the element at the front of the queue
     * @throws EmptyQueueException if the queue is empty
     * @post the queue remains unchanged
     */
    E peek() throws EmptyQueueException;

    /**
     * Tests if the queue is empty.
     *
     * @return true if the queue contains no elements, false otherwise
     * @post the queue remains unchanged
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the queue.
     *
     * @return the number of elements currently in the queue
     * @post the queue remains unchanged
     */
    int size();

    /**
     * Removes all elements from the queue.
     * After this operation, the queue will be empty.
     *
     * @post the queue is empty
     * @post size() returns 0
     * @post isEmpty() returns true
     */
    void clear();

    /**
     * Returns an array containing all elements in the queue.
     * The front element of the queue is at index 0 of the returned array.
     *
     * @return an array containing all elements in the queue, with the front element first
     * @post the queue remains unchanged
     */
    Object[] toArray();

    /**
     * Compares the specified object with this queue for equality.
     * Two queues are equal if they contain the same elements in the same order.
     *
     * @param obj the object to be compared for equality with this queue
     * @return true if the specified object is equal to this queue, false otherwise
     * @post the queue remains unchanged
     */
    boolean equals(Object obj);

    /**
     * Returns an iterator over the elements in this queue.
     * The iterator will traverse the queue from front to rear.
     *
     * @return an iterator over the elements in this queue
     * @post the queue remains unchanged
     */
    java.util.Iterator<E> iterator();
}