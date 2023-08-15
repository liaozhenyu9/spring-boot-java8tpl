package com.lzy.java8tpl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 业务类型表
 * </p>
 *
 * @author abc
 * @since 2023-08-15
 */
@Getter
@Setter
@TableName("biz")
@ApiModel(value = "Biz对象", description = "业务类型表")
public class Biz implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "biz_id", type = IdType.AUTO)
    private Integer bizId;

    @ApiModelProperty("业务编码")
    @TableField("biz_code")
    private String bizCode;

    @ApiModelProperty("别名")
    @TableField("alias")
    private String alias;

    @ApiModelProperty("业务描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("创建者")
    @TableField("creator")
    private String creator;

    @ApiModelProperty("是否删除 0:未删除 1:已删除")
    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    @TableField("gmt_modified")
    private LocalDateTime gmtModified;
}
