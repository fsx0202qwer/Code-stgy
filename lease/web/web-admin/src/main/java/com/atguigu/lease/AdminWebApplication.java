package com.atguigu.lease;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ClassName:AdminWebApplication
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/12 9:14
 * @Version 1.0
 */
@SpringBootApplication
@EnableScheduling
public class AdminWebApplication {
   public static void main(String[] args) {
      SpringApplication.run(AdminWebApplication.class, args);
   }
}