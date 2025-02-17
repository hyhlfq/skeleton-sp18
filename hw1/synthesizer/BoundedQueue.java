package synthesizer;

public interface BoundedQueue<T> extends Iterable<T> {

    /**
     * @return size of the buffer
     */
    int capacity();

    /**
     * @return number of items currently in the buffer
     */
    int fillCount();

    /**
     * add item x to the end
     * @param x item to be added
     */
    void enqueue(T x);

    /**
     * delete and return item from the front
     * @return item from the front
     */
    T dequeue();

    /**
     * @return (but do not delete) item from the front
     */
    T peek();

    /**
     * @return true if the buffer is empty
     */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /**
     * @return true if the buffer is full
     */
    default boolean isFull()  {
        return fillCount() == capacity();
    }
}
