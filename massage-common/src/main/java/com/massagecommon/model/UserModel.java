package com.massagecommon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: massage
 * @description:
 * @author: wushuang
 * @create: 2021-03-24 21:33
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel {

    private String userPhone;

    private String userName;
}
