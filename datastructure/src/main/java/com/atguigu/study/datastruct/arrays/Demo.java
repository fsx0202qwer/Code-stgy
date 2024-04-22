package com.atguigu.study.datastruct.arrays;

/**
 * ClassName:Demo
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/19 16:48
 * @Version 1.0
 */
public class Demo {
   public static void main(String[] args) {
      MyArrayGeneric<Integer> data = new MyArrayGeneric<>();
      data.addFrist(1);
      data.addFrist(2);
      data.addFrist(3);
      data.addFrist(4);
      data.addLast(20);
      data.addLast(30);
      data.printReverse();
      data.print();
      System.out.println("data.isFull() = " + data.isFull());
   }
}
