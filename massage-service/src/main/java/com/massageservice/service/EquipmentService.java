package com.massageservice.service;

public interface EquipmentService {

    /**
     *  设备列表
     *
     * @param businessId
     * @param type
     * @return java.lang.String
     * @methodName equipmentList
     * @author WuShunag
     * @date 16:45
     */
    String equipmentList(String businessId, String type);

    /**
     *  老师添加设备
     *
     * @param businessId
    * @param teacherId
    * @param equipmentId
     * @return java.lang.String
     * @methodName addEquipment
     * @author WuShunag
     * @date 21:00
     */
    String addEquipment(String businessId, String teacherId, String equipmentId);

    String equipmentListByUser(String businessId, String type);
}
