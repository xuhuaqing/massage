package com.massageweb.controller;

import com.massageservice.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author:WuShuang
 * @date:2020/5/3
 * @ver:1.0
 **/
@RestController
@RequestMapping("/api/equipment/")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    /**
     *  设备列表
     *      type    (0的时候 是老师添加设备的
     *              1的时候 是商户选着设备的时候)
     *
     *      businessId 商家id
     *
     * @param businessId
    * @param type
     * @return java.lang.String
     * @methodName equipmentList
     * @author WuShunag
     * @date 17:14
     */
    @GetMapping("equipmentList")
    public String equipmentList(@RequestParam("businessId") String businessId,@RequestParam("type") String type){
        return equipmentService.equipmentList(businessId,type);
    }

    @GetMapping("equipmentListByUser")
    public String equipmentListByUser(@RequestParam("businessId") String businessId,@RequestParam("type") String type){
        return equipmentService.equipmentListByUser(businessId,type);
    }

    /**
     *  添加设备
     *
     *  businessId 商家id
     *
     *  teacherId 老师id
     *
     *  equipmentId 设备id
     *
     * @param
     * @return java.lang.String
     * @methodName addEquipment
     * @author WuShunag
     * @date 20:57
     */
    @PostMapping("addEquipment")
    public String addEquipment(String businessId,String teacherId,String equipmentId){
        return equipmentService.addEquipment(businessId,teacherId,equipmentId);
    }
}
