package LinkedLists;

//**We can get n(log k) when we divide and conquer. i.e. split the list into two and recursively merge
// the left and right sides and then finally merrge the two. */
class ListNode{
    public ListNode next;
    public int value;

    public ListNode(ListNode next, int value){
       this.next=next;
       this.value=value;
    }
}
public class MergeKSortedLists {
     private static ListNode mergeTwo(ListNode l1, ListNode l2){
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

    private static ListNode mergeRange(ListNode[] lists,int left,int right){
        //basically we merge from range left ro right
        if(left==right) return lists[left];
        //divide and conquor  i.e. we split it into two sublists along the middle
         
        int mid=left + (right-left)/2; //this is preferred to int mid=(left+right)/2 (Java automatically floors integers, so
        //need for Math.floor()), this is prefered due to integer overflow problems e.g. if left and right are pretty high and almost
        //at integer limit, doing left+right could surpass the allowed integer size. Overflow leads to wrapping around i.e.
        //around to the negative integer.  i.e. max value for integers is 

        //So this is preferred way for doing the mid point for binary search related problems

        ListNode leftMerged=mergeRange(lists,left,mid);
        ListNode rightMerged=mergeRange(lists,mid+1,right);

        return mergeTwo(leftMerged,rightMerged);
    }

    public static ListNode mergeKLists(ListNode[] lists){
        if (lists == null || lists.length == 0) return null;
         return mergeRange(lists, 0, lists.length - 1);
    }

    public static void main(String[] args) {
        ListNode a = new ListNode(null, 1);
        a.next = new ListNode(null, 4);
        a.next.next = new ListNode(null, 7);

        ListNode b = new ListNode(null, 2);
        b.next = new ListNode(null, 5);

        ListNode c = new ListNode(null, 3);
        c.next = new ListNode(null, 6);
        c.next.next = new ListNode(null, 8);

        ListNode[] lists = {a, b, c};

        ListNode merged = mergeKLists(lists);

        while (merged != null) {
            System.out.print(merged.value + " ");
            merged = merged.next;
        }
    }
}
