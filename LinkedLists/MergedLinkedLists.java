package LinkedLists;

class ListNode{
    public ListNode next;
    public int value;

    public ListNode(ListNode next, int value){
       this.next=next;
       this.value=value;
    }
}

public class MergedLinkedLists {

    public static ListNode mergeTwo(ListNode l1, ListNode l2){
        //check if any of them are null
        if(l1==null && l2==null){
            throw new IllegalArgumentException("Lists are null");
        }

        if(l1==null) return l2;
        if(l2==null) return l1;

        //iterate
        ListNode dummy=new ListNode(null,0);
        ListNode current=dummy;

        while(l1 !=null && l2!=null){
            if(l1.value < l2.value){
                current.next=l1;
                l1=l1.next;
            }else{
                current.next=l2;
                l2=l2.next;
            }
            current=current.next;
        }

        if(l1 != null) current.next=l1;
        if(l2!= null) current.next=l2;

        return dummy.next; //still points to the head since current will point to end of merged list

    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(null, 1);
        l1.next = new ListNode(null, 3);
        l1.next.next = new ListNode(null, 5);

        ListNode l2 = new ListNode(null, 2);
        l2.next = new ListNode(null, 4);
        l2.next.next = new ListNode(null, 6);

        ListNode merged = mergeTwo(l1, l2);

        while (merged != null) {
            System.out.print(merged.value + " ");
            merged = merged.next;
        }
        // Output: 1 2 3 4 5 6
    }
}
