package com.massageservice.service;

import com.massagecommon.model.PlaceOrderModel;

import java.util.List;

public interface ExternalService {
    /** 
    * @Description: 录入客户订单信息 
    * @Param:  
    * @return:  
    * @Author: wushuang
    * @Date:  
    */
    String addOrderByPlace(PlaceOrderModel placeOrderModel, Object externalModel);

    /** 
    * @Description: 获取老师添加的设备列表 
    * @Param:  
    * @return:  
    * @Author: wushuang
    * @Date:  
    */
    List<String> getEqList(String teacherId);

    /**
    * @Description: 已下单列表
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    String getOrderListByTeacherId(PlaceOrderModel placeOrderModel);

    /** 
    * @Description: 老师添加设备
    * @Param:  
    * @return:  
    * @Author: wushuang
    * @Date:  
    */
    String addEquipment(String businessId, String teacherId, String equipmentId);

    /**
    * @Description: 获取商家新设备列表
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    String getBusinessEqList(String businessId);

    /** 
    * @Description: 商家登陆 
    * @Param:  
    * @return:  
    * @Author: wushuang
    * @Date:  
    */
    String getEqListByBusinessId(String businessId);
}
