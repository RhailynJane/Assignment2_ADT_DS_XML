package implementations;

import utilities.StackADT;
import implementations.MyArrayList;
import java.util.EmptyStackException;

/**
 * MyStack - Stack Implementation using MyArrayList
 *
 * A Last-In-First-Out (LIFO) data structure where elements are added
 * and removed from the same end (the "top" of the stack).
 *
 * Uses MyArrayList internally because:
 * - Stacks only add/remove from one end
 * - Array-based storage provides O(1) push/pop operations
 * - Good cache locality for sequential access
 *
 * Stack Operations:
 * - push(): Add element to top
 * - pop(): Remove and return top element
 * - peek(): View top element without removing
 * - isEmpty(): Check if stack is empty
 * - size(): Get number of elements
 *
 * Visual: [bottom] → [element1] → [element2] → [TOP]
 *         Index 0      Index 1      Index 2    ← push/pop here
 *
 * @param <E> the type of elements stored in this stack
 */
public class MyStack<E> implements StackADT<E> {

    // Internal storage using MyArrayList
    private MyArrayList<E> list;

    /**
     * Default constructor - creates empty stack with default capacity
     */
    public MyStack() {
        list = new MyArrayList<>();
    }

    /**
     * Constructor with initial capacity
     * @param initialCapacity the initial capacity of the internal array
     * @throws IllegalArgumentException if initialCapacity is negative
     */
    public MyStack(int initialCapacity) throws IllegalArgumentException {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity cannot be negative: " + initialCapacity);
        }
        list = new MyArrayList<>(initialCapacity);
    }

    /**
     * Pushes an element onto the top of the stack
     * Time Complexity: O(1) amortized
     *
     * @param element the element to push
     * @throws NullPointerException if element is null
     */
    @Override
    public void push(E element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException("Cannot push null element onto stack");
        }
        list.add(element); // Add to end of list (top of stack)
    }

    /**
     * Removes and returns the element at the top of the stack
     * Time Complexity: O(1)
     *
     * @return the element at the top of the stack
     * @throws EmptyStackException if stack is empty
     */
    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return list.remove(list.size() - 1); // Remove from end (top of stack)
    }

    /**
     * Returns the element at the top of the stack without removing it
     * Time Complexity: O(1)
     *
     * @return the element at the top of the stack
     * @throws EmptyStackException if stack is empty
     */
    @Override
    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return list.get(list.size() - 1); // Get from end (top of stack)
    }

    /**
     * Removes all elements from the stack
     * Time Complexity: O(1)
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * Checks if the stack is empty
     * Time Complexity: O(1)
     *
     * @return true if stack contains no elements
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns the number of elements in the stack
     * Time Complexity: O(1)
     *
     * @return the number of elements
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Returns an array containing all elements in the stack.
     * The top element of the stack is at index 0 of the returned array.
     * Note: This differs from the internal storage order for interface compliance.
     *
     * @return array with top element at index 0
     */
    @Override
    public Object[] toArray() {
        Object[] result = new Object[list.size()];
        // Reverse order: top element goes to index 0
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(list.size() - 1 - i);
        }
        return result;
    }

    /**
     * Converts stack to typed array with top element at index 0
     *
     * @param holder array to fill (must be correct size)
     * @return filled array
     * @throws NullPointerException if holder is null
     */
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null) {
            throw new NullPointerException("Holder array cannot be null");
        }

        // Reverse order: top element goes to index 0
        for (int i = 0; i < Math.min(holder.length, list.size()); i++) {
            holder[i] = list.get(list.size() - 1 - i);
        }
        return holder;
    }

    /**
     * Searches for an element in the stack
     * Returns the 1-based position from the top of the stack
     *
     * @param target element to search for
     * @return 1-based position from top, or -1 if not found
     * @throws NullPointerException if target is null
     */
    public int search(E target) throws NullPointerException {
        if (target == null) {
            throw new NullPointerException("Cannot search for null element");
        }

        // Search from top to bottom (end to beginning of list)
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).equals(target)) {
                // Return 1-based position from top
                return list.size() - i;
            }
        }
        return -1; // Not found
    }

    /**
     * Checks if stack contains the specified element
     *
     * @param target element to search for
     * @return true if element is found
     * @throws NullPointerException if target is null
     */
    public boolean contains(E target) throws NullPointerException {
        if (target == null) {
            throw new NullPointerException("Cannot search for null element");
        }
        return list.contains(target);
    }

    /**
     * Creates a copy of this stack
     * The copy is independent of the original
     *
     * @return a new stack with the same elements
     */
    public MyStack<E> clone() {
        MyStack<E> copy = new MyStack<>();
        // Add elements in same order (bottom to top)
        for (int i = 0; i < list.size(); i++) {
            copy.push(list.get(i));
        }
        return copy;
    }

    /**
     * String representation showing stack from bottom to top
     * Format: [bottom, ..., top] with top indicator
     *
     * @return string representation
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "Stack: [] ← top";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Stack: [");

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("] ← top");
        return sb.toString();
    }

    /**
     * Detailed string showing stack structure vertically
     * Useful for visualizing stack operations
     *
     * @return vertical representation
     */
    public String toVerticalString() {
        if (isEmpty()) {
            return "Stack: (empty)\n  ← top";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Stack:\n");

        // Show from top to bottom
        for (int i = list.size() - 1; i >= 0; i--) {
            if (i == list.size() - 1) {
                sb.append("  ").append(list.get(i)).append(" ← top\n");
            } else {
                sb.append("  ").append(list.get(i)).append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Equals method - compares stack contents
     * Two stacks are equal if they have the same elements in the same order
     *
     * @param obj object to compare with
     * @return true if stacks are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        @SuppressWarnings("unchecked")
        MyStack<E> other = (MyStack<E>) obj;

        return list.equals(other.list);
    }

    /**
     * Hash code based on stack contents
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return list.hashCode();
    }
}