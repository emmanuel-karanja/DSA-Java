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

    public static boolean isPalindrome(ListNode head){
         if (head == null || head.next == null) { // empty or single node is palindrome
            return true;
        }

        ListNode leftHalf=head;

        //find middle
        ListNode rightHalf=reverseLinkedList(getMiddle(head));

        while(rightHalf !=null && leftHalf!=null){
            if(rightHalf.value!=leftHalf.value){
                return false;
            }

            leftHalf=leftHalf.next;
            rightHalf=rightHalf.next;
        }

        return true;
    }

    private static ListNode getMiddle(ListNode head){
        ListNode slow=head;
        ListNode fast=head;

        while(fast!=null && fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        }

        return slow;
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
