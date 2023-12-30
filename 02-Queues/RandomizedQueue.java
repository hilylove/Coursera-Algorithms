/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.items = (Item[]) new Object[4];
        this.size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (items.length == size) {
            resize(size * 2);
        }
        items[size] = item;
        size++;
    }

    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = items[i];
        }
        items = newArray;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }
        int index = StdRandom.uniformInt(size);
        Item toRemove = items[index];
        items[index] = items[--size]; // check if the size is decremented by 1,
        items[size] = null;
        return toRemove;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniformInt(size);
        return items[index];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int totalSelected = 0;
        private int[] randomIndexes = new int[size];

        public RandomizedQueueIterator() {
            for (int i = 0; i < size; i++) {
                randomIndexes[i] = i;
            }
            StdRandom.shuffle(randomIndexes);
        }

        public boolean hasNext() {
            return totalSelected < size && size > 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int selectedIndex = randomIndexes[totalSelected];
            totalSelected++;
            return items[selectedIndex];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        System.out.println(queue.isEmpty());
        System.out.println(queue.isEmpty());
        queue.enqueue(76);
        queue.enqueue(14);
        System.out.println(queue.sample());
        System.out.println(queue.size());
        System.out.println(queue.dequeue());
        System.out.println(queue.isEmpty());
        System.out.println(queue.dequeue());
    }


}
