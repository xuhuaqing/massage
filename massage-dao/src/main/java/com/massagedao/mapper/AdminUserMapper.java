package com.massagedao.mapper;

import com.github.pagehelper.Page;
import com.massagecommon.entity.*;
import com.massagecommon.model.ExternalUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminUserMapper {
    /**
     * 用户登陆
     *
     * @param adminCommon
     * @return com.massagecommon.entity.AdminCommon
     * @methodName adminLogin
     * @author WuShunag
     * @date 0:59
     */
    Integer adminLogin(@Param("adminCommon") AdminCommon adminCommon);

    /**
     * 查询老密码
     *
     * @Author: WuShuang on 2019/12/26  16:27
     * @param: [md5]
     * @return: java.lang.Integer
     * @Description:
     */
    Integer selectOldPas(@Param("md5") String md5);

    /**
     * 修改密码
     *
     * @Author: WuShuang on 2019/12/26  16:29
     * @param: [md5]
     * @return: void
     * @Description:
     */
    void changePassword(@Param("md5") String md5);

    /**
     * 商户列表
     *
     * @param
     * @param type
     * @param isPrivate
     * @return com.github.pagehelper.Page<com.massagecommon.entity.BusinessDTO>
     * @methodName findBusinessManage
     * @author WuShunag
     * @date 2:11
     */
    Page<BusinessDTO> findBusinessManage(@Param("type") Integer type, @Param("agentId") String agentId, @Param("teacherType") String teacherType, @Param("userId") String userId,@Param("isPrivate") String isPrivate);

    /**
     * 删除用户
     *
     * @param userId
     * @return void
     * @methodName deleteBusiness
     * @author WuShunag
     * @date 2:17
     */
    void deleteBusiness(@Param("userId") String userId);

    /**
     * 添加用户
     *
     * @param businessDTO
     * @return void
     * @methodName businessAdd
     * @author WuShunag
     * @date 2:29
     */
    void businessAdd(@Param("businessDTO") BusinessDTO businessDTO);

    /**
     * 修改商户
     *
     * @param businessDTO
     * @return void
     * @methodName updateBusiness
     * @author WuShunag
     * @date 2:36
     */
    void updateBusiness(@Param("businessDTO") BusinessDTO businessDTO);

    /**
     * 一级分类
     *
     * @param
     * @return com.github.pagehelper.Page<com.massagecommon.entity.ProjectEntity>
     * @methodName findOneClassification
     * @author WuShunag
     * @date 15:50
     */
    Page<ProjectEntity.ProjectFour> findOneClassification(@Param("projectId") String projectId);

    /**
     * 修改一级分类
     *
     * @param projectId
     * @param projectImg
     * @return void
     * @methodName updOneClassification
     * @author WuShunag
     * @date 16:11
     */
    void updOneClassification(@Param("projectId") String projectId, @Param("projectImg") String projectImg);

    /**
     * 三级分类
     *
     * @param projectId
     * @return com.massagecommon.entity.ProjectEntity.ProjectFour
     * @methodName showClassification
     * @author WuShunag
     * @date 17:13
     */
    ProjectEntity.ProjectFour showClassification(@Param("projectId") String projectId);

    /**
     * 修改分类
     *
     * @param projectFour
     * @return void
     * @methodName updateClassificationProject
     * @author WuShunag
     * @date 17:34
     */
    void updateClassificationProject(@Param("projectFour") ProjectEntity.ProjectFour projectFour);

    /**
     * 设备列表
     *
     * @param equipmentId
     * @param userName
     * @return com.github.pagehelper.Page<com.massagecommon.entity.EquipmentEntity.EquipmentDTO>
     * @methodName findEquipment
     * @author WuShunag
     * @date 18:52
     */
    Page<EquipmentEntity.EquipmentDTO> findEquipment(@Param("equipmentId") String equipmentId,@Param("userName") String userName);

    String selectTeacherName(@Param("teacherId") String teacherId);

    String selectTeacherName1(@Param("teacherId") String teacherId);

    /**
     * 启用 或者 停用设备
     *
     * @param equipmentId
     * @param status
     * @return void
     * @methodName upEquipment
     * @author WuShunag
     * @date 19:52
     */
    void upEquipment(@Param("equipmentId") String equipmentId, @Param("status") String status);

    /**
     * 添加设备
     *
     * @param equipmentDTO
     * @return void
     * @methodName addEquipment
     * @author WuShunag
     * @date 20:15
     */
    void addEquipment(@Param("equipmentDTO") EquipmentEntity.EquipmentDTO equipmentDTO);

    /**
     * 订单列表
     *
     * @param equipmentDTO
     * @param userId
     * @param projectName
     * @return void
     * @methodName addEquipment
     * @author WuShunag
     * @date 20:15
     */
    Page<OrderEntity> findOrder(@Param("orderName") String orderName, @Param("userId") String userId,  @Param("projectName")String projectName);

    /**
     * 导出数据
     *
     * @param id
     * @return java.util.List<com.massagecommon.entity.OrderEntity>
     * @methodName exportExcelByUser
     * @author WuShunag
     * @date 0:30
     */
    List<OrderEntity> exportExcelByUser(@Param("id") String[] id);

    /**
     * 修改订单
     *
     * @Author: WuShuang on 2020/6/5  16:40
     * @param: [id, totalTimes]
     * @return: void
     * @Description:
     */
    void updateOrder(@Param("id") String id, @Param("totalTimes") String totalTimes);

    /**
     * 判断是否有设备了
     *
     * @param equipmentId
     * @return java.lang.Integer
     * @methodName isEquipment
     * @author WuShunag
     * @date 21:42
     */
    Integer isEquipment(@Param("equipmentId") String equipmentId);

    /**
     * 业绩结算
     *
     * @param userId
     * @return void
     * @methodName settleBusiness
     * @author WuShunag
     * @date 0:46
     */
    void settleBusiness(@Param("userId") String userId);

    /**
     *  当月的订单
     *
     * @param date
    * @param endDate
    * @param userId
     * @return java.util.List<com.massagecommon.entity.TeacherOrderDTO>
     * @methodName exportExcelByTeacherManage
     * @author WuShunag
     * @date 18:43
     */
    List<TeacherOrderDTO> exportExcelByTeacherManage(@Param("date")String date, @Param("endDate")String endDate, @Param("id")String[] userId);

    /**
     *  店家当月的消耗量
     *
     * @param date
    * @param endDate
    * @param userId
     * @return java.util.List<com.massagecommon.entity.TeacherOrderDTO.BusinessOrderDTO>
     * @methodName exportExcelByBusinessManage
     * @author WuShunag
     * @date 21:20
     */
    List<TeacherOrderDTO.BusinessOrderDTO> exportExcelByBusinessManage(@Param("date")String date, @Param("endDate")String endDate,@Param("id") String[] userId);

    /**
     *  修改账号名称
     *
     * @param totalTimes
     * @param userPhone
     * @return void
     * @methodName updateName
     * @author WuShunag
     * @date 23:04
     */
    void updateName(@Param("name") String totalTimes,@Param("userName") String userName);

    /**
     *  修改订单名称
     *
     * @param id
    * @param totalTimes
     * @return void
     * @methodName updateOrderName
     * @author WuShunag
     * @date 23:16
     */
    void updateOrderName(@Param("id") String id,@Param("name") String totalTimes,@Param("phone")String userName);

    /** 删除订单
     *
     *
     * @param id
     * @return void
     * @methodName deleteOrder
     * @author WuShunag
     * @date 23:21
     */
    void deleteOrder(@Param("id")String id);

    /**
     *  商家的销售订单
     *
     * @param date
    * @param endDate
    * @param userId
     * @return java.util.List<com.massagecommon.entity.TeacherOrderDTO>
     * @methodName exportExcelByBusinessManageAchievement
     * @author WuShunag
     * @date 23:15
     */
    List<TeacherOrderDTO> exportExcelByBusinessManageAchievement(@Param("date") String date,@Param("endDate") String endDate,@Param("id") String[] userId);
    /**
     *  省份列表
     *
     * @param date
    * @param endDate
    * @param userId
     * @return java.util.List<com.massagecommon.entity.TeacherOrderDTO>
     * @methodName exportExcelByBusinessManageAchievement
     * @author WuShunag
     * @date 23:15
     */
    Page<ProvincialEntity> findProvincial();

    /**
     *  查询价格区间
     *
     * @param date
    * @param endDate
    * @param userId
     * @return java.util.List<com.massagecommon.entity.TeacherOrderDTO>
     * @methodName exportExcelByBusinessManageAchievement
     * @author WuShunag
     * @date 23:15
     */
    Page<RangeDTO> findRangeList(@Param("id") String id);


    void updateRange(@Param("id")String id, @Param("value")String value, @Param("field")String field);


    Page<ExternalUser> findPrivateOrder(@Param("orderName") String orderName, @Param("userId") String userId, @Param("projectName") String projectName, @Param("id") List<String> device_number);


    void updatePrivate(String userId);
}
