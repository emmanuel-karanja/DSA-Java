package LinkedLists;
/**Given a linked list starting at head, find the middle of the linked list.
 * 
 * INTUITION
 * 
 * Method 1: Do a single passto find te length of the listi.e starting at head keep a counter count and increment
 *           until the pointer points to null.
 * Method 2: use a fast and slow pointer. Slow moves 1step, slow moves two step,when fast ==null, slow will point to the middle
 * 
 */
public class FindLinkedListMiddleFastAndSlow {

    public static ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;       // 1 step
            fast = fast.next.next;  // 2 steps
        }
        
        return slow; // slow is at the middle
    }   
  
}
