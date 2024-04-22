package com.atguigu.study.datastruct.arrays;

/**
 * ClassName:MyArrayGeneric
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/19 16:36
 * @Version 1.0
 */
public class MyArrayGeneric<E> implements MyArray<E>{
   // 初始真是长度
   int size;
   // 数组
   E[] data;

   public MyArrayGeneric() {
      this(5);
   }

   public MyArrayGeneric(int capacity) {
      data = (E[]) new Object[capacity];
      size=0;
   }

   @Override
   public int getSize() {
      return size;
   }

   @Override
   public int getCapacity() {
      return data.length;
   }

   @Override
   public boolean isEmpty() {
      return size==0;
   }

   @Override
   public boolean isFull() {
      return size== data.length;
   }

   @Override
   public void addFrist(E e) {
//      for (int i = size; i >0 ; i--) {
//         data[i]=data[i-1];
//      }
//      size++;
//      data[0]=e;
      add(0,e);
   }

   @Override
   public void add(int index, E e) {
      //判断容器是否已满
      if (isFull()){
        E[] newCapacity = (E[]) new Object[getCapacity()*2];
         for (int i = 0; i < data.length; i++) {
            newCapacity[i]=data[i];
         }
         data=newCapacity;
      }
      for (int i = size; i >index; i--) {
         data[i]=data[i-1];
      }
      size++;
      data[index]=e;
   }

   @Override
   public void addLast(E e) {
      add(size,e);
   }

   @Override
   public E set(int index, E e) {
      E datum = data[index];
      data[index]=e;
      return datum;
   }

   @Override
   public E get(int index) {
      return data[index];
   }

   @Override
   public boolean contain(E e) {
      for (int i = 0; i < size; i++) {
        if (data[i]==e) {
           return true;
        }
      }
      return false;
   }

   @Override
   public void print() {
      for (int i = 0; i <size; i++) {
         System.out.println("data["+i+"] = " + data[i]);
      }
   }

   @Override
   public void printReverse() {
      for (int i = size-1; i >= 0; i--) {
         System.out.println("data["+i+"] = " + data[i]);
      }
   }
}
