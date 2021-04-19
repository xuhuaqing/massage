package com.massageservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.massagecommon.constant.MsgConstant;
import com.massagecommon.entity.UserEntity;
import com.massagecommon.model.ExternalModel;
import com.massagecommon.model.ExternalUser;
import com.massagecommon.model.PlaceOrderModel;

import com.massagecommon.util.HttpRequestUtil;
import com.massagecommon.util.ResponseUtil;
import com.massagecommon.util.SignUtils;
import com.massagedao.mapper.ExternalMapper;
import com.massagedao.mapper.UserMapper;
import com.massageservice.service.ExternalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.swing.*;
import java.util.*;

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
        UserEntity userEntity2 = userMapper.isRegister5(userEntity);
        if (userEntity2 == null) {
            String uuid = UUID.randomUUID().toString();
            userEntity.setUserId(uuid);
            userMapper.register3(userEntity);
            placeOrderModel.setUserId(uuid);
        } else {
            placeOrderModel.setUserId(userEntity2.getUserId());
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
        Page<Map<String, Object>> externalUsers = externalMapper.getOrderListByTeacherId(placeOrderModel);
        return ResponseUtil.toClient(MsgConstant.MSG_000000 + "", SUCCESS_MSG, externalUsers, new HashMap<String, Object>(1) {
            {
                put("count", externalUsers.getTotal());
            }
        });
    }

    @Override
    public String addEquipment(String businessId, String teacherId, String equipmentId) {
        Integer i = userMapper.teacherIsExist(teacherId);
        if (i <= 0) {
            return ResponseUtil.errorMsgToClient("老师id不存在！");
        }
        List<String> eqList = externalMapper.getEqList(teacherId);
        if (eqList.contains(equipmentId)) {
            return ResponseUtil.errorMsgToClient("您已经添加过该设备了！");
        }
        externalMapper.addEquipment(businessId, teacherId, equipmentId);
        return ResponseUtil.successToClient();
    }

    @Override
    public String getBusinessEqList(String businessId) {
        List<Map<String, String>> maps = externalMapper.getBusinessEqList(businessId);
        return ResponseUtil.successToClient(maps);
    }

    @Override
    public String getEqListByBusinessId(String businessId) {
        List<Map<String, String>> maps = externalMapper.getBusinessEqList(businessId);
        if (CollectionUtils.isEmpty(maps)) {
            return ResponseUtil.successToClient();
        }
        ArrayList<Map<String, Object>> objects = new ArrayList<>();
        maps.forEach(
                s -> {
                    String device_number = s.get("device_number");
                    List<Map<String, String>> list = externalMapper.getEqListBydeviceNumber(device_number);
                    HashMap<String, Object> objectObjectHashMap = new HashMap<>(s);
                    objectObjectHashMap.put("list", list);
                    objects.add(objectObjectHashMap);
                }
        );
        return ResponseUtil.successToClient(objects);
    }

    @Override
    public String getBusinessByUserId(String userName, String userPhone) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setUserPhone(userPhone);
        UserEntity userEntity2 = userMapper.isRegister5(userEntity);
        if (Objects.isNull(userEntity2)) {
            return ResponseUtil.errorMsgToClient("无店铺订单！请仔细核对 手机号 名称!");
        }
        String userId = userEntity2.getUserId();
        Set<String> eqIds = externalMapper.getEqByUserId(userId);
        if(CollectionUtils.isEmpty(eqIds)){
            return ResponseUtil.successToClient();
        }
        List<Map<String, String>> list = externalMapper.findBusinessName(eqIds);
        return ResponseUtil.successToClient(list);
    }

    /**
     * @Description: 根据用户id 获取设备
     * @Param:
     * @return:
     * @Author: wushuang
     * @Date:
     */
    @Override
    public String getEqListByUserId(String userId) {
        Set<String> eqByUserId = externalMapper.getEqByUserId(userId);
        if (CollectionUtils.isEmpty(eqByUserId)) {
            return ResponseUtil.successToClient();
        }
        List<Map<String, String>> list = externalMapper.getEqShowIdByEqId(eqByUserId);
        return ResponseUtil.successToClient(list);
    }


    @Override
    public String getOrderByUserId(String userId, String type, Integer page) {
        //未消耗
        if (Objects.equals(type, "0")) {
            PageHelper.startPage(page,SwingConstants.LEADING);
            Page<Map<String,String>> maps = externalMapper.getNotConsumedOrder(userId);
            return ResponseUtil.toClient(MsgConstant.MSG_000000 + "", SUCCESS_MSG, maps, new HashMap<String, Object>(1) {
                {
                    put("count", maps.getTotal());
                }
            });
        } else
            //已消耗
            if (Objects.equals(type, "1")) {
                PageHelper.startPage(page,SwingConstants.LEADING);
                Page<Map<String,String>> maps = externalMapper.getConsumedOrder(userId);
                return ResponseUtil.toClient(MsgConstant.MSG_000000 + "", SUCCESS_MSG, maps, new HashMap<String, Object>(1) {
                    {
                        put("count", maps.getTotal());
                    }
                });
        }else{
            return ResponseUtil.errorMsgToClient("无效的标识！");
        }
    }

    @Override
    public String woyaomeili(String eqId, String userId, String orderId) {
       Integer status = externalMapper.eqStatus(eqId);
       if(Objects.equals(1,status)){
           return ResponseUtil.errorMsgToClient("设备已锁机！");
       }
        /**
         * 校验订单
         */
       Integer count =  externalMapper.getNotConsumedOrderCount(userId,orderId);
       if(count <= 0){
           return ResponseUtil.errorMsgToClient("您的此订单次数已用尽！");
       }
        String code = externalMapper.findDeviceNumber(orderId);
        long time = System.currentTimeMillis() / 1000;
        String replace = UUID.randomUUID().toString().replace("-", "");
        String replace1 = UUID.randomUUID().toString().replace("-", "");
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("scheme_code", code);
        params.put("state", 1);
        String sign = SignUtils.getSign(params, time, replace, replace1);
        params.put("sign", sign);
        String s = HttpRequestUtil.httpPost("https://api-ck-test.medlander.com/jlapi/external/set-user-train-state", JSON.toJSONString(params));
        ExternalModel externalModel = ExternalModel.parse(s);
        if (Objects.isNull(externalModel)) {
            return ResponseUtil.errorMsgToClient("无数据！");
        }
        if (!ExternalModel.isSucc(externalModel.getCode())) {
            return ResponseUtil.errorMsgToClient("设置失败！");
        }
        externalMapper.updateDidNumber(userId,orderId);
        return ResponseUtil.successToClient();
    }
}
