package com.massagecommon.entity;

import lombok.Data;

/**
 * @author:WuShuang
 * @date:2020/5/4
 * @ver:1.0
 **/
@Data
public class BusinessDTO {

    private String userId;

    private String userName;

    private String address;

    private String password;

    private String phone;

    private String type;

    private String createTime;

    private String achievement;

    private Integer teacherType = 0;

    private String id;

    private String provincialId;

    private Integer isPrivate = 0;
}
