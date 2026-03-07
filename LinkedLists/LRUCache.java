package LinkedLists;

import java.util.HashMap;

/**
 * ============================================================
 * PROBLEM: LRU CACHE
 * ============================================================
 * 
 * Implement a Least Recently Used (LRU) Cache with the following operations:
 * 
 * 1. `get(key)`: Return the value of the key if it exists, otherwise return -1.
 * 2. `put(key, value)`: Insert or update the value of the key. If the cache exceeds
 *    its capacity, evict the least recently used item.
 *
 * REQUIREMENTS:
 * - Both operations must run in O(1) time complexity.
 *
 * ============================================================
 * REASONING:
 * ============================================================
 * To achieve O(1) access and update:
 * 1. Use a HashMap<Integer, Node> for O(1) lookup by key.
 * 2. Use a Doubly Linked List to maintain the usage order:
 *    - Head: Most recently used
 *    - Tail: Least recently used
 * 3. On `get(key)`:
 *    - If key exists, move the node to head (mark as most recently used) and return value.
 * 4. On `put(key, value)`:
 *    - If key exists, update value and move node to head.
 *    - If key doesn't exist:
 *        - If capacity is reached, remove tail node (least recently used) from both list and map.
 *        - Insert new node at head.
 *
 * The doubly linked list allows O(1) removal and insertion of nodes when we have direct reference.
 */

class LRUCache {
    private class Node {
        int key, value;
        Node prev, next;
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private HashMap<Integer, Node> map;
    private Node head, tail;   //Dummy head and tail the read head is head.next and the real tail is tail.prev
    // they are like bookends.

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(-1, -1); // dummy head
        tail = new Node(-1, -1); // dummy tail
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            moveToHead(node);
        } else {
            if (map.size() == capacity) {
                Node toRemove = tail.prev;
                // Remove from doubly linked list and hashmap
                removeNode(toRemove);
                map.remove(toRemove.key);
            }

            Node newNode = new Node(key, value);
            addNode(newNode);
            map.put(key, newNode);
        }
    }

    // Helper: remove node from list
    private void removeNode(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }

    // Helper: add node right after head (most recently used)
    private void addNode(Node node) {
        Node nextNode = head.next;
        head.next = node;
        node.prev = head;
        node.next = nextNode;
        nextNode.prev = node;
    }

    // Helper: move node to head (most recently used)
    private void moveToHead(Node node) {
        removeNode(node);
        addNode(node);
    }

    // Helper: print current cache state for debugging
    public void printCache() {
        Node curr = head.next;
        System.out.print("Cache State: ");
        while (curr != tail) {
            System.out.print("(" + curr.key + ":" + curr.value + ") ");
            curr = curr.next;
        }
        System.out.println();
    }

    // Driver main method
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(3);
        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(3, 300);
        cache.printCache(); // Should print 3->2->1 (most recent first)

        System.out.println("Get 2: " + cache.get(2)); // 200
        cache.printCache(); // Should print 2->3->1

        cache.put(4, 400); // Evict key 1
        cache.printCache(); // Should print 4->2->3

        System.out.println("Get 1: " + cache.get(1)); // -1 (evicted)
        System.out.println("Get 3: " + cache.get(3)); // 300
        cache.printCache(); // Should print 3->4->2
    }
}