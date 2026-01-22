package LinkedLists;
/**Find the middle of the linkedList, and then reverse the right half and compare it with the left half */

class ListNode{
    public ListNode next;
    public int value;

    public ListNode(ListNode next, int value){
        this.next=next;
        this.value=value;
    }
}
public class PalindromeLinkedList {

    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode slow = head;
        ListNode fast = head;

        // find middle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // skip middle node for odd-length lists , edge case
        if (fast != null) {
            slow = slow.next;
        }

        ListNode rightHalf = reverseLinkedList(slow);
        ListNode leftHalf = head;

        while (rightHalf != null) {
            if (leftHalf.value != rightHalf.value) {
                return false;
            }
            leftHalf = leftHalf.next;  //advance list 1
            rightHalf = rightHalf.next;  // advance list 2
        }

        return true;
    }

    private static ListNode reverseLinkedList(ListNode head){
        ListNode next=null;
        ListNode prev=null;
        ListNode current=head;

        while(current!=null){
            next=current.next;
            current.next=prev;

            prev=current;
            current=next;
        }

        return prev;
    }
    
}
