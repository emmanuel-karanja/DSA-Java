package LinkedLists;
/**Detect cycle in a linked list:
 * 
 * INTUTION:
 * Use Floyd's cycle algorithm i.. a slow and faster pointer, fast pointer moves two steps and the slow one step at a time.
 * When slow==fast, we know we've found a cycle, since the fastone has 'circled back'
 */
public class DetectCycle {

    public static boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                return true; // cycle detected
            }
        }
        
        return false; // no cycle
    }
}
