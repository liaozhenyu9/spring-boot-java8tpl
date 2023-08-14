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
 * 设备信息表
 * </p>
 *
 * @author abc
 * @since 2023-08-14
 */
@Getter
@Setter
@TableName("device")
@ApiModel(value = "Device对象", description = "设备信息表")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "device_id", type = IdType.AUTO)
    private Integer deviceId;

    @TableField("biz_id")
    private Integer bizId;

    @ApiModelProperty("设备编码 由设备端生成")
    @TableField("device_code")
    private String deviceCode;

    @ApiModelProperty("设备序列号")
    @TableField("device_sn")
    private String deviceSn;

    @ApiModelProperty("设备名称")
    @TableField("device_name")
    private String deviceName;

    @ApiModelProperty("设备类型")
    @TableField("type_id")
    private Integer typeId;

    @ApiModelProperty("机型")
    @TableField("model")
    private String model;

    @ApiModelProperty("品牌商")
    @TableField("brand")
    private String brand;

    @ApiModelProperty("制造商")
    @TableField("manufacturer")
    private String manufacturer;

    @ApiModelProperty("工业设计名称")
    @TableField("industry_design_name")
    private String industryDesignName;

    @ApiModelProperty("安卓版本")
    @TableField("android_version")
    private String androidVersion;

    @ApiModelProperty("编译版本")
    @TableField("display_id")
    private String displayId;

    @ApiModelProperty("sdk 版本")
    @TableField("sdk_int")
    private String sdkInt;

    @ApiModelProperty("主板参数 同cpu")
    @TableField("board")
    private String board;

    @ApiModelProperty("硬件类型 同cpu")
    @TableField("hardware")
    private String hardware;

    @ApiModelProperty("主板平台")
    @TableField("platform")
    private String platform;

    @ApiModelProperty("指令集类型")
    @TableField("supported_abis")
    private String supportedAbis;

    @ApiModelProperty("内存大小")
    @TableField("total_memory")
    private Integer totalMemory;

    @ApiModelProperty("硬盘大小")
    @TableField("total_storage")
    private Integer totalStorage;

    @ApiModelProperty("cpu 名称")
    @TableField("cpu_name")
    private String cpuName;

    @ApiModelProperty("cpu 内核数")
    @TableField("cpu_core_count")
    private Integer cpuCoreCount;

    @ApiModelProperty("cpu 最大平率")
    @TableField("cpu_max_freq")
    private String cpuMaxFreq;

    @ApiModelProperty("cpu 最小频率")
    @TableField("cpu_min_freq")
    private String cpuMinFreq;

    @ApiModelProperty("是否删除 0:未删除 1:已删除")
    @TableField("is_deleted")
    private Boolean isDeleted;

    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    @TableField("gmt_modified")
    private LocalDateTime gmtModified;
}
