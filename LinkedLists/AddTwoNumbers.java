package LinkedLists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**You are given two non-empty linkedlists representing two non-negative integers.
 * The digits are stored in reverse order, and each node contains a single digit.
 * Add the two numbers and return the result as a linked list.
 * 
 * INTUITION
 * 
 *  letssay we have 2->4->3 and 5->6-4
 * 
 *    243
 *    564
 * ------
 *    708
 * 
 * result is 7->0->8 or in the normal order 807
 * 
 * What we do?
 *  
 * add two nodes, we create a node to store the value and if it has a sum greater than 10, we carry 1
 * they aresingle digits and carry will never be greater than one.
 * 
 * for the next digit we add the carry+p.digit+q.digit and create a node and preverse the carry to the next.
 * 
 * At each iteration, current points to the last node of the result list,
carry stores the overflow from the previous digit,
and we correctly sum corresponding digits or treat missing digits as zero.
 */

class ListNode{
    public ListNode next;
    public int value;

    public ListNode(int value){
        this.value=value;
        this.next=null;
    }
}
public class AddTwoNumbers {

    public static ListNode add(ListNode l1, ListNode l2){
        ListNode dummy=new ListNode(0); //we usualy do this to hold on to the head
        //of the new list, we do this too when merging two linked lists.

        ListNode p=l1;
        ListNode q=l2;
        ListNode current=dummy;
        int carry=0;

        while(p!=null || q!=null){
              int sum=(p!=null? p.value:0)+(q!=null?q.value:0)+carry;
              carry=(int) Math.floor(sum/10);

              current.next=new ListNode(sum %10);
              current=current.next; //advance current pointer
              if(p!=null) p=p.next;
              if(q!=null) q=q.next;
        }

        //handle final carry
        if(carry > 0){
            current.next=new ListNode(carry);
        }

        return dummy.next;
    }

    //helpers

    private static ListNode arrayToList(int[] arr){
        if(arr==null || arr.length==0) return null;

        ListNode dummy=new ListNode(0);
        ListNode current=dummy;
        for(int val: arr){
            current.next= new ListNode(val);
            current=current.next;
        }

        return dummy.next;
    }

    private static int[] listToArray(ListNode head){
        if(head==null) return new int[0];
        List<Integer> intList=new ArrayList<>();

        while(head!=null){
            intList.add(head.value);
            head=head.next;
        }

        return intList.stream()
                      .mapToInt(Integer::intValue)
                      .toArray();
    }

    public static void main(String[] args){
        int[] arr1={2,4,3};
        int[] arr2={5,6,4};

        int[] res=listToArray(add(arrayToList(arr1),arrayToList(arr2)));

        System.out.println(Arrays.toString(res));
    }
    
}
