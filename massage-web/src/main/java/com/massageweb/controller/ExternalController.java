package com.massageweb.controller;

import com.alibaba.fastjson.JSON;
import com.massagecommon.model.ExternalModel;
import com.massagecommon.model.PlaceOrderModel;
import com.massagecommon.model.UserModel;
import com.massagecommon.util.HttpRequestUtil;
import com.massagecommon.util.ResponseUtil;
import com.massagecommon.util.SignUtils;
import com.massageservice.service.ExternalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

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
        if (StringUtils.isAllBlank(userModel.getUserName(), userModel.getUserPhone())) {
            return ResponseUtil.errorMsgToClient("至少传入一个搜索条件！");
        }
        long time = System.currentTimeMillis() / 1000;
        String replace = UUID.randomUUID().toString().replace("-", "");
        String replace1 = UUID.randomUUID().toString().replace("-", "");
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("name", Objects.isNull(userModel.getUserName())?"":userModel.getUserName());
        params.put("phone", Objects.isNull(userModel.getUserPhone())?"":userModel.getUserPhone());
        params.put("state", "1");
        return getString(time, replace, replace1, params, testUrl+"/jlapi/external/get-user-scheme");
    }

    /** 
    * @Description: 获取订单详情
    * @Param:  
    * @return:  
    * @Author: wushuang
    * @Date:  
    */
    @GetMapping("getOrderDetails/{id}")
    public String getOrderDetails(@PathVariable String id){
        log.info("入参id：{}",id);
        if (StringUtils.isAllBlank(id)) {
            return ResponseUtil.errorMsgToClient("id不可为空！");
        }
        long time = System.currentTimeMillis() / 1000;
        String replace = UUID.randomUUID().toString().replace("-", "");
        String replace1 = UUID.randomUUID().toString().replace("-", "");
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("scheme_code",id);
        return getString(time, replace, replace1, params, testUrl+"/jlapi/external/user-scheme-details");
    }

    @GetMapping("cancelOrder/{id}")
    public String cancelOrder(@PathVariable String id){
        log.info("入参id：{}",id);
        if (StringUtils.isAllBlank(id)) {
            return ResponseUtil.errorMsgToClient("id不可为空！");
        }
        return orderStatus(id,"3");
    }

    private String orderStatus(String id,String state){
        long time = System.currentTimeMillis() / 1000;
        String replace = UUID.randomUUID().toString().replace("-", "");
        String replace1 = UUID.randomUUID().toString().replace("-", "");
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("scheme_code",id);
        params.put("state",state);
        return getString(time, replace, replace1, params, testUrl+" /jlapi/external/set-user-scheme-state");
    }


    /**
    * @Description:  确认下单
    * @Param:
    * @return:
    * @Author: wushuang
    * @Date:
    */
    @PostMapping("placeOrder")
    public String placeOrder(@RequestBody PlaceOrderModel placeOrderModel){
        String s = orderStatus(placeOrderModel.getSchemeId(), "1");
        ExternalModel parse = ExternalModel.parse(s);
        if (Objects.isNull(parse)) {
            return ResponseUtil.successToClient();
        }
        if (!ExternalModel.isSucc(parse.getCode())) {
            return ResponseUtil.errorMsgToClient(parse.getNote());
        }
        /**
         * 重新获取一遍订单信息
         */
        String orderDetails = getOrderDetails(placeOrderModel.getSchemeId());
        ExternalModel externalModel = ExternalModel.parse(orderDetails);
        if (Objects.isNull(externalModel)) {
            return ResponseUtil.successToClient();
        }
        if (!ExternalModel.isSucc(externalModel.getCode())) {
            return ResponseUtil.errorMsgToClient(externalModel.getNote());
        }
        return externalService.addOrderByPlace(placeOrderModel,externalModel);
    }

    private String getString(long time, String replace, String replace1, TreeMap<String, Object> params, String s2) {
        String sign = SignUtils.getSign(params, time, replace, replace1);
        params.put("sign", sign);
        String s = HttpRequestUtil.httpPost(s2, JSON.toJSONString(params));
        log.info("返回的数据--{}", s);
        ExternalModel externalModel = ExternalModel.parse(s);
        if (Objects.isNull(externalModel)) {
            return ResponseUtil.successToClient();
        }
        if (!ExternalModel.isSucc(externalModel.getCode())) {
            return ResponseUtil.errorMsgToClient(externalModel.getNote());
        }
        return ResponseUtil.successToClient(externalModel.getData());
    }


}
