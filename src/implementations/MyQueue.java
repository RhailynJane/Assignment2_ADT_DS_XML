package implementations;

import utilities.QueueADT;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Array-based implementation of the Queue Abstract Data Type (ADT).
 * This implementation uses a circular array to efficiently manage space
 * and provides O(1) enqueue and dequeue operations (amortized).
 *
 * @param <E> the type of elements stored in this queue
 * @author Your Group Name
 * @version 1.0
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
     * Index of the front element
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

    @Override
    public void enqueue(E element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException("Cannot enqueue null element");
        }

        // Check if we need to resize the array
        if (size == queue.length) {
            resize();
        }

        queue[rear] = element;
        rear = (rear + 1) % queue.length;
        size++;
    }

    @Override
    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Cannot dequeue from empty queue");
        }

        E element = queue[front];
        queue[front] = null; // Help GC
        front = (front + 1) % queue.length;
        size--;

        return element;
    }

    @Override
    public E peek() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Cannot peek at empty queue");
        }

        return queue[front];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        // Help garbage collection
        for (int i = 0; i < queue.length; i++) {
            queue[i] = null;
        }
        front = 0;
        rear = 0;
        size = 0;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];

        for (int i = 0; i < size; i++) {
            result[i] = queue[(front + i) % queue.length];
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof QueueADT)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        QueueADT<E> other = (QueueADT<E>) obj;

        if (this.size() != other.size()) {
            return false;
        }

        // Compare elements using iterators
        Iterator<E> thisIter = this.iterator();
        Iterator<E> otherIter = other.iterator();

        while (thisIter.hasNext() && otherIter.hasNext()) {
            E thisElement = thisIter.next();
            E otherElement = otherIter.next();

            if (thisElement == null) {
                if (otherElement != null) {
                    return false;
                }
            } else if (!thisElement.equals(otherElement)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return new QueueIterator();
    }

    /**
     * Resizes the internal array when it becomes full.
     * Doubles the capacity and reorganizes elements to start from index 0.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = queue.length * 2;
        E[] newQueue = (E[]) new Object[newCapacity];

        // Copy elements in order from front to rear
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[(front + i) % queue.length];
        }

        queue = newQueue;
        front = 0;
        rear = size;
    }

    /**
     * Iterator implementation for the queue.
     * Traverses elements from front to rear.
     */
    private class QueueIterator implements Iterator<E> {
        private int current;
        private int remaining;

        public QueueIterator() {
            current = front;
            remaining = size;
        }

        @Override
        public boolean hasNext() {
            return remaining > 0;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in queue");
            }

            E element = queue[current];
            current = (current + 1) % queue.length;
            remaining--;

            return element;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported by queue iterator");
        }
    }

    /**
     * Returns a string representation of the queue.
     * Elements are listed from front to rear.
     *
     * @return string representation of the queue
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < size; i++) {
            sb.append(queue[(front + i) % queue.length]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }
}