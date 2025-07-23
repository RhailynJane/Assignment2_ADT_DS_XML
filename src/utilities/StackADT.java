package utilities;

import exceptions.EmptyStackException;

/**
 * Stack Abstract Data Type (ADT) Interface
 *
 * A stack is a Last-In-First-Out (LIFO) data structure where elements are added
 * and removed from the same end, called the "top" of the stack.
 *
 * Think of it like a stack of plates - you can only add a plate to the top,
 * and you can only remove the plate from the top.
 *
 * @param <E> the type of elements stored in this stack
 * @author Rhailyn Cona, Komalpreet Kaur, Anne Marie Ala, Abel Fekadu Samuel Braun
 * @version 1.0
 */
public interface StackADT<E> {

    /**
     * Adds an element to the top of the stack.
     * This operation is also called "push".
     *
     * @param element the element to be added to the top of the stack
     * @throws NullPointerException if the specified element is null
     * @post the element is added to the top of the stack
     * @post the size of the stack increases by 1
     */
    void push(E element) throws NullPointerException;

    /**
     * Removes and returns the element at the top of the stack.
     * This operation is also called "pop".
     *
     * @return the element that was at the top of the stack
     * @throws EmptyStackException if the stack is empty
     * @post the top element is removed from the stack
     * @post the size of the stack decreases by 1
     */
    E pop() throws EmptyStackException;

    /**
     * Returns the element at the top of the stack without removing it.
     * This operation is also called "peek" or "top".
     *
     * @return the element at the top of the stack
     * @throws EmptyStackException if the stack is empty
     * @post the stack remains unchanged
     */
    E peek() throws EmptyStackException;

    /**
     * Tests if the stack is empty.
     *
     * @return true if the stack contains no elements, false otherwise
     * @post the stack remains unchanged
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the stack.
     *
     * @return the number of elements currently in the stack
     * @post the stack remains unchanged
     */
    int size();

    /**
     * Removes all elements from the stack.
     * After this operation, the stack will be empty.
     *
     * @post the stack is empty
     * @post size() returns 0
     * @post isEmpty() returns true
     */
    void clear();

    /**
     * Returns an array containing all elements in the stack.
     * The top element of the stack is at index 0 of the returned array.
     *
     * @return an array containing all elements in the stack, with the top element first
     * @post the stack remains unchanged
     */
    Object[] toArray();

    /**
     * Compares the specified object with this stack for equality.
     * Two stacks are equal if they contain the same elements in the same order.
     *
     * @param obj the object to be compared for equality with this stack
     * @return true if the specified object is equal to this stack, false otherwise
     * @post the stack remains unchanged
     */
    boolean equals(Object obj);
}