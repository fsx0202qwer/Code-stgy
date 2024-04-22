package com.atguigu.study.datastruct.linkedlists;

/**
 * ClassName:LeetCode206ReverseLinkedList
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/21 17:24
 * @Version 1.0
 * 反转链表
 */
public class LeetCode206ReverseLinkedList {
   private static class ListNode {
      public int e;
      public ListNode next;

      public ListNode() {

      }

      public ListNode(int e) {
         this.e = e;
      }

      public ListNode(int e, ListNode next) {
         this.e = e;
         this.next = next;
      }

      @Override
      public String toString() {
         return "ListNode{" +
                 "e=" + e +
                 '}';
      }
   }
   //打印
   public void getList(ListNode head){
      while (head!=null){
         System.out.println("head.e = " + head.e);
         head = head.next;
      }
   }
   //反转
   public ListNode reverseList(ListNode head){
      //新链表头节点
      ListNode newNode = null;
      //旧链表的头节点
      ListNode currentNode=head;
      //临时存储要操作的旧链表的下一个节点
      ListNode tempNode=null;
      while (currentNode!=null){
         //先记录下一个节点
         /**
          * ①
          * new 1 ->null
          * old 2->3->4-null
          * ②
          * new 2->1->null
          * old 3->4->null
          */
         tempNode=currentNode.next;
         //将指向下一个节点变为null
         currentNode.next=newNode;
         //把旧链表头节点赋值给新节点
         newNode=currentNode;
         //更新旧链表的当前头节点
         currentNode=tempNode;
      }
      return newNode;
   }

   public static void main(String[] args) {
      LeetCode206ReverseLinkedList list = new LeetCode206ReverseLinkedList();
      ListNode node1 = new ListNode(1,null);
      ListNode node2 = new ListNode(2,node1);
      ListNode node3 = new ListNode(3,node2);
      ListNode node4 = new ListNode(4,node3);
      ListNode head=node4;
      list.getList(head);
      ListNode node = list.reverseList(head);
      list.getList(node);
   }
}
