package com.atguigu.lease.common.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName:MybatisMetaObjectHandler
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/12 15:34
 * @Version 1.0
 */
@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {
   @Override
   public void insertFill(MetaObject metaObject) {
      this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
   }

   @Override
   public void updateFill(MetaObject metaObject) {
      this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
   }
}