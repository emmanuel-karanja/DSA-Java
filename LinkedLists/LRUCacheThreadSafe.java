package LinkedLists;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ============================================================
 * THREAD-SAFE LRU CACHE
 * ============================================================
 * 
 * All operations (`get` and `put`) are thread-safe using ReentrantLock.
 * Multiple threads can safely access the cache concurrently.
 *
 * O(1) operations are preserved with a HashMap + Doubly Linked List.
 */

public class LRUCacheThreadSafe {
    private class Node {
        int key, value;
        Node prev, next;
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final HashMap<Integer, Node> map;
    private final Node head, tail;
    private final ReentrantLock lock;

    public LRUCacheThreadSafe(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
        lock = new ReentrantLock();
    }

    public int get(int key) {
        lock.lock();
        try {
            if (!map.containsKey(key)) return -1;
            Node node = map.get(key);
            moveToHead(node);
            return node.value;
        } finally {
            lock.unlock();
        }
    }

    public void put(int key, int value) {
        lock.lock();
        try {
            if (map.containsKey(key)) {
                Node node = map.get(key);
                node.value = value;
                moveToHead(node);
            } else {
                if (map.size() == capacity) {
                    Node toRemove = tail.prev;
                    removeNode(toRemove);
                    map.remove(toRemove.key);
                }
                Node newNode = new Node(key, value);
                addNode(newNode);
                map.put(key, newNode);
            }
        } finally {
            lock.unlock();
        }
    }

    private void removeNode(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }

    private void addNode(Node node) {
        Node nextNode = head.next;
        head.next = node;
        node.prev = head;
        node.next = nextNode;
        nextNode.prev = node;
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addNode(node);
    }

    public void printCache() {
        lock.lock();
        try {
            Node curr = head.next;
            System.out.print("Cache State: ");
            while (curr != tail) {
                System.out.print("(" + curr.key + ":" + curr.value + ") ");
                curr = curr.next;
            }
            System.out.println();
        } finally {
            lock.unlock();
        }
    }

    // Driver main method with multi-thread demo
    public static void main(String[] args) throws InterruptedException {
        LRUCacheThreadSafe cache = new LRUCacheThreadSafe(3);

        Runnable writer = () -> {
            for (int i = 1; i <= 5; i++) {
                cache.put(i, i * 100);
                cache.printCache();
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            }
        };

        Runnable reader = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Get " + i + ": " + cache.get(i));
                try { Thread.sleep(30); } catch (InterruptedException ignored) {}
            }
        };

        Thread t1 = new Thread(writer);
        Thread t2 = new Thread(reader);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        cache.printCache();
    }
}