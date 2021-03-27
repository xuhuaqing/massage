package com.massageservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.massagecommon.entity.UserEntity;
import com.massagecommon.model.ExternalUser;
import com.massagecommon.model.PlaceOrderModel;

import com.massagecommon.util.ResponseUtil;
import com.massagedao.mapper.ExternalMapper;
import com.massagedao.mapper.UserMapper;
import com.massageservice.service.ExternalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.massagecommon.constant.Constants.SUCCESS_ADMIN_CODE;
import static com.massagecommon.constant.MsgConstant.SUCCESS_MSG;

/**
 * @program: massage
 * @description: 设备下单
 * @author: wushuang
 * @create: 2021-03-25 00:12
 **/
@Service
@Slf4j
public class ExternalServiceImpl implements ExternalService {

    @Autowired
    private ExternalMapper externalMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public String addOrderByPlace(PlaceOrderModel placeOrderModel, Object externalModel) {
        String s = JSON.toJSONString(externalModel);
        ExternalUser externalUser = JSON.parseObject(s, ExternalUser.class);
        Integer i = externalMapper.only(externalUser.getScheme_code());
        if (i > 0) {
            return ResponseUtil.errorMsgToClient("该订单已经下过单了！");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(placeOrderModel.getName());
        userEntity.setUserPhone(placeOrderModel.getPhone());
        placeOrderModel.setUserId(placeOrderModel.getUserId());
        UserEntity userEntity2 = userMapper.isRegister1(userEntity);
        if (userEntity2 == null) {
            userMapper.register3(userEntity);
        }
        externalMapper.addOrderByPlace(placeOrderModel, externalUser);
        return ResponseUtil.successToClient();
    }

    @Override
    public List<String> getEqList(String teacherId) {
        return externalMapper.getEqList(teacherId);
    }

    @Override
    public String getOrderListByTeacherId(PlaceOrderModel placeOrderModel) {
        PageHelper.startPage(placeOrderModel.getPage(), SwingConstants.LEADING);
        Page<Map<String,Object>> externalUsers =  externalMapper.getOrderListByTeacherId(placeOrderModel);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, externalUsers, new HashMap<String,Object>(1) {
            {
                put("count", externalUsers.getTotal());
            }
        });
    }

    @Override
    public String addEquipment(String businessId, String teacherId, String equipmentId) {
        Integer i =  userMapper.teacherIsExist(teacherId);
        if(i <= 0){
            return ResponseUtil.errorMsgToClient("老师id不存在！");
        }
        List<String> eqList = externalMapper.getEqList(teacherId);
        if(eqList.contains(equipmentId)){
            return ResponseUtil.errorMsgToClient("您已经添加过该设备了！");
        }
        externalMapper.addEquipment(businessId,teacherId,equipmentId);
        return ResponseUtil.successToClient();
    }

    @Override
    public String getBusinessEqList(String businessId) {
        List<Map<String,String>> maps = externalMapper.getBusinessEqList(businessId);
        return ResponseUtil.successToClient(maps);
    }

    @Override
    public String getEqListByBusinessId(String businessId) {
        List<Map<String,String>> maps = externalMapper.getBusinessEqList(businessId);
        if(CollectionUtils.isEmpty(maps)){
            return ResponseUtil.successToClient();
        }
        ArrayList<Map<String,Object>> objects = new ArrayList<>();
        maps.forEach(
                s ->{
                    String device_number = s.get("device_number");
                    List<Map<String,String>> list = externalMapper.getEqListBydeviceNumber(device_number);
                    HashMap<String, Object> objectObjectHashMap = new HashMap<>(s);
                    objectObjectHashMap.put("list",list);
                    objects.add(objectObjectHashMap);
                }
        );
            return ResponseUtil.successToClient(objects);
    }
}
