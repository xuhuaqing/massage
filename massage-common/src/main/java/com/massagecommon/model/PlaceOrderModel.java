package com.massagecommon.model;

import lombok.Data;

/**
 * @program: massage
 * @description: 下单
 * @author: wushuang
 * @create: 2021-03-24 23:58
 **/
@Data
public class PlaceOrderModel {

    private String schemeId;

    private double amount;

    private String remarks;
}
