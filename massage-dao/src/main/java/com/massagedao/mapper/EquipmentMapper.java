package com.massagedao.mapper;

import com.massagecommon.entity.EquipmentEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EquipmentMapper {
    /**
     * 设备列表
     *
     * @param businessId
     * @param type
     * @return java.util.List<com.massagecommon.entity.EquipmentEntity>
     * @methodName equipmentList
     * @author WuShunag
     * @date 16:52
     */
    List<EquipmentEntity> equipmentList(@Param("businessId") String businessId, @Param("type") String type);

    /**
     * 是否下架了
     *
     * @param equipmentId
     * @return java.lang.Integer
     * @methodName isdelete
     * @author WuShunag
     * @date 20:26
     */
    Integer isdelete(@Param("equipmentId") String equipmentId);

    /**
     * 老师是否添加了设备
     *
     * @param equipmentId
     * @return java.lang.Integer
     * @methodName isAddEquipment
     * @author WuShunag
     * @date 20:29
     */
    Integer isAddEquipment(@Param("equipmentId") String equipmentId);

    /**
     * 添加设备
     *
     * @param businessId
     * @param teacherId
     * @param equipmentId
     * @return void
     * @methodName addEquipment
     * @author WuShunag
     * @date 21:01
     */
    void addEquipment(@Param("businessId") String businessId, @Param("teacherId") String teacherId, @Param("equipmentId") String equipmentId);


    List<EquipmentEntity> equipmentListByUser(@Param("businessId") String businessId, @Param("type") String type);

    /**
     * 老师到店
     *
     * @param i
     * @param equipmentId
     * @return void
     * @methodName teacherToShop
     * @author WuShunag
     * @date 1:03
     */
    void teacherToShop(@Param("i") int i, @Param("equipmentId") String equipmentId);

    /**
     *
     *  消耗记录
     * @param numberId
    * @param equipmentId
     * @return void
     * @methodName addOrderRecord
     * @author WuShunag
     * @date 19:56
     */
    void addOrderRecord(@Param("numberId")String numberId, @Param("equipmentId")String equipmentId);

    String selectParentId(@Param("numberId")String numberId);
}
