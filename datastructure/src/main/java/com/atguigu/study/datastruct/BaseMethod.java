package com.atguigu.study.datastruct;

/**
 * ClassName:BaseMethod
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/19 16:26
 * @Version 1.0
 */
public interface BaseMethod {
   // 获取真实长度
   int getSize();
   // 获取初始长度
   int getCapacity();
   // 判断是否为空
   boolean isEmpty();
   // 判断是否已满
   boolean isFull();
}
