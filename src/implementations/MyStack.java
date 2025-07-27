package implementations;

import exceptions.EmptyStackException;
import utilities.Iterator;
import utilities.StackADT;

import java.io.Serializable;

/**
 * ArrayList-based implementation of the Stack Abstract Data Type (ADT).
 * Uses a dynamic array (MyArrayList) to store elements.
 *  @author Rhailyn Cona, Komalpreet Kaur, Anne Marie Ala, Abel Fekadu Samuel Braun
 *  @version 1, July 5, 2025.
 * @param <E> the type of elements held in this stack
 */
public class MyStack<E> implements StackADT<E>, Cloneable, Serializable {

    // Internal storage for stack elements
    private MyArrayList<E> list;

    /**
     * Creates an empty stack using a dynamic array.
     */
    public MyStack() {

        list = new MyArrayList<>();
    }

    /**
     * Adds an element to the top of the stack.
     *
     * @param element element to add
     * @throws NullPointerException if element is null
     */
    @Override
    public void push(E element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException("Cannot push null element");
        }
        list.add(element);
    }


    /**
     * Removes and returns the top element of the stack.
     *
     * @return top element
     * @throws EmptyStackException if stack is empty
     */
    @Override
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return list.remove(list.size() - 1);
    }

    /**
     * Returns the top element without removing it.
     *
     * @return top element
     * @throws EmptyStackException if stack is empty
     */
    @Override
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return list.get(list.size() - 1);
    }

    /**
     * Returns true if the stack contains no elements.
     *
     * @return true if empty
     */
    @Override
    public boolean isEmpty() {
        return list.size() == 0;
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @return stack size
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Removes all elements from the stack.
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * Returns true if the stack contains the specified element.
     *
     * @param element element to check
     * @return true if stack contains element
     * @throws NullPointerException if element is null
     */
    @Override
    public boolean contains(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot check contains for null");
        }
        return list.contains(element);
    }

    /**
     * Returns an iterator over the elements from top to bottom.
     *
     * @return iterator
     */
    public Iterator<E> iterator() {
        // Return a custom iterator that iterates backwards over list (top first)
        return new Iterator<E>() {
            private int current = list.size() - 1;

            @Override
            public boolean hasNext() {
                return current >= 0;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                return list.get(current--);
            }
        };
    }

    /**
     * Returns an Object array containing elements from top to bottom.
     *
     * @return array of elements
     */
    public Object[] toArray() {
        Object[] arr = new Object[size()];
        for (int i = 0; i < size(); i++) {
            arr[i] = list.get(size() - 1 - i);
        }
        return arr;
    }

    /**
     * Returns an array containing all elements from top to bottom.
     * If passed array is large enough, fills it and returns it.
     * Else creates new array.
     *
     * @param a array to store elements
     * @return array containing all elements
     * @throws NullPointerException if array is null
     */
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] a) {
        if (a == null) {
            throw new NullPointerException("Array is null");
        }

        int size = size();
        if (a.length < size) {
            a = (E[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
        }
        for (int i = 0; i < size; i++) {
            a[i] = list.get(size - 1 - i);
        }
        if (a.length > size) {
            a[size] = null; // null-terminate if array larger than needed
        }
        return a;
    }

    /**
     * Returns the 1-based position from the top of the stack where the element
     * is located; returns -1 if not found.
     *
     * @param element element to search
     * @return position from top (1-based), or -1 if not found
     */
    public int search(E element) {
        if (element == null) {
            return -1;
        }
        // Search from top (end of list) to bottom (start)
        for (int i = list.size() - 1, pos = 1; i >= 0; i--, pos++) {
            if (element.equals(list.get(i))) {
                return pos;
            }
        }
        return -1;
    }

    /**
     * Returns true if this stack equals another stack (same elements in order).
     *
     * @param that the other StackADT to compare with
     * @return true if both stacks are equal in size and contents; false if not equal
     */
    @Override
    public boolean equals(StackADT<E> that) {
        if (this == that) return true;
        if (that == null) return false;
        if (this.size() != that.size()) return false;

        Iterator<E> thisIt = this.iterator();
        Iterator<E> thatIt = that.iterator();

        while (thisIt.hasNext() && thatIt.hasNext()) {
            E e1 = thisIt.next();
            E e2 = thatIt.next();
            if (e1 == null ? e2 != null : !e1.equals(e2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a string representation of the stack from bottom to top.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        if (isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size(); i++) {
            sb.append(list.get(i));
            if (i < size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Returns a deep copy of this stack.
     *
     * @return cloned stack
     */
    @Override
    public MyStack<E> clone() {
        try {
            // shallow copy of MyStack
            MyStack<E> copy = (MyStack<E>) super.clone();
            // clone the internal list as well for deep copy
            copy.list = this.list.clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            // Should never happen because we implement Cloneable
            throw new RuntimeException("Clone not supported", e);
        }
    }

    /**
     * Returns false because this stack has no fixed size limit and cannot overflow.
     *
     * @return false always
     */
    public boolean stackOverflow() {
        return false;
    }

}
