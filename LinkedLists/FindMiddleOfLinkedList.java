package LinkedLists;
/**Given a linked list starting at head, find the middle of the linked list.
 * 
 * INTUITION
 * 
 * Method 1: Do a single passto find te length of the listi.e starting at head keep a counter count and increment
 *           until the pointer points to null.*/
public class FindMiddleOfLinkedList {

    public static ListNode getMiddle(ListNode head){
        //find the length
        int n = 0;
        ListNode curr = head;
        while (curr != null) {
            n++;
            curr = curr.next;
        }

        curr = head;
        for (int i = 0; i < n / 2; i++) {
            curr = curr.next;
        }
        return curr;
    }
    
}
