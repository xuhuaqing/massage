package com.massageweb.controller;

import com.alibaba.fastjson.JSON;
import com.massagecommon.constant.MsgConstant;
import com.massagecommon.entity.EquipmentEntity;
import com.massagecommon.entity.UserEntity;
import com.massagecommon.model.ExternalModel;
import com.massagecommon.model.ExternalUser;
import com.massagecommon.model.PlaceOrderModel;
import com.massagecommon.model.UserModel;
import com.massagecommon.util.HttpRequestUtil;
import com.massagecommon.util.ResponseUtil;
import com.massagecommon.util.SignUtils;
import com.massageservice.service.ExternalService;
import com.massageservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @program: massage
 * @description: 私密护理
 * @author: wushuang
 * @create: 2021-03-24 21:19
 **/
@RestController
@RequestMapping("/yihaomi/external/")
@Slf4j
public class ExternalController {

    private final static String testUrl = "https://api-ck-test.medlander.com";


    @Autowired
    private UserService userService;
    @Autowired
    private ExternalService externalService;

    /**
     * @Description: 老师获取设备下的订单
     * @Param:
     * @return:
     * @Author: wushuang
     * @Date:
     */
    @PostMapping("getTeacherOrderByUser")
    public String getTeacherOrderByUser(@RequestBody UserModel userModel) {
        if (StringUtils.isAllBlank(userModel.getUserName())) {
            return ResponseUtil.errorMsgToClient("至少传入一个搜索条件！");
        }
        List<String> strings = externalService.getEqList(userModel.getTeacherId());
        if (CollectionUtils.isEmpty(strings)) {
            return ResponseUtil.errorMsgToClient("请添加设备！");
        }
        long time = System.currentTimeMillis() / 1000;
        String replace = UUID.randomUUID().toString().replace("-", "");
        String replace1 = UUID.randomUUID().toString().replace("-", "");
        TreeMap<String, Object> params = new TreeMap<>();

        params.put("name", validateMobilePhone(userModel.getUserName()) ? "" : userModel.getUserName());
        params.put("phone", validateMobilePhone(userModel.getUserName()) ? userModel.getUserName() : "");
        params.put("state", "0");
        String string = getString(time, replace, replace1, params, testUrl + "/jlapi/external/get-user-scheme");
        log.info("string返回的数据--{}", string);
        Map map = JSON.parseObject(string, Map.class);
        if (Objects.isNull(map)) {
            return ResponseUtil.successToClient();
        }
        String code = (String) map.get("code");
        if (!Objects.equals(code, MsgConstant.MSG_000000)) {
            return string;
        }
        Object data = map.get("data");
        String s = JSON.toJSONString(data);
        List<ExternalUser> externalUsers = JSON.parseArray(s, ExternalUser.class);
        ArrayList<ExternalUser> objects = new ArrayList<>();
        for (ExternalUser externalUser : externalUsers) {
            if (strings.contains(externalUser.getDevice_number())) {
                objects.add(externalUser);
            }
        }
        return ResponseUtil.successToClient(objects);
    }

    public static boolean validateMobilePhone(String in) {
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        return pattern.matcher(in).matches();
    }
    /**
     * @Description: 获取订单详情
     * @Param:
     * @return:
     * @Author: wushuang
     * @Date:
     */
    @GetMapping("getOrderDetails/{id}")
    public String getOrderDetails(@PathVariable String id) {
        log.info("入参id：{}", id);
        if (StringUtils.isAllBlank(id)) {
            return ResponseUtil.errorMsgToClient("id不可为空！");
        }
        long time = System.currentTimeMillis() / 1000;
        String replace = UUID.randomUUID().toString().replace("-", "");
        String replace1 = UUID.randomUUID().toString().replace("-", "");
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("scheme_code", id);
        return getString(time, replace, replace1, params, testUrl + "/jlapi/external/user-scheme-details");
    }

    @GetMapping("cancelOrder/{id}")
    public String cancelOrder(@PathVariable String id) {
        log.info("入参id：{}", id);
        if (StringUtils.isAllBlank(id)) {
            return ResponseUtil.errorMsgToClient("id不可为空！");
        }
        return orderStatus(id, "3");
    }

    private String orderStatus(String id, String state) {
        long time = System.currentTimeMillis() / 1000;
        String replace = UUID.randomUUID().toString().replace("-", "");
        String replace1 = UUID.randomUUID().toString().replace("-", "");
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("scheme_code", id);
        params.put("state", state);
        return getString(time, replace, replace1, params, testUrl + "/jlapi/external/set-user-scheme-state");
    }


    /**
     * @Description: 确认下单
     * @Param:
     * @return:
     * @Author: wushuang
     * @Date:
     */
    @PostMapping("placeOrder")
    public String placeOrder(@RequestBody PlaceOrderModel placeOrderModel) {
        String s = orderStatus(placeOrderModel.getSchemeId(), "1");
        Map map = JSON.parseObject(s, Map.class);
        if (Objects.isNull(map)) {
            return ResponseUtil.successToClient();
        }
        String code = (String) map.get("code");
        if (!Objects.equals(code, MsgConstant.MSG_000000)) {
            return s;
        }
        /**
         * 重新获取一遍订单信息
         */
        long time = System.currentTimeMillis() / 1000;
        String replace = UUID.randomUUID().toString().replace("-", "");
        String replace1 = UUID.randomUUID().toString().replace("-", "");
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("scheme_code", placeOrderModel.getSchemeId());
        String orderDetails = getString(time, replace, replace1, params, testUrl + "/jlapi/external/user-scheme-details");
        Map map2 = JSON.parseObject(orderDetails, Map.class);
        if (Objects.isNull(map2)) {
            return ResponseUtil.successToClient();
        }
        String code2 = (String) map2.get("code");
        if (!Objects.equals(code2, MsgConstant.MSG_000000)) {
            return orderDetails;
        }
        return externalService.addOrderByPlace(placeOrderModel, map2.get("data"));
    }

