package com.massagedao.mapper;

import com.github.pagehelper.Page;
import com.massagecommon.model.ExternalUser;
import com.massagecommon.model.PlaceOrderModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
    * @Description: 获取设备 根据用户id
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    Set<String> getEqByUserId(@Param("userId") String userId);

    /**
    * @Description: 根据设备id 查询商家名称 和id
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    List<Map<String, String>> findBusinessName(@Param("id") Set<String> eqIds);

    /**
    * @Description:   获取 显示id
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    List<Map<String, String>> getEqShowIdByEqId(@Param("id") Set<String> eqByUserId);

    /**
    * @Description:  显示已消耗订单
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    Page<Map<String, String>> getConsumedOrder(@Param("userId") String userId);
    Page<Map<String, String>> getNotConsumedOrder(@Param("userId") String userId);

    /**
    * @Description: 设备状态
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    Integer eqStatus(@Param("eqId") String eqId);

    /**
    * @Description:   获取订单次数
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    Integer getNotConsumedOrderCount(@Param("userId") String userId, @Param("orderId") String orderId);

    /**
    * @Description:  订单的code
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    String findDeviceNumber(@Param("orderId") String orderId);

    /**
    * @Description: 修改订单次数
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    void updateDidNumber(@Param("userId") String userId, @Param("orderId") String orderId);


    String getBusinessNameByEqId(@Param("device_number") String device_number);


    void privateAddEquipment(@Param("equipmentId") String equipmentId, @Param("userName") String userName);
}
