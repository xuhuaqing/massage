package com.massagecommon.entity;

import lombok.Data;

/**
 * @author:WuShuang
 * @date:2020/5/3
 * @ver:1.0
 **/
@Data
public class UserOrderEntity {

    private String numberId;

    private String orderId;

    private String orderName;

    /**
     * 0是固定模式
     * 1是自定义模式
     */
    private String orderType;

    private double price;

    private Integer totalTimes;

    private Integer times;
    /**
     * 每次时长
     */
    private Integer everyTime;

    private String createTime;

    private String equipmentId;

    private String parentId;


}
