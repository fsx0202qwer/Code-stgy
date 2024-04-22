package com.atguigu.study.datastruct.linkedlists;

/**
 * ClassName:MyLinkedList
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/20 15:32
 * @Version 1.0
 */
public class MyLinkedList <E>{
   public class Node<E>{
      public E e;
      public Node<E> next;
      public Node(){
         this(null,null);
      }
      public Node(E e){
         this(e,null);
      }
      public Node(E e,Node<E> next){
         this.e=e;
         this.next=next;
      }
   }

   public  Node<E> virtualHead;

   public int size;
   //构造
   public MyLinkedList(){
      this.virtualHead=new Node<>();
      this.size=0;
   }
   // 判断是否为空
   public boolean isEmpty(){
     return this.size==0;
   }
   // 获取链表中元素的实际个数
   public int getSize(){
      return size;
   }

   //增
   //向头部添加
   public void addFirst(E e){
      // Node<E> node = new Node<>();
      // node.next=head;
      // head=node;
      // 此为上面三行合并为一行的结果
      add(0,e);
   }
   //向中间添加
   public void add(int index,E e){
      if (index<0||index>size) new RuntimeException("链表不在有效边界");
      Node<E> prev=virtualHead;
      for (int i = 0; i <= index-1; i++) {
         prev=prev.next;
      }
      // Node<E> node = new Node<>(e);
      // node.next=prev.next;
      // prev.next=node;
      prev.next= new Node<E>(e,prev.next);
      size++;

   }
   //向尾部添加
   public void addLast(E e){
      add(size,e);
   }
   //删
   //删除头部
   public E removeFirst(){
      return remove(0);
   }
   //删除中间
   public E remove(int index){
      if(index < 0 || index >= size){
         throw new RuntimeException("链表不在有效边界");
      }
      Node<E> prev =virtualHead;
      for (int i = 0; i <=index-1; i++) {
         prev = prev.next;
      }
      Node<E> deleteNode=prev.next;
      prev.next=prev.next.next;
      deleteNode.next=null;
      size--;
      return deleteNode.e;
   }
   //删除尾部
   public E removeLast(){
      return remove(size-1);
   }
   //改
   public E set(int index ,E e){
      if(index < 0 || index >= size){
         throw new RuntimeException("链表不在有效边界");
      }
      Node<E> currentNode =virtualHead;
      for (int i = 0; i <=index; i++) {
         currentNode = currentNode.next;
      }
      E result=currentNode.e;
      currentNode.e=e;
      return result;
   }
   //查
   //查头数据
   public E getFirst(){
      return get(0);
   }
   //查指定索引数据
   public E get(int index){
      Node<E> currentNode =virtualHead;
      for (int i = 0; i <=index; i++) {
         currentNode = currentNode.next;
      }
      return currentNode.e;
   }
   public E getLast(){
      return get(size-1);
   }
   //含

   //打印
   public void print(){
      Node<E> head=virtualHead.next;
      StringBuffer sb = new StringBuffer();
      while (head!=null){
         sb.append(head.e)
                 .append("->");
         head= head.next;
      }
      sb.append("null");
      System.out.println(sb.toString());
   }

   public static void main(String[] args) {
      MyLinkedList<Integer> list = new MyLinkedList<>();
      list.addFirst(1);
      list.addFirst(2);
      list.addFirst(3);
      list.addFirst(4);
      list.addFirst(5);
//      list.remove(2);
//      list.print();
//      list.removeFirst();
//      list.print();
//      list.removeLast();
//      list.print();
      System.out.println(list.set(0,0));
      System.out.println(list.get(0));
   }
}
