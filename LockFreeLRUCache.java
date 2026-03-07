import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class LockFreeLRUCache<K,V>{

    private final int capacity;

    private final ConcurrentHashMap<K, Node<K,V>> map;
    private final ConcurrentLinkedDeque<Node<K,V>> accessQueue;
    private final AtomicInteger size;

    static class Node<K,V>{
        final K key;
        volatile V value;
        volatile boolean alive = true;

        Node(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

    public LockFreeLRUCache(int capacity){
        this.capacity = capacity;
        this.map = new ConcurrentHashMap<>();
        this.accessQueue = new ConcurrentLinkedDeque<>();
        this.size = new AtomicInteger();
    }

    public V get(K key){

        Node<K,V> node = map.get(key);

        if(node == null)
            return null;

        // record access
        accessQueue.offerLast(node);

        return node.value;
    }

    public void put(K key, V value){

        Node<K,V> newNode = new Node<>(key,value);

        Node<K,V> old = map.put(key,newNode);

        if(old == null){
            size.incrementAndGet();
        }else{
            old.alive = false;
        }

        accessQueue.offerLast(newNode);

        evictIfNeeded();
    }

    private void evictIfNeeded(){

        while(size.get() > capacity){

            Node<K,V> node = accessQueue.pollFirst();

            if(node == null)
                return;

            if(!node.alive)
                continue;

            if(map.remove(node.key,node)){
                node.alive = false;
                size.decrementAndGet();
                return;
            }
        }
    }
}