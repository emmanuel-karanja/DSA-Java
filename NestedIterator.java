
/**
 * PROBLEM STATEMENT:
 * ------------------
 * Implement a NestedIterator class to flatten a nested list of integers. 
 * Each element is either an integer or a list of NestedElement(s), nested arbitrarily.
 * 
 * The NestedIterator should support:
 *   - boolean hasNext(): returns true if there are more integers to iterate.
 *   - int next(): returns the next integer in depth-first order.
 *
 * REQUIREMENTS / DESIGN DECISIONS:
 * ---------------------------------
 * 1. Flatten lazily: Only compute the next integer when requested.
 * 2. Depth-first order: Traverse nested lists in order, respecting nesting.
 * 3. Thread-safe: Multiple threads may call hasNext() / next() concurrently.
 * 4. Efficient memory: Store only necessary state (stack of iterators).
 * 
 * INTERNAL STATE:
 * ----------------
 * - Stack<Iterator<NestedElement>>: Tracks iterators at each level of nesting.
 * - Integer nextEl: Caches the next integer for hasNext() / next().
 * 
 * THREAD-SAFETY:
 * ---------------
 * - Synchronize all public methods to protect internal state (stack & nextEl).
 * - Ensures consistent behavior when accessed concurrently.
 n
  NOTES:

   1. Stack stores the iterators of th nexted list. 
   2. We are dealing with a custom type.
 */

import java.util.*;
import java.util.concurrent.locks.*;

interface NestedElement {
    boolean isInteger();
    Integer getInteger();
    List<NestedElement> getList();
}

// Thread-safe NestedIterator
class NestedIterator implements Iterator<Integer> {
    private final Stack<Iterator<NestedElement>> stack = new Stack<>();
    private Integer nextEl = null;
    private final Lock lock = new ReentrantLock();

    public NestedIterator(List<NestedElement> nestedList) {
        if (nestedList != null) stack.push(nestedList.iterator());
        advance();
    }

    // Advance to the next integer
    private void advance() {
        nextEl = null;
        while (!stack.isEmpty()) {
            // Look at the top element . This is the key to this.
            Iterator<NestedElement> itr = stack.peek();

            if (!itr.hasNext()) {
                stack.pop();
                continue;
            }
            // Stores nested elements
            NestedElement el = itr.next();
            if (el.isInteger()) {
                nextEl = el.getInteger();
                return;
            } else {  // If it's a list, go down a level
                List<NestedElement> sublist = el.getList();
                if (sublist != null) {
                    stack.push(sublist.iterator());  // Add its iterator to the stack
                }
            }
        }
    }

    @Override
    public boolean hasNext() {
        lock.lock();
        try {
            return nextEl != null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Integer next() {
        lock.lock();
        try {
            if (nextEl == null) throw new NoSuchElementException();
            Integer res = nextEl;
            advance();  // point to the next
            return res;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        // Example nested structure: [1, [2, [3,4]], 5]
        NestedElement n1 = new SimpleNestedInt(1);
        NestedElement n2 = new SimpleNestedInt(2);
        NestedElement n3 = new SimpleNestedInt(3);
        NestedElement n4 = new SimpleNestedInt(4);
        NestedElement n5 = new SimpleNestedInt(5);

        NestedElement list3_4 = new SimpleNestedList(Arrays.asList(n3, n4));
        NestedElement list2_34 = new SimpleNestedList(Arrays.asList(n2, list3_4));
        List<NestedElement> topList = Arrays.asList(n1, list2_34, n5);

        NestedIterator it = new NestedIterator(topList);
        System.out.println("Flattened output:");
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        // Expected output: 1 2 3 4 5
    }
}

// Helper implementations
class SimpleNestedInt implements NestedElement {
    private final int value;
    SimpleNestedInt(int v) { this.value = v; }
    public boolean isInteger() { return true; }
    public Integer getInteger() { return value; }
    public List<NestedElement> getList() { return null; }
}

class SimpleNestedList implements NestedElement {
    private final List<NestedElement> list;
    SimpleNestedList(List<NestedElement> l) { this.list = l; }
    public boolean isInteger() { return false; }
    public Integer getInteger() { return null; }
    public List<NestedElement> getList() { return list; }
}