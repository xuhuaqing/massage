package com.massageservice.service;

import com.massagecommon.model.ExternalModel;
import com.massagecommon.model.PlaceOrderModel;

public interface ExternalService {
    /** 
    * @Description: 录入客户订单信息 
    * @Param:  
    * @return:  
    * @Author: wushuang
    * @Date:  
    */
    String addOrderByPlace(PlaceOrderModel placeOrderModel, ExternalModel externalModel);
}
