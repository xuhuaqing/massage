package com.massageservice.service;

import com.massagecommon.entity.AdminCommon;
import com.massagecommon.entity.BusinessDTO;
import com.massagecommon.entity.EquipmentEntity;
import com.massagecommon.entity.ProjectEntity;

/**
 * @author:WuShuang
 * @date:2020/5/4
 * @ver:1.0
 **/
public interface AdminUserService {
    String adminLogin(AdminCommon adminCommon);

    String changePassword(String oldPassword, String password, String rePassword);

    String findBusinessManage(Integer page, Integer pageSize, Integer type, String agentId, String teacherType, String userId, String isPrivate);

    String deleteBusiness(String userId);

    String businessAdd(BusinessDTO businessDTO);

    String updateBusiness(BusinessDTO businessDTO);

    String findOneClassification(Integer page, Integer pageSize, String projectId);

    String updOneClassification(String projectId, String projectImg);

    String showClassification(String projectId);

    String updateClassificationProject(ProjectEntity.ProjectFour projectFour);

    String findEquipment(String equipmentId, Integer page, Integer pageSize, String userName);

    String upEquipment(String equipmentId, String status);

    String addEquipment(EquipmentEntity.EquipmentDTO equipmentDTO);

    String businessList();

    String updEquipment(EquipmentEntity.EquipmentDTO equipmentDTO);

    String findOrder(String orderName, Integer page, Integer pageSize, String userId, String projectName, Integer isPrivate);

    String exportExcelByUser(String[] id);

    String updateOrder(String id, String totalTimes, String field, String userName);


    String settleBusiness(String userId);

    String exportExcelByTeacherManage(String date, String[] userId);

    String exportExcelByBusinessManage(String date, String[] userId);

    String deleteOrder(String id);

    String exportExcelByBusinessManageAchievement(String date, String[] userId);

    String findProvincial(Integer page, Integer pageSize);

    String findRangeList(Integer page, Integer pageSize, String id);

    String updateRange(String id, String value, String field);

    String updatePrivate(String userId);

    String findPrivateEquipment(Integer page);

    String startEq(String device_number, String status);

    String privateAddEquipment(String equipmentId, String userName);
}
