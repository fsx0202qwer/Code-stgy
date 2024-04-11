package com.atguigu.lease.common.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:MybatisPlusConfiguration
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/11 20:35
 * @Version 1.0
 */
@Configuration
@MapperScan("com.atguigu.lease.web.*.mapper")
public class MybatisPlusConfiguration {

}
