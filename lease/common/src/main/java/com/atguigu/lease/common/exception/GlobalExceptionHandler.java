package com.atguigu.lease.common.exception;

import com.atguigu.lease.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName:GlobalExceptionHandler
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/15 16:27
 * @Version 1.0
 */

//全局异常处理注解由spring-mvc提供
@ControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(Exception.class)
   @ResponseBody
   public Result error(Exception e){
      e.printStackTrace();
      return Result.fail();
   }
}