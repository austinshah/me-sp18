package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    private int first;
    private int last;
    private T[] rb;

    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
    }


    public void enqueue(T x) {
        if (this.isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        } else {
            rb[last] = x;
            last += 1;
            fillCount += 1;
            if (last == capacity) {
                last = 0;
            }
        }
    }

    public T dequeue() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            T returnItem = rb[first];
            first += 1;
            fillCount -= 1;
            if (first == capacity) {
                first = 0;
            }
            return returnItem;
        }
    }

    public T peek() {
        if (this.isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            return rb[first];
        }
    }

    private class ArrayIterator implements Iterator<T> {
        private int index;

        private ArrayIterator() {
            index = first;
        }

        public boolean hasNext() {
            return index < capacity;
        }

        public T next() {
            T current = rb[index];
            index += 1;
            return current;
        }
    }

    public Iterator<T> iterator() {
        return new ArrayIterator();
    }
}
