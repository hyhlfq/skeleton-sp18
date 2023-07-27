public class LinkedListDeque<T> implements Deque<T>{

    private static class ListNode<T> {

        private T item;
        private ListNode<T> prev;
        private ListNode<T> next;

        private ListNode() {
            this(null, null, null);
        }

        private ListNode(T item, ListNode<T> prev, ListNode<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private ListNode<T> sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new ListNode<>();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        ListNode<T> newNode = new ListNode<>(item, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        ListNode<T> newNode = new ListNode<>(item, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size += 1;
    }

    @Override
    public void printDeque() {
        ListNode<T> p = sentinel.next;
        String s = "";
        while (p != sentinel) {
            s += p.item + " ";
            p = p.next;
        }
        System.out.println(s.trim());
    }

    @Override
    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T item = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return item;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        ListNode<T> p = sentinel.next;
        while (index > 0) {
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, ListNode<T> p) {
        if (index == 0) {
            return p.item;
        }
        return getRecursiveHelper(index - 1, p.next);
    }
}
