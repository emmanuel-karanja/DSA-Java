package LinkedLists;
/**INTUTION
 * 1. Split it in left and right halves 
 * 2. Merge sort each
 * 3. mergeth two
 */
class ListNode{
    public ListNode next;
    public int value;
    public ListNode(ListNode next,int value){
        this.next=next;
        this.value=value;
    }

}
public class MergeSotLinkedList {


    public static ListNode mergeSort(ListNode head){
        if(head==null || head.next==null){
            return head;
        }

        ListNode middle=getMiddle(head);
        ListNode right=middle.next;
        middle.next=null;// to avoid infinite looping

        ListNode leftSorted=mergeSort(head);
        ListNode rightSorted=mergeSort(right);

        return mergeTwo(leftSorted,rightSorted);
    }

    private static ListNode getMiddle(ListNode head){

        ListNode fast=head.next; // ensures slow stops at node before right half
        ListNode slow=head;

        while(fast!=null && fast.next!=null){
            slow=slow.next;
            fast=fast.next.next;
        }

        return slow;
    }
    
    private static ListNode mergeTwo(ListNode l1, ListNode l2){
        if(l1==null) return l2;
        if(l2==null) return l1;

        ListNode dummy=new ListNode(null, 0);
        ListNode current=dummy;

        while(l1!=null && l2!=null){
            if(l1.value < l2.value){
                current.next=l1;
                l1=l1.next;
            }else{
                current.next=l2;
                l2=l2.next;
            }
            current=current.next;
        }

        while(l1!=null){
            current.next=l1;
            l1=l1.next;
            current=current.next;
        }

        while(l2!=null){
            current.next=l2;
            l2=l2.next;
            current=current.next;
        }

        return dummy.next;
    }
}
