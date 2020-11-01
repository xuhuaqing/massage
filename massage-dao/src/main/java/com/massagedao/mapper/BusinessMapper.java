package com.massagedao.mapper;

import com.github.pagehelper.Page;
import com.massagecommon.entity.BusinessEntity;
import com.massagecommon.entity.EquipmentEntity;
import com.massagecommon.entity.OrderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusinessMapper {

    /**
     * 商家列表
     *
     * @param
     * @param businessName
     * @return java.util.List<com.massagecommon.entity.BusinessEntity>
     * @methodName businessList
     * @author WuShunag
     * @date 15:23
     */
    List<BusinessEntity> businessList(@Param("businessName") String businessName,@Param("provincialId")String provincialId);

    /**
     * 修改
     *
     * @param equipmentDTO
     * @return void
     * @methodName updEquipment
     * @author WuShunag
     * @date 21:45
     */
    void updEquipment(@Param("equipmentDTO") EquipmentEntity.EquipmentDTO equipmentDTO);

    /**
     * @param businessId
     * @return java.lang.Double
     * @methodName moneyUnsettled
     * @author WuShunag
     * @date 10:55
     */
    Double moneyUnsettled(@Param("businessId") String businessId);

    /**
     * 根据用户名称搜索
     *
     * @param searchName
     * @param businessId
     * @return com.github.pagehelper.Page<com.massagecommon.entity.OrderEntity>
     * @methodName findOrder
     * @author WuShunag
     * @date 19:44
     */
    Page<OrderEntity> findOrder(@Param("searchName") String searchName, @Param("businessId") String businessId);

    /**
     * 未结算业绩
     *
     * @param businessId
     * @return java.lang.Integer
     * @methodName findAchievement
     * @author WuShunag
     * @date 0:36
     */
    Integer findAchievement(@Param("businessId") String businessId);
}
