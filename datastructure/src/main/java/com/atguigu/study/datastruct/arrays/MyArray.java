package com.atguigu.study.datastruct.arrays;

import com.atguigu.study.datastruct.BaseMethod;

/**
 * ClassName:MyArray
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/19 16:21
 * @Version 1.0
 */
public interface MyArray<E> extends BaseMethod {
   /**
    *    封装数组达到类似arraylistd的效果
    *    实现增删改查含,扩缩正反印
    */
   // 向头部添加数据
   void addFrist(E e);
   // 向中间插入数据
   void add(int index,E e);
   // 向尾部插入数据
   void  addLast(E e);
   // 修改对应索引的数据并且返回原数据
   E set(int index,E e);
   // 查找索引位置的元素
   E get(int index);
   // 查找数组中是否包含元素
   boolean contain(E e);
   //扩容
   //缩容
   //11.正打印
   void print();

   //12.反打印
   void printReverse();
}
