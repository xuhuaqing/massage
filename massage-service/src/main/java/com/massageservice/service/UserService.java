package com.massageservice.service;

import com.massagecommon.entity.OrderEntity;
import com.massagecommon.entity.UserEntity;
import com.massagecommon.entity.UserOrderEntity;

import java.util.List;

public interface UserService {
    /**
     *  用户是否注册了
     *
     * @param userEntity
     * @return java.lang.Integer
     * @methodName isRegister
     * @author WuShunag
     * @date 14:55
     */
    UserEntity isRegister(UserEntity userEntity);


    /**
     *  查询用户已消耗订单
     *
     * @param userId
     * @return java.util.List<com.massagecommon.entity.UserOrderEntity>
     * @methodName findOrder
     * @author WuShunag
     * @date 15:31
     */
    List<UserOrderEntity> findOrder(UserEntity userId);

    /**
     *  未消耗订单
     *
     * @param userEntity
     * @return java.util.List<com.massagecommon.entity.UserOrderEntity>
     * @methodName userOrder
     * @author WuShunag
     * @date 15:54
     */
    List<UserOrderEntity> userOrder(UserEntity userEntity);

    /**
     *  商家登陆
     *
     * @param userEntity
     * @return com.massagecommon.entity.UserEntity
     * @methodName loginBusiness
     * @author WuShunag
     * @date 16:35
     */
    UserEntity loginBusiness(UserEntity userEntity);

    /**
     *  创建订单
     *
     * @param orderEntity
     * @return java.lang.String
     * @methodName submitOrder
     * @author WuShunag
     * @date 20:21
     */
    String submitOrder(OrderEntity orderEntity);

    /**
     *  老师登陆
     *
     * @param userEntity
     * @return com.massagecommon.entity.UserEntity
     * @methodName loginTeacher
     * @author WuShunag
     * @date 20:52
     */
    UserEntity loginTeacher(UserEntity userEntity);

    /**
     *  发送订单请求
     *
     * @param numberId
    * @param orderId
    * @param everyTime
    * @param equipmentId
     * @return java.lang.String
     * @methodName sendOrder
     * @author WuShunag
     * @date 16:19
     */
    String sendOrder(String numberId, String orderId, String everyTime, String equipmentId);

    /**
     *  商家发起请求
     *
     * @param equipmentId
     * @return java.lang.String
     * @methodName businessSendOrder
     * @author WuShunag
     * @date 1:45
     */
    String businessSendOrder(String equipmentId);

    /**
     *  老师到店 离店
     *
     * @param type
     * @param equipmentId
     * @return java.lang.String
     * @methodName teacherToShop
     * @author WuShunag
     * @date 0:35
     */
    String teacherToShop(String type, String equipmentId);

    /**
     *
     *
     * @param userEntity
     * @return java.lang.String
     * @methodName orderUser
     * @author WuShunag
     * @date 11:22
     */
    String orderUser(UserEntity userEntity);

    void isRegister2(UserEntity userEntity);
}
