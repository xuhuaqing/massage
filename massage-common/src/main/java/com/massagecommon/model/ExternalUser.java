package com.massagecommon.model;

import lombok.Data;

/**
 * @program: massage
 * @description:
 * @author: wushuang
 * @create: 2021-03-24 22:08
 **/
@Data
public class ExternalUser {
    private String user_uuid;
    private String name;
    private String phone;
    private String device_number;
    private String scheme_code;
    private String scheme_name;
    private String total_number;
    private String total_time;
    private String did_number;
    private String state;
}
