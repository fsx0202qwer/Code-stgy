package com.atguigu.lease.common.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:MybatisPlusConfiguration
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/12 9:06
 * @Version 1.0
 */
@SpringBootConfiguration
@MapperScan("com.atguigu.lease.web.*.mapper")
public class MybatisPlusConfiguration {

}