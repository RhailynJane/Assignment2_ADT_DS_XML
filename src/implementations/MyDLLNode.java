package implementations;

/**
 * MyDLLNode - A node class for use in a doubly linked list.
 *
 * Each node contains:
 * - Data: the element stored in this node
 * - Next: reference to the next node in the list
 * - Previous: reference to the previous node in the list
 *
 * This allows for efficient insertion and deletion at any position
 * in the linked list, as well as bidirectional traversal.
 *
 * @param <E> the type of element stored in this node
 *  @author Rhailyn Cona, Komalpreet Kaur, Anne Marie Ala, Abel Fekadu Samuel Braun
 *  @version 1, July 5, 2025.
 */
public class MyDLLNode<E> {

    /**
     * The data/element stored in this node
     */
    private E data;

    /**
     * Reference to the next node in the list
     * null if this is the last node
     */
    private MyDLLNode<E> next;

    /**
     * Reference to the previous node in the list
     * null if this is the first node
     */
    private MyDLLNode<E> previous;

    /**
     * Constructs a new node with the specified data.
     * Next and previous references are initialized to null.
     *
     * @param data the element to store in this node
     */
    public MyDLLNode(E data) {
        this.data = data;
        this.next = null;
        this.previous = null;
    }

    /**
     * Constructs a new node with specified data and references.
     *
     * @param data the element to store in this node
     * @param previous reference to the previous node
     * @param next reference to the next node
     */
    public MyDLLNode(E data, MyDLLNode<E> previous, MyDLLNode<E> next) {
        this.data = data;
        this.previous = previous;
        this.next = next;
    }

    /**
     * Gets the data stored in this node.
     *
     * @return the data stored in this node
     */
    public E getData() {
        return data;
    }

    /**
     * Sets the data stored in this node.
     *
     * @param data the new data to store in this node
     */
    public void setData(E data) {
        this.data = data;
    }

    /**
     * Gets the reference to the next node.
     *
     * @return the next node, or null if this is the last node
     */
    public MyDLLNode<E> getNext() {
        return next;
    }

    /**
     * Sets the reference to the next node.
     *
     * @param next the node that should follow this node
     */
    public void setNext(MyDLLNode<E> next) {
        this.next = next;
    }

    /**
     * Gets the reference to the previous node.
     *
     * @return the previous node, or null if this is the first node
     */
    public MyDLLNode<E> getPrevious() {
        return previous;
    }

    /**
     * Sets the reference to the previous node.
     *
     * @param previous the node that should precede this node
     */
    public void setPrevious(MyDLLNode<E> previous) {
        this.previous = previous;
    }

    /**
     * Checks if this node has a next node.
     *
     * @return true if there is a next node, false otherwise
     */
    public boolean hasNext() {
        return next != null;
    }

    /**
     * Checks if this node has a previous node.
     *
     * @return true if there is a previous node, false otherwise
     */
    public boolean hasPrevious() {
        return previous != null;
    }

    /**
     * Returns a string representation of this node.
     * Shows the data stored in the node.
     *
     * @return string representation of this node
     */
    @Override
    public String toString() {
        return "Node[data=" + data + "]";
    }

    /**
     * Compares this node with another object for equality.
     * Two nodes are equal if they contain the same data.
     *
     * @param obj the object to compare with
     * @return true if the nodes are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof MyDLLNode)) return false;

        @SuppressWarnings("unchecked")
        MyDLLNode<E> other = (MyDLLNode<E>) obj;

        if (data == null) {
            return other.data == null;
        }

        return data.equals(other.data);
    }

    /**
     * Returns a hash code for this node.
     * Based on the hash code of the data stored in the node.
     *
     * @return hash code for this node
     */
    @Override
    public int hashCode() {
        return data == null ? 0 : data.hashCode();
    }
}