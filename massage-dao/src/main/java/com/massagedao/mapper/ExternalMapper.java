package com.massagedao.mapper;

import com.github.pagehelper.Page;
import com.massagecommon.model.ExternalUser;
import com.massagecommon.model.PlaceOrderModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExternalMapper {
    /**
    * @Description: 客户添加订单
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    void addOrderByPlace(@Param("placeOrderModel") PlaceOrderModel placeOrderModel, @Param("externalUser") ExternalUser externalUser);

    /**
    * @Description: 校验订单唯一性
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    Integer only(@Param("scheme_code") String scheme_code);

    /**
    * @Description: 设备列表
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    List<String> getEqList(@Param("teacherId") String teacherId);

    /**
    * @Description:  已下单列表
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
     * @param teacherId
    */
    Page<Map<String,Object>> getOrderListByTeacherId(@Param("placeOrderModel") PlaceOrderModel placeOrderModel);

    /**
    * @Description: 老师添加设备
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    void addEquipment(@Param("businessId") String businessId, @Param("teacherId") String teacherId, @Param("equipmentId") String equipmentId);

    /**
    * @Description: 商家新设备列表
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    List<Map<String, String>> getBusinessEqList(@Param("businessId") String businessId);

    /**
    * @Description: 设备下的老师
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    List<Map<String, String>> getEqListBydeviceNumber(@Param("device_number") String device_number);
}
