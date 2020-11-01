package com.massagecommon.entity;

import lombok.Data;

/**
 * @author:WuShuang
 * @date:2020/5/3
 * @ver:1.0
 **/
@Data
public class EquipmentEntity {

    private String equipmentId;

    private String surplusTime;

    private Integer number;

    @Data
    public static class EquipmentDTO{
        private String teacherName;
        private String address;
        private String number;
        private String equipmentId;
        private String userName;
        private String startTime;
        private String experienceTime;
        private String customTime;
        private Integer status;
        private Integer addEquipment;
        private Integer id;
    }

}
