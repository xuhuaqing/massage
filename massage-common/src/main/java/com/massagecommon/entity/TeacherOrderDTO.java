package com.massagecommon.entity;

import lombok.Data;

/**
 * @author:WuShuang
 * @date:2020/8/2
 * @ver:1.0
 **/
@Data
public class TeacherOrderDTO {

    private String orderId;

    private String orderName;

    private Double price;

    private Integer totalTimes;

    private String createTime;

    private String businessName;

    private String teacherName;

    private String userName;

    private String remarks;

    private String type;

    private String give;


    @Data
    public static class BusinessOrderDTO{

        private String type;

        private Double price;

        private String businessName;

        private Integer time;

        private Integer count;

    }
}
