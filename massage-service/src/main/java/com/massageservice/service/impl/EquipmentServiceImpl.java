package com.massageservice.service.impl;

import com.massagecommon.entity.EquipmentEntity;
import com.massagecommon.util.ResponseUtil;
import com.massagedao.mapper.EquipmentMapper;
import com.massageservice.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:WuShuang
 * @date:2020/5/3
 * @ver:1.0
 **/
@Service
public class EquipmentServiceImpl implements EquipmentService
{

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Override
    public String equipmentList(String businessId, String type) {
       List<EquipmentEntity> equipmentEntities =  equipmentMapper.equipmentList(businessId,type);
        return ResponseUtil.successToClient(equipmentEntities);
    }

    @Override
    public String addEquipment(String businessId, String teacherId, String equipmentId) {
        equipmentMapper.addEquipment(businessId,teacherId,equipmentId);
        return ResponseUtil.successToClient();
    }

    @Override
    public String equipmentListByUser(String businessId, String type) {
        List<EquipmentEntity> equipmentEntities =  equipmentMapper.equipmentListByUser(businessId,type);
        return ResponseUtil.successToClient(equipmentEntities);
    }
}
