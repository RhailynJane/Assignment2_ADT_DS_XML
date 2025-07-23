package implementations;

import utilities.Iterator;
import utilities.ListADT;
import java.util.NoSuchElementException;

/**
 *  @author Rhailyn Cona, Komalpreet Kaur, Anne Marie Ala, Abel Fekadu Samuel Braun
 *  @version 1, July 5, 2025.
 * MyArrayList: Array-backed implementation of ListADT interface.
 * @param <E> generic element type
 */
public class MyArrayList<E> implements ListADT<E>, Cloneable {

    private static final int DEFAULT_CAPACITY = 10;
    private E[] array;
    private int size;

    @SuppressWarnings("unchecked")
    public MyArrayList() {
        array = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element.");
        }
        ensureCapacity(size + 1);
        array[size++] = element;
        return true;
    }

    @Override
    public boolean add(int index, E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element.");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
        }
        ensureCapacity(size + 1);
        for (int i = size - 1; i >= index; i--) {
            array[i + 1] = array[i];
        }
        array[index] = element;
        size++;
        return true;
    }

    @Override
    public boolean addAll(ListADT<? extends E> otherList) {
        if (otherList == null) {
            throw new NullPointerException("Cannot addAll from null list.");
        }
        boolean changed = false;
        for (int i = 0; i < otherList.size(); i++) {
            add(otherList.get(i));
            changed = true;
        }
        return changed;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean contains(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot search for null element.");
        }
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return array[index];
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E removed = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
        size--;
        return removed;
    }

    @Override
    public E remove(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot remove null element.");
        }
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                return remove(i);
            }
        }
        return null;
    }

    @Override
    public E set(int index, E element) {
        if (element == null) {
            throw new NullPointerException("Cannot set null element.");
        }
        checkIndex(index);
        E oldValue = array[index];
        array[index] = element;
        return oldValue;
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
    public Iterator<E> iterator() {
        return new MyArrayListIterator();
    }

    private class MyArrayListIterator implements Iterator<E> {
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements.");
            }
            return array[cursor++];
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public E[] toArray(E[] inputArray) {
        if (inputArray == null) {
            throw new NullPointerException("Input array cannot be null.");
        }
        if (inputArray.length < size) {
            return (E[]) java.util.Arrays.copyOf(array, size, inputArray.getClass());
        }
        for (int i = 0; i < size; i++) {
            inputArray[i] = array[i];
        }
        if (inputArray.length > size) {
            inputArray[size] = null;
        }
        return inputArray;
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size];
        System.arraycopy(array, 0, newArray, 0, size);
        return newArray;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > array.length) {
            int newCapacity = array.length * 2;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            E[] newArray = (E[]) new Object[newCapacity];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
        }
    }

    @Override
    public MyArrayList<E> clone() {
        try {
            MyArrayList<E> copy = (MyArrayList<E>) super.clone();
            copy.array = java.util.Arrays.copyOf(array, size);
            // size copied automatically
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }
}
