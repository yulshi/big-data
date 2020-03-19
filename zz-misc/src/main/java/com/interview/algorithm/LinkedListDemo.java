package com.interview.algorithm;

import lombok.ToString;
import org.w3c.dom.NodeList;
import sun.security.util.Length;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 206. Reverse Linked List
 *
 * @author yulshi
 * @create 2019/11/28 16:20
 */
public class LinkedListDemo {

    public static void main(String[] args) {

        ListNode head = new ListNode(1);
        ListNode currNode = head;

        for (int i = 2; i <= 5; i++) {
            ListNode node = new ListNode(i);
            currNode.next = node;
            currNode = node;
        }
        System.out.println("the original linked list");
        System.out.println(head);
        //currNode.next = head.next ;
        //System.out.println(head);
        System.out.println("the reversed linked list");
        System.out.println(reverseList(head));
        //System.out.println(swapPairs(head));
        //System.out.println(detectCycle(head).val);
    }

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) { val = x; }
     * }
     */
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            List<Integer> list = new ArrayList<>();
            ListNode node = this;
            while (node != null) {
                list.add(node.val);
                node = node.next;
            }
            return list.toString();
        }
    }

    /**
     * 206. Reverse Linked List
     *
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head) {

        ListNode prev = null;
        ListNode curr = head;
        ListNode next = null;

        ListNode reversed = null;

        while (curr != null) {

            // Keep the next node in a temporary variable
            next = curr.next;

            if (curr.next == null) {
                reversed = curr;
            }

            // move prev and curr forward by one node
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        return reversed;

    }

    /**
     * 24. Swap Nodes in Pairs
     * Given 1->2->3->4->5->6, you should return the list as 2->1->4->3->6->5.
     *
     * @param head
     * @return
     */
    public static ListNode swapPairs(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode prev = null, left = head, right = head.next;
        ListNode result = right;

        while (left != null && right != null) {

            // Keep a temporary pointer to the next group
            ListNode nextLeft = right.next;

            // swap the left node and right node
            right.next = left;
            left.next = nextLeft;
            if (prev != null) {
                prev.next = right;
            }
            prev = left;

            if (nextLeft == null) {
                break;
            }
            // move forward to next group
            left = nextLeft;
            right = left.next;
        }

        return result;
    }

    /**
     * 141. Linked List Cycle
     * Given a linked list, determine if it has a cycle in it.
     *
     * @param head
     * @return
     */
    public static boolean hasCycle(ListNode head) {

//        // Having a HashSet to keep track of the record of node
//        Set<Integer> cache = new HashSet<>();
//        while (head != null) {
//            if (cache.contains(head.hashCode())) {
//                return true;
//            }
//            cache.add(head.hashCode());
//            head = head.next;
//        }
//        return false;

        /** Two pointers, one traverse two in one time and another traverse just one **/
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    /**
     * 142. Linked List Cycle II
     * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
     *
     * @param head
     * @return
     */
    public static ListNode detectCycle(ListNode head) {

        // Option 1: use hashSet to keep track of accessed node
        Set<ListNode> visited = new HashSet<>();
        while (head != null) {
            if (visited.contains(head)) {
                return head;
            }
            visited.add(head);
            head = head.next;
        }
        return null;

        // Option 2: Having a fast and slow pointer
//        if (head == null || head.next == null) {
//            return null;
//        }
//
//        ListNode slow = head.next;
//        ListNode fast = head.next.next;
//        while (slow != fast) {
//            if (fast == null || fast.next == null) {
//                return null;
//            }
//            slow = slow.next;
//            fast = fast.next.next;
//        }
//
//        System.out.println("found the meet point: " + slow.val);
//        System.out.println("start: " + head.val);
//
//
//        ListNode newStarter = head;
//        while(newStarter != slow) {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(newStarter.val + ", " + slow.val);
//            newStarter = newStarter.next;
//            slow = slow.next;
//        }
//
//        return slow;

    }

    /**
     * 25. Reverse Nodes in k-Group
     * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
     *
     * Given this linked list: 1->2->3->4->5
     * For k = 2, you should return: 2->1->4->3->5
     * For k = 3, you should return: 3->2->1->4->5
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        //TODO
        return null;
    }
}