    private String getString(long time, String replace, String replace1, TreeMap<String, Object> params, String s2) {
        String sign = SignUtils.getSign(params, time, replace, replace1);
        params.put("sign", sign);
        String s = HttpRequestUtil.httpPost(s2, JSON.toJSONString(params));
        log.info("返回的数据--{}", s);
        ExternalModel externalModel = ExternalModel.parse(s);
        if (Objects.isNull(externalModel)) {
            return ResponseUtil.errorMsgToClient("无数据！");
        }
        if (!ExternalModel.isSucc(externalModel.getCode())) {
            return ResponseUtil.errorMsgToClient(externalModel.getNote());
        }
        return ResponseUtil.successToClient(externalModel.getData());
    }

    /**
     * @Description: 获取已下单的列表
     * @Param:
     * @return:
     * @Author: wushuang
     * @Date:
     */
    @PostMapping("getOrderListByTeacherId")
    public String getOrderListByTeacherId(@RequestBody PlaceOrderModel placeOrderModel){
        if(StringUtils.isBlank(placeOrderModel.getTeacherId())){
            return ResponseUtil.errorMsgToClient("id不可为空！");
        }
        return externalService.getOrderListByTeacherId(placeOrderModel);
    }

    
    /** 
    * @Description: 老师添加设备
    * @Param:  
    * @return:  
    * @Author: wushuang
    * @Date:  
    */
    @PostMapping("addTeacherEq")
    public String addTeacherEq(@RequestBody  Map<String ,String> map){
        String businessId = map.get("businessId");
        String teacherId =  map.get("teacherId");
        String equipmentId =  map.get("equipmentId");
        if(StringUtils.isAllBlank(businessId,teacherId,equipmentId)){
            return ResponseUtil.errorMsgToClient("参数不全！");
        }
        return externalService.addEquipment(businessId,teacherId,equipmentId);
    }


    /**
    * @Description: 获取商家新设备
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    @GetMapping("getBusinessEqList/{businessId}")
    public String getBusinessEqList(@PathVariable String businessId){
        return externalService.getBusinessEqList(businessId);
    }
    /** 
    * @Description:  商家登陆 获取设备信息 与 老师绑定信息 
    * @Param:  
    * @return:  
    * @Author: wushuang
    * @Date:  
    */
    @GetMapping("getEqListByBusinessId/{businessId}")
    public String getEqListByBusinessId(@PathVariable String businessId){
        return externalService.getEqListByBusinessId(businessId);
    }


    /** 
    * @Description: 根据账户名 手机号 获取下单电偶 
    * @Param:  
    * @return:  
    * @Author: wushuang
    * @Date:  
    */
    @GetMapping("getBusinessByUserId/{userName}/{userPhone}")
    public String getBusinessByUserId(@PathVariable String userName,@PathVariable String userPhone){
        return externalService.getBusinessByUserId(userName,userPhone);
    }

    /**
     * @Description:  根据用户id 获取设备
     * @Param:
     * @return:
     * @Author: wushuang
     * @Date:
     */
    @GetMapping("getEqListByUserId/{userId}")
    public String getEqListByUserId(@PathVariable String userId){
        return externalService.getEqListByUserId(userId);
    }


    /**
    * @Description:  根据用户id 获取设备  type 0 未消耗 1已消耗
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    @GetMapping("getOrderByUserId/{userId}/{type}/{page}")
    public String getOrderByUserId(@PathVariable String userId,@PathVariable String type,@PathVariable Integer page){
        return externalService.getOrderByUserId(userId,type,page);
    }


    /**
    * @Description:  顾客点击我要美丽按钮
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    @GetMapping("woyaomeili/{eqId}/{userId}/{orderId}")
    public String woyaomeili(@PathVariable String eqId,@PathVariable String userId,@PathVariable String orderId){
        log.info(eqId);
        long time = System.currentTimeMillis() / 1000;
        String replace = UUID.randomUUID().toString().replace("-", "");
        String replace1 = UUID.randomUUID().toString().replace("-", "");
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("device_number", eqId);
        String orderDetails = getString(time, replace, replace1, params, testUrl + "/jlapi/external/get-device-state");
        Map map2 = JSON.parseObject(orderDetails, Map.class);
        if (Objects.isNull(map2)) {
            return ResponseUtil.errorMsgToClient("设备出现问题！");
        }
        String code2 = (String) map2.get("code");
        if (!Objects.equals(code2, MsgConstant.MSG_000000)) {
            return orderDetails;
        }
        Object data = map2.get("data");
        String s = JSON.toJSONString(data);
        log.info("1:"+s);
        Map map = JSON.parseObject(s, Map.class);
        Object data1 = map.get("device_state");
        if(Objects.equals(1,Integer.parseInt(data1.toString()))){
            return ResponseUtil.errorMsgToClient("设备工作中");
        }
        return externalService.woyaomeili(eqId,userId,orderId);
    }

}
