package com.massageweb.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.massagecommon.entity.*;
import com.massagecommon.util.ResponseUtil;
import com.massagedao.mapper.AdminUserMapper;
import com.massagedao.mapper.EquipmentMapper;
import com.massageservice.service.AdminUserService;
import com.massageservice.service.EquipmentService;
import com.massageservice.service.UserService;
import com.massageweb.common.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.massagecommon.constant.MsgConstant.MSG_001008;

/**
 * @author:WuShuang
 * @date:2020/5/3
 * @ver:1.0
 **/
@RestController
@RequestMapping("/api/user/")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private AdminUserMapper adminUserMapper;



    public static final String WXURL = "https://api.weixin.qq.com/sns/oauth2/access_token?";

    private final static CloseableHttpClient HTTP_CLIENT = HttpClientBuilder.create().build();


    public static String get(String url) {
        StringBuffer sb = new StringBuffer();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = HTTP_CLIENT.execute(httpGet);

            HttpEntity entity = response.getEntity();
            InputStreamReader reader = new InputStreamReader(entity.getContent(), "utf-8");
            char[] charbufer;
            while (0 < reader.read(charbufer = new char[10])) {
                sb.append(charbufer);
            }
        } catch (IOException e) {//1
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }
        return sb.toString();
    }



    @PostMapping("getOpenId")
    public String getOpenId(String code) throws Exception {




        String format = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code","wxe79618b5a057822c", "99b44b7ca0ceb82b07fe78e09c4b4ca4", code);

        String responseData = get(format);
        log.info("request weChat openId api , response data:[{}]", responseData);
        cn.hutool.json.JSONObject responseJson = new cn.hutool.json.JSONObject(responseData);
        if (responseJson.containsKey("errcode")) {
            return ResponseUtil.errorMsgToClient(responseJson.getByPath("errmsg").toString());
        }
        String openId = responseJson.getByPath("openid").toString();



        return ResponseUtil.successToClient(openId);






     /*   String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ AuthUtil.APP_ID
                + "&secret="+AuthUtil.APP_SECRET
                + "&code="+code
                + "&grant_type=authorization_code";
        try {
            cn.hutool.json.JSON json = AuthUtil.doGetJson(url);
            String openid = StrUtil.toString(json.getByPath("openid"));

            System.err.println("用户的openId="+openid);
            String token = StrUtil.toString(json.getByPath("access_token"));
            *//**
             * 拉取用户信息
             *//*
            String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token
                    + "&openid=" + openid
                    + "&lang=zh_CN";
            cn.hutool.json.JSON userInfo = AuthUtil.doGetJson(infoUrl);//这里的userInfo已经是用户的信息了
            System.out.println(userInfo);
            return ResponseUtil.successToClient(userInfo);
        }catch (Exception e){
            e.getMessage();
        }*/
    }





    /**
     *  用户登陆
     *
     *  用户登陆  需要 userName  userPhone type（0是用户 1是商家 2是老师） businessId
     *
     *  商家登陆 需要 businessId  password type（0是用户 1是商家 2是老师）
     *
     *  老师登陆 需要 userPhone password type（0是用户 1是商家 2是老师）
     *
     * @param
     * @return java.lang.String
     * @methodName login
     * @author WuShunag
     * @date 14:46
     */
    @PostMapping("login")
    public String login(UserEntity userEntity){
        JSONObject jsonObject = new JSONObject();
        /**
         * 用户登陆
         */
        if(userEntity.getType() == 0){
            UserEntity i = userService.isRegister(userEntity);
            if(i == null){
                return ResponseUtil.errorMsgToClient("登陆失败！请检查账号密码！");
            }
            i.setType(userEntity.getType());
            jsonObject.put("userInfo",i);
        }else if(userEntity.getType() == 1 ){
            UserEntity userEntity1 = userService.loginBusiness(userEntity);
            if(userEntity1 == null){
                return ResponseUtil.errorMsgToClient("密码不对！");
            }
            userEntity1.setType(userEntity.getType());
            List<EquipmentEntity> equipmentEntities = equipmentMapper.equipmentList(userEntity1.getBusinessId(), 1 + "");
            userEntity1.setEquipmentId(equipmentEntities.isEmpty()?"":equipmentEntities.get(0).getEquipmentId());
            jsonObject.put("userInfo",userEntity1);
        } else if(userEntity.getType() == 2){
            UserEntity userEntity1 = userService.loginTeacher(userEntity);
            if(userEntity1 == null){
                return ResponseUtil.errorMsgToClient(MSG_001008,"密码不对！");
            }
            if(userEntity1.getTeacherType() == 1){
                String provincialId = userEntity1.getProvincialId();
                Page<BusinessDTO> businessManage =
                        adminUserMapper.findBusinessManage(1, provincialId, null, null);
                List<String> collect = businessManage.getResult().stream().map(
                        BusinessDTO::getUserId
                ).collect(Collectors.toList());
                if(!collect.contains(userEntity.getBusinessId())){
                    return ResponseUtil.errorMsgToClient(MSG_001008,"请选择本省份的店铺！");
                }
            }
            userEntity1.setBusinessId(userEntity.getBusinessId());
            userEntity1.setEquipmentId(userEntity.getEquipmentId());
            userEntity1.setType(userEntity.getType());
            userEntity1.setUserPhone(userEntity.getUserPhone());
            jsonObject.put("userInfo",userEntity1);
        }else if(userEntity.getType() == 3){
            UserEntity userEntity1 = userService.loginTeacher(userEntity);
            if(userEntity1 == null){
                return ResponseUtil.errorMsgToClient(MSG_001008,"密码不对！");
            }
            userEntity1.setBusinessId(userEntity.getBusinessId());
            userEntity1.setEquipmentId(userEntity.getEquipmentId());
            userEntity1.setType(userEntity.getType());
            userEntity1.setUserPhone(userEntity.getUserPhone());
            jsonObject.put("userInfo",userEntity1);
        }
        return ResponseUtil.successToClient(jsonObject);
    }


    /**
     *  用户订单   需要 userName  userPhone businessId
     *
     * @param
     * @return java.lang.String
     * @methodName userOrder
     * @author WuShunag
     * @date 15:50
     */
    @PostMapping("userOrder")
    public String userOrder(UserEntity userEntity){
        JSONObject jsonObject = new JSONObject();

        /**
         * 已消耗订单
         */
        List<UserOrderEntity> userOrderEntity = userService.findOrder(userEntity);
        /**
         * 未消耗订单
         */
        List<UserOrderEntity> userOrderEntity1 = userService.userOrder(userEntity);
        jsonObject.put("consumed",userOrderEntity);
        jsonObject.put("notConsumed",userOrderEntity1);
        return ResponseUtil.successToClient(jsonObject);
    }


    /**
     *  创建订单  需要
     *
     *  orderName （项目名称）
     *  orderType  （0是固定模式 1是自定义模式）
     *  price  （价钱）
     *  totalTimes（总次数）
     *  businessId （商家id ）
     *  userName(用户名称)
     *  userPhone（用户手机号）
     *  everyTime （每次时长）
     *  projectTime （总时长）
     *  equipmentId （设备id）
     *
     *  teacherId(老师id  当自定义模式下 传入)
     *
     * @param orderEntity
     * @return java.lang.String
     * @methodName submitOrder
     * @author WuShunag
     * @date 20:40
     */
    @PostMapping("submitOrder")
    public String submitOrder(OrderEntity orderEntity){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(orderEntity.getUserName());
        userEntity.setUserPhone(orderEntity.getUserPhone()+"");
        userService.isRegister2(userEntity);
        return userService.submitOrder(orderEntity);
    }


    /**
     *  发送请求指令
     *  numberId 订单的唯一id ， 用逗号拼接
     *  orderId  向设备发送的id  随便取一个就行
     *  everyTime 时长    多个订单加起来的时长
     *  equipmentId 设备id
     *
     *
     * @param
     * @return java.lang.String
     * @methodName sendOrder
     * @author WuShunag
     * @date 15:55
     */
    @PostMapping("sendOrder")
    public String sendOrder(String numberId,String orderId,String everyTime,String equipmentId){


       return  userService.sendOrder(numberId,orderId,everyTime,equipmentId);
    }


    /**
     *  商家发起请求
     *
     * @param
     * @return java.lang.String
     * @methodName businesSendOrder
     * @author WuShunag
     * @date 1:43
     */
    @PostMapping("businessSendOrder")
    public String businessSendOrder(String equipmentId){
        return  userService.businessSendOrder(equipmentId);
    }



    /**
     *  0是 到店
     *  1是 离店
     *
     * @param
     * @return java.lang.String
     * @methodName teacherToShop
     * @author WuShunag
     * @date 0:32
     */
    @PostMapping("teacherToShop")
    public String teacherToShop(String type,String equipmentId){
      return   userService.teacherToShop(type,equipmentId);
    }


    /**
     *  客户登陆时 选择店铺  userName  userPhone
     *  商户选择店铺  password
     *  0是客户  1是商户
     *
     * @param userEntity
     * @return java.lang.String
     * @methodName orderUser
     * @author WuShunag
     * @date 15:59
     */
    @PostMapping("orderUser")
    public String orderUser(UserEntity userEntity){
        return userService.orderUser(userEntity);
    }



    @RequestMapping("test")
    public void test(){
        for (int i = 0; i < 30; i++) {
            Random random = new Random();
            int x = random.nextInt(100) + 22;
            equipmentMapper.addOrderRecord(String.valueOf(x),"");
        }
    }
}
