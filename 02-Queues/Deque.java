/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    // construct an empty deque
    public Deque() {
        this.first = null;
        this.last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node toAdd = new Node();
        toAdd.item = item;
        if (isEmpty()) {
            first = toAdd;
            last = first;
        }
        else {
            Node oldFirst = first;
            first = toAdd;
            toAdd.next = oldFirst;
            oldFirst.prev = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node toAdd = new Node();
        toAdd.item = item;
        if (isEmpty()) {
            last = toAdd;
            first = last;
        }
        else {
            Node oldLast = last;
            last = toAdd;
            oldLast.next = last;
            last.prev = oldLast;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Node toRemove = first;
        first = toRemove.next;
        size--;
        if (isEmpty()) {
            last = null;
        }
        else {
            first.prev = null;
        }
        return toRemove.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Node toRemove = last;
        last = toRemove.prev;
        size--;
        if (isEmpty()) {
            first = null;
        }
        else {
            last.next = null;
        }
        return toRemove.item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        public DequeIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        StdOut.print(deque.isEmpty());
        StdOut.print("\n");
        deque.addFirst(1);
        for (int i : deque) {
            StdOut.println(i + " ");
        }
        deque.removeLast();
        for (int i : deque) {
            StdOut.println(i + " ");
        }
        // StdOut.print("\n");
        // StdOut.println(deque.removeFirst() + " ");
        // StdOut.println(deque.removeFirst() + " ");
        // StdOut.println(deque.removeLast() + " ");
        // StdOut.println(deque.removeFirst() + " ");
        // StdOut.println(deque.isEmpty());
    }
}
