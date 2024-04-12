package com.atguigu.lease.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "创建时间")
    @JsonIgnore  //响应浏览器时忽略字段
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @Schema(description = "更新时间")
    @JsonIgnore  //响应浏览器时忽略字段
    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    @Schema(description = "逻辑删除")
    @JsonIgnore  //响应浏览器时忽略字段
    @TableLogic  //逻辑删除的注解
    @TableField("is_deleted")
    private Byte isDeleted;

}