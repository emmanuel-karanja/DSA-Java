package LinkedLists;


class ListNode{
    public ListNode next;
    public int value;
    public ListNode(ListNode next, int value){
        this.next=next;
        this.value=value;
    }
}
public class ReverseLinkedList {

    public static ListNode reverse(ListNode head){
        //set next, prev and current
        ListNode current=head;
        ListNode prev=null;
        ListNode next=null;

        while(current!=null){
            next=current.next;
            current.next=prev;

            prev=current;
            current=next;
        }

        return prev; //remember this
    }

    public static void main(String[] args){
        ListNode head = new ListNode(null, 1);
        head.next = new ListNode(null, 2);
        head.next.next = new ListNode(null, 3);

        ListNode reversed = reverse(head);

        while(reversed != null){
            System.out.print(reversed.value + " ");
            reversed = reversed.next;
        }
        // Output: 3 2 1
    }
}
