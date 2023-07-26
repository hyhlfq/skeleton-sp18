public class ArrayDeque<T> {

    private final int INITIAL_LENGTH = 8;
    private final int RESIZE_FACTOR = 2;
    private final int SHRINK_FACTOR = 4;
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    @SuppressWarnings("unchecked")
    public ArrayDeque() {
        size = 0;
        nextFirst = 0;
        nextLast = 1;
        items = (T[]) new Object[INITIAL_LENGTH];
    }

    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * RESIZE_FACTOR);
        }
        items[nextFirst] = x;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size++;
    }

    public void addLast(T x) {
        if (size == items.length) {
            resize(size * RESIZE_FACTOR);
        }
        items[nextLast] = x;
        nextLast = (nextLast + 1) % items.length;
        size++;
    }

    public void printDeque() {
        String s = "";
        for (int i = 0; i < size; i++) {
            s += get(i) + " ";
        }
        System.out.println(s.trim());
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        nextFirst = (nextFirst + 1) % items.length;
        T x = items[nextFirst];
        items[nextFirst] = null;
        size--;
        if (items.length >= INITIAL_LENGTH && items.length / SHRINK_FACTOR >= size) {
            resize(items.length / RESIZE_FACTOR);
        }
        return x;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        nextLast = (nextLast - 1 + items.length) % items.length;
        T x = items[nextLast];
        items[nextLast] = null;
        size--;
        if (items.length >= INITIAL_LENGTH && items.length / SHRINK_FACTOR >= size) {
            resize(items.length / RESIZE_FACTOR);
        }
        return x;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(nextFirst + 1 + index) % items.length];
    }

    @SuppressWarnings("unchecked")
    private void resize(int length) {
        T[] newItems = (T[]) new Object[length];
        for (int i = 0; i < size; i++) {
            newItems[i] = get(i);
        }
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }
}
