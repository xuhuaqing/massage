package com.massagedao.mapper;

import com.massagecommon.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {


    /**
     * 是否注册了
     *
     * @param userEntity
     * @return java.lang.Integer
     * @methodName isRegister
     * @author WuShunag
     * @date 14:57
     */
    UserEntity isRegister(@Param("userEntity") UserEntity userEntity);

    /**
     * 创建用户
     *
     * @param userEntity
     * @return void
     * @methodName register
     * @author WuShunag
     * @date 15:08
     */
    void register(@Param("userEntity") UserEntity userEntity);

    /**
     * 查询用户订单
     *
     * @param userId
     * @return java.util.List<com.massagecommon.entity.UserOrderEntity>
     * @methodName findOrder
     * @author WuShunag
     * @date 15:32
     */
    List<UserOrderEntity> findOrder(@Param("userEntity") UserEntity userEntity);

    /**
     * 用户未消耗订单
     *
     * @param userEntity
     * @return java.util.List<com.massagecommon.entity.UserOrderEntity>
     * @methodName userOrder
     * @author WuShunag
     * @date 15:55
     */
    List<UserOrderEntity> userOrder(@Param("userEntity") UserEntity userEntity);

    /**
     * 商家登陆
     *
     * @param userEntity
     * @return com.massagecommon.entity.UserEntity
     * @methodName loginBusiness
     * @author WuShunag
     * @date 16:36
     */
    UserEntity loginBusiness(@Param("userEntity") UserEntity userEntity);

    /**
     * 创建订单
     *
     * @param orderEntity
     * @return void
     * @methodName submitOrder
     * @author WuShunag
     * @date 20:29
     */
    void submitOrder(@Param("orderEntity") OrderEntity orderEntity);

    /**
     * 老师登陆
     *
     * @param userEntity
     * @return com.massagecommon.entity.UserEntity
     * @methodName loginTeacher
     * @author WuShunag
     * @date 20:53
     */
    UserEntity loginTeacher(@Param("userEntity") UserEntity userEntity);

    /**
     * 修改用户订单id 和 修改用户次数
     *
     * @param numberId
     * @param s
     * @return void
     * @methodName sendOrder
     * @author WuShunag
     * @date 16:24
     */
    void sendOrder(@Param("numberId") String numberId, @Param("s") String s);

    /**
     * 查询商家市场id
     *
     * @param equipmentId
     * @return java.lang.String
     * @methodName findOrderByBusiness
     * @author WuShunag
     * @date 22:27
     */
    String findOrderByBusiness(@Param("equipmentId") String equipmentId);

    void updateTime(@Param("equipmentId") String equipmentId, @Param("s") String s);

    /**
     * shanghu 发起请求
     *
     * @param equipmentId
     * @return com.massagecommon.entity.EquipmentEntity.EquipmentDTO
     * @methodName businessSendOrder
     * @author WuShunag
     * @date 1:49
     */
    EquipmentEntity.EquipmentDTO businessSendOrder(@Param("equipmentId") String equipmentId);


    Integer selectVersion(@Param("numberId") String numberId);

    Integer selectTotal(@Param("numberId") String numberId);

    List<BusinessEntity> orderUser(@Param("userEntity") UserEntity userEntity);


    List<BusinessEntity> getbusinessList(@Param("password") String password);


    UserEntity isRegister1(@Param("userEntity") UserEntity userEntity);
    UserEntity isRegister5(@Param("userEntity") UserEntity userEntity);


    void updateOpenId(@Param("userEntity") UserEntity userEntity);

    void register2(@Param("userEntity") UserEntity userEntity);
    void register3(@Param("userEntity") UserEntity userEntity);

    void giveOrder(@Param("orderEntity")OrderEntity orderEntity,@Param("orderId")String orderId);

    Integer teacherIsExist(@Param("teacherId") String teacherId);
}
