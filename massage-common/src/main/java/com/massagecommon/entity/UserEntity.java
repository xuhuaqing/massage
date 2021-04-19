package com.massagecommon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:WuShuang
 * @date:2020/5/3
 * @ver:1.0
 **/
@Data
@NoArgsConstructor                 //无参构造
@AllArgsConstructor
public class UserEntity {

    private String userId;

    private Integer type;

    private String userName;

    private String userPhone;

    private String businessId;

    private String password;

    private String equipmentId;

    private String openId;

    private Integer teacherType = 0;

    private String provincialId;

    /**
     * 1 是私密账号登陆
     */
    private Integer isPrivate = 0;


}
