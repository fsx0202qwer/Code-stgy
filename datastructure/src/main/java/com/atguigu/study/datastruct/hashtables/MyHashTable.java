package com.atguigu.study.datastruct.hashtables;

import java.lang.reflect.Array;

/**
 * ClassName:MyHashTable
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/22 13:39
 * @Version 1.0
 */
public class MyHashTable <K,V>{
   private static class Node<K,V>{

      K key;

      V value;

      Node<K,V> next;

      public Node(K key, V value, Node<K, V> next) {
         this.key = key;
         this.value = value;
         this.next = next;
      }

      //链表内容展示形式为：[(k1:v1)->(k2:v2)->(k3:v3)->null]
      @Override
      public String toString() {
         StringBuffer stringBuffer = new StringBuffer();
         stringBuffer.append("[");
         Node<K, V> currentNode = this;
         while (currentNode != null) {
            stringBuffer.append("(").append(currentNode.key).append(":").append(currentNode.value).append(")->");
            currentNode = currentNode.next;
         }
         stringBuffer.append("null]");
         return stringBuffer.toString();
      }
   }
   //2.Field + Constant
   private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
   private static final float DEFAULT_LOAD_FACTOR = 0.75f;
   private Node<K, V>[] array;
   private int size;

   //3.constructor
   public MyHashTable() {
      this.array = new Node[DEFAULT_INITIAL_CAPACITY];
      this.size = 0;
   }
   //4.基础操作
   public int getSize() {
      return size;
   }

   public boolean isEmpty() {
      return size == 0;
   }
   //自定义哈希函数，计算key对应的数组的索引值。
   private int hash(K key, int length) {
      //hashCode()方法的哈希值可能为负数
      //与Integer.MAX_VALUE进行与操作可以保证是一个正数
      //Integer.MAX_VALUE (0111 1111 1111 1111 1111 1111 1111 1111)
      return (key.hashCode() & Integer.MAX_VALUE) % length;
   }
   //增,改：添加元素
   public void put(K key,V value){
      int index=hash(key,array.length);
      Node<K,V> currentNode=array[index];
      //currentNode 不等于null 说明该索引位置不为空
      while (currentNode!=null){
         if(currentNode.key.equals(key)){
            // 此时说明 hash中存在 该key 则为修改操作
            currentNode.value=value;
            return;
         }
         currentNode=currentNode.next;
      }
      //此时说明currentNode 为 null
      Node<K, V> kvNode = new Node<>(key, value, array[index]);
      array[index]=kvNode;
      size++;
      if((float)size/array.length>DEFAULT_LOAD_FACTOR){
         grow();
      }
   }

   //负载因子大于0.75时扩容
   private void grow() {
      Node<K, V>[] newArray = new Node[array.length * 2];
      //先取出每一个节点
      for (int i = 0; i < array.length; i++) {
         Node<K,V> currentNode=array[i];
         while (currentNode!=null){
            // 计算新家的地址
            int index=hash(currentNode.key,newArray.length);
            // 存储下一个节点
            Node<K, V> next = currentNode.next;
            //将新节点赋值个体currentNode的next
            currentNode.next=newArray[index];
            //将原来的头部节点currentNode挂在新数组上
            newArray[index]=currentNode;
            //将存储的下一个节点作为原来的头节点
            currentNode=next;
         }
      }
      //用新节点替换旧节点
      this.array=newArray;
   }
   //删：根据key值删除元素 并把这个元素节点的值返回
   public V remove(K key){
      V result=null;
      int index = hash(key, array.length);
      Node<K, V> currentNode = array[index];
      // currentNode 为 null 时 说明对应节点为空
      if(currentNode==null){
         throw new RuntimeException(key + "对应的节点为空，无法删除");
      }
      // 假如头部节点的key恰好为对应的key时
      if(currentNode.key.equals(key)){
         result=currentNode.value;
         array[index] = currentNode.next;
         currentNode.next = null;
         size--;
      }
      return result;
   }
   //查: 通过key查询对应的value值
   public V get(K key){
      int index = hash(key, array.length);
      for (Node<K,V> currentNode = array[index]; currentNode != null; currentNode = currentNode.next){
         if(currentNode.key.equals(key)){
            return currentNode.value;
         }
      }
      return null;
   }

   public static void main(String[] args) {
      MyHashTable<Integer, Integer> hashTable = new MyHashTable<>();
      hashTable.put(1,1);
      hashTable.put(2,2);
      hashTable.put(3,3);
      hashTable.put(4,4);
      System.out.println("hashTable.get(1) = " + hashTable.get(1));
      System.out.println("hashTable.getSize() = " + hashTable.getSize());
      hashTable.put(1,100);
      System.out.println("hashTable.get(1) = " + hashTable.get(1));
      System.out.println("hashTable.getSize() = " + hashTable.getSize());
      hashTable.remove(1);
      System.out.println("hashTable.getSize() = " + hashTable.getSize());

   }
}
