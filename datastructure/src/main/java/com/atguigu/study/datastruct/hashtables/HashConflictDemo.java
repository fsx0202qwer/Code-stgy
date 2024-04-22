package com.atguigu.study.datastruct.hashtables;

import java.util.HashSet;
import java.util.Set;

/**
 * ClassName:HashConflictDemo
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/22 13:38
 * @Version 1.0
 */
public class HashConflictDemo {
   public static void main(String[] args) {
      System.out.println("AA".hashCode());
      System.out.println("BB".hashCode());
      System.out.println("----------------");
      System.out.println("Aa".hashCode());
      System.out.println("BB".hashCode());
      System.out.println("----------------");
      System.out.println("柳柴".hashCode());
      System.out.println("柴柕".hashCode());

      System.out.println("----------------");
      System.out.println(new Object().hashCode());
      System.out.println(new Object().hashCode());
      System.out.println(new Object().hashCode());
      System.out.println("----------------");

      Set set = new HashSet();
      int hashCodeValue;
      //第11万会出现hashCode冲突，每加1万差不多就多一次冲突。但不是固定的。
      for (int i = 1; i <= 190000; i++) {
         hashCodeValue = new Object().hashCode();
         if (set.contains(hashCodeValue)) {
            System.out.println("发生hash冲突，第" + i + "次，hashCodeValue " + hashCodeValue);
            continue;
         } else {
            set.add(hashCodeValue);
         }
      }
      System.out.println("size=" + set.size());
   }
}