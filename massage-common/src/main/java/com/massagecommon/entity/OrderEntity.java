package com.massagecommon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.User;

/**
 * @author:WuShuang
 * @date:2020/5/3
 * @ver:1.0
 **/
@Data
@NoArgsConstructor                 //无参构造
@AllArgsConstructor
public class OrderEntity extends UserEntity {


    private Integer parentId;

    private String orderName;
    private String temainingTimes;
    private String remarks;


    private String id;

    /**
     *  0是固定模式
     *
     *  1是自定义模式
     */
    private Integer orderType;

    private double price;

    /**
     * 总次数
     */
    private Integer totalTimes;
    private Integer times;

    private String businessId;

    private String userName;

    private String userPhone;

    /**
     *  每次时长
     */
    private Integer everyTime;

    /**
     * 总时长
     */
    private Integer projectTime;

    private String equipmentId;

    private String teacherId;

    private String numberId;

    private String createTime;

    /**
     * 0 是 不送  1是送
     */
    private String give;

    private String giveOrderName;

    /**
     * 总次数
     */
    private Integer giveTotalTimes;
    /**
     *  每次时长
     */
    private Integer giveEveryTime;
    /**
     * 总时长
     */
    private Integer giveProjectTime;

    /**
     * 体验订单 如果为1那就是说明是体验的
     */
    private Integer Experience = 0;



}
