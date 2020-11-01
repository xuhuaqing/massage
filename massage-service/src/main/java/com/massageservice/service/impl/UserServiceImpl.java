package com.massageservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.massagecommon.entity.*;
import com.massagecommon.util.GenerateRandomCode;
import com.massagecommon.util.RedisUtil;
import com.massagecommon.util.ResponseUtil;
import com.massagedao.mapper.EquipmentMapper;
import com.massagedao.mapper.UserMapper;
import com.massageservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.massagecommon.constant.MsgConstant.MSG_000004;

/**
 * @author:WuShuang
 * @date:2020/5/3
 * @ver:1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserEntity isRegister(UserEntity userEntity) {
      UserEntity userEntity1 =  userMapper.isRegister1(userEntity);
      if(userEntity1 == null){
//          UserEntity userEntity2 =  userMapper.isRegister1(userEntity);
//            if(userEntity2 == null){
//                userMapper.register(userEntity);
//                UserEntity register = userMapper.isRegister(userEntity);
//                register.setBusinessId(userEntity.getBusinessId());
//                return register;
//            }else {
//                    UserEntity register = userMapper.isRegister(userEntity);
//                    register.setBusinessId(userEntity.getBusinessId());
//                    return register;
//            }
            return null;
      }else {
          String userPhone = userEntity1.getUserPhone();
          if(userEntity1.getUserName().equals(userEntity.getUserName()) && userPhone.substring(userPhone.length() - 4).equals(userEntity.getUserPhone().substring(userEntity.getUserPhone().length() -4 ))){
              userEntity1.setBusinessId(userEntity.getBusinessId());
              return  userEntity1;
          }

          return null;

      }
    }

    /**
     *  查询用户订单
     *
     * @param
     * @return java.util.List<com.massagecommon.entity.UserOrderEntity>
     * @methodName findOrder
     * @author WuShunag
     * @date 15:31
     */
    @Override
    public List<UserOrderEntity> findOrder(UserEntity userEntity) {

        return userMapper.findOrder(userEntity);
    }

    @Override
    public List<UserOrderEntity> userOrder(UserEntity userEntity) {
        return userMapper.userOrder(userEntity);
    }

    @Override
    public UserEntity loginBusiness(UserEntity userEntity) {

        return userMapper.loginBusiness(userEntity);
    }

    @Override
    public String submitOrder(OrderEntity orderEntity) {
        /**
         * 判断该设备下架了吗
         */
        if(orderEntity.getOrderType().equals("1")){
            Integer integer = equipmentMapper.isdelete(orderEntity.getEquipmentId());
            if(integer == 1){
                return ResponseUtil.toClient(MSG_000004,"设备停用！");
            }
            Integer integer1 =  equipmentMapper.isAddEquipment(orderEntity.getEquipmentId());
            if(integer1 == 1){
                return ResponseUtil.toClient(MSG_000004,"老师没有添加该设备！");
            }
        }
        String s = GenerateRandomCode.generNumCode(7);
        orderEntity.setId(s);
        /**
         * 体验订单
         */
        if(orderEntity.getExperience() == 1){
            if(orderEntity.getOrderType() == 1 && orderEntity.getGive().equals("1")){
                return ResponseUtil.errorMsgToClient("体验订单不可以勾选赠送订单！");
            }
            orderEntity.setOrderName("体验订单");
            orderEntity.setPrice(0);
            orderEntity.setTotalTimes(1);
            orderEntity.setProjectTime(60);
            orderEntity.setEveryTime(60);
            userMapper.submitOrder(orderEntity);
            return ResponseUtil.successToClient(orderEntity);
        }
        userMapper.submitOrder(orderEntity);
        /**
         * 这是赠送订单
         */
        if(orderEntity.getOrderType() == 1 && orderEntity.getGive().equals("1")){
            s = GenerateRandomCode.generNumCode(7);
            //判断老师身份
            if(orderEntity.getTeacherType() == 1){
                if(orderEntity.getGiveTotalTimes() > 5){
                    return ResponseUtil.errorMsgToClient("赠送订单次数不可大于5次！");
                }
                userMapper.giveOrder(orderEntity,s);
            }
            userMapper.giveOrder(orderEntity,s);
        }
        return ResponseUtil.successToClient(orderEntity);
    }


    /**
     *  老师登陆
     *
     * @param userEntity
     * @return com.massagecommon.entity.UserEntity
     * @methodName loginTeacher
     * @author WuShunag
     * @date 20:52
     */
    @Override
    public UserEntity loginTeacher(UserEntity userEntity) {
        return  userMapper.loginTeacher(userEntity);
    }


    private static ExecutorService executorService = new ThreadPoolExecutor(20, 30,
            60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(512), // 使用有界队列，避免OOM
            new ThreadPoolExecutor.DiscardPolicy());

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized String sendOrder(String numberId, String orderId, String everyTime, String equipmentId) {
        /**
         * 判断该设备下架了吗
         */
        Integer integer = equipmentMapper.isdelete(equipmentId);
        if(integer == 1){
            return ResponseUtil.toClient(MSG_000004,"设备停用！");
        }
        Integer integer1 =  equipmentMapper.isAddEquipment(equipmentId);
        if(integer1 == 1){
            return ResponseUtil.toClient(MSG_000004,"老师没有添加该设备！");
        }
               String value = "{ID="+equipmentId+",QSN=nnnnnnnnxhq,VTA="+everyTime+",VTB="+orderId+",VTC=0}";
               Object o = redisUtil.get(equipmentId);
               if(o == null){
                   boolean set = redisUtil.set(equipmentId, value);
                   if(set){
                       String[] split = numberId.split(",");
                       String sq = GenerateRandomCode.generNumCode(7);
                       for (String s : split) {
                           Integer i = userMapper.selectVersion(s);
                           Integer q = userMapper.selectTotal(s);
                           if(i+1<=q){
                               userMapper.sendOrder(s,sq);
                           }
                       }

                       executorService.execute(
                               () ->{
                                   String[] split1 = numberId.split(",");
                                   for (String s : split1) {
                                       String id = equipmentMapper.selectParentId(s);
                                       if(id.equals("0")){
                                           equipmentMapper.addOrderRecord(s,equipmentId);
                                       }else {
                                           equipmentMapper.addOrderRecord(id,equipmentId);
                                       }
                                   }
                               }
                       );
                       // userMapper.sendOrder(numberId,sq);
                       return ResponseUtil.successToClient();
                   }

               }else {
                   //设备时长id
                   String string = o.toString();
                   String timeId =  userMapper.findOrderByBusiness(equipmentId);
                   if(string.indexOf(timeId)>0 && string.indexOf(equipmentId)>0){
                       //这里 说明商家 在redis里面存着 （在使用） (明文)
                       String  o1 = redisUtil.get("returnMsg" + equipmentId).toString();
                       String s = regComp(o1);
                       System.err.println("设备id="+equipmentId+",时间="+s);

                       userMapper.updateTime(equipmentId,s);
                       //存的也是明文
                       boolean set = redisUtil.set(equipmentId, value);
                       if(set){
                           // userMapper.sendOrder(numberId,sq);
                           String[] split = numberId.split(",");
                           String sq = GenerateRandomCode.generNumCode(7);
                           for (String s1 : split) {
                               Integer i = userMapper.selectVersion(s1);
                               Integer q = userMapper.selectTotal(s1);
                               if(i+1<=q){
                                   userMapper.sendOrder(s1,sq);
                               }
                           }

                           executorService.execute(
                                   () ->{
                                       String[] split1 = numberId.split(",");
                                       for (String s2 : split1) {
                                           String id = equipmentMapper.selectParentId(s2);
                                           if(id.equals("0")){
                                               equipmentMapper.addOrderRecord(s2,equipmentId);
                                           }else {
                                               equipmentMapper.addOrderRecord(id,equipmentId);
                                           }
                                       }
                                   }
                           );
                           return ResponseUtil.successToClient();
                       }
                   }
                   Long aLong = redisUtil.listSize("lineUp" + equipmentId);
                   //redisUtil.lPush("lineUp"+equipmentId,value);
                   return ResponseUtil.errorMsgToClient("您前面还有"+aLong+1+"人在使用仪器 ，请等待！");
               }

     /*      return ResponseUtil.successToClient();*/
        return null;
    }

    @Override
    public String businessSendOrder(String equipmentId) {

        /**
         * 判断该设备下架了吗
         */
        Integer integer = equipmentMapper.isdelete(equipmentId);
        if(integer == 1){
            return ResponseUtil.toClient(MSG_000004,"设备停用！");
        }
        Integer integer1 =  equipmentMapper.isAddEquipment(equipmentId);
        if(integer1 == 1){
            return ResponseUtil.toClient(MSG_000004,"老师没有添加该设备！");
        }

            EquipmentEntity.EquipmentDTO equipmentDTO  =  userMapper.businessSendOrder(equipmentId);
            String value = "{ID="+equipmentId+",QSN=nnnnnnnnxhq,VTA="+equipmentDTO.getStartTime()+",VTB="+equipmentDTO.getNumber()+",VTC=0}";
            redisUtil.set(equipmentId,value);
            return ResponseUtil.successToClient();

    }

    @Override
    public String teacherToShop(String type, String equipmentId) {


        /**
         * 判断该设备下架了吗
         */
        Integer integer = equipmentMapper.isdelete(equipmentId);
        if(integer == 1){
            return ResponseUtil.toClient(MSG_000004,"设备停用！");
        }
        Integer integer1 =  equipmentMapper.isAddEquipment(equipmentId);
        if(integer1 == 1){
            return ResponseUtil.toClient(MSG_000004,"老师没有添加该设备！");
        }

        EquipmentEntity.EquipmentDTO equipmentDTO  =  userMapper.businessSendOrder(equipmentId);
        Integer o = type.equals("0")? 200:0;
        String value = "{ID="+equipmentId+",QSN=nnnnnnnnxhq,VTA="+o+",VTB="+equipmentDTO.getNumber()+",VTC=0}";
        redisUtil.set(equipmentId,value);

        //老师到店
        equipmentMapper.teacherToShop(o,equipmentId);
        return ResponseUtil.successToClient();
    }

    @Override
    public String orderUser(UserEntity userEntity) {
        if(userEntity.getType() == 0){
            List<BusinessEntity> businessDTO = userMapper.orderUser(userEntity);
            return ResponseUtil.successToClient(businessDTO);
        }else if (userEntity.getType() == 1){
            List<BusinessEntity> businessDTO = userMapper.getbusinessList(userEntity.getPassword());
            return ResponseUtil.successToClient(businessDTO);
        }
        return null;
    }

    @Override
    public void isRegister2(UserEntity userEntity) {
        UserEntity userEntity2 =  userMapper.isRegister1(userEntity);
        if(userEntity2 == null){
            userMapper.register2(userEntity);
        }
    }


    public static String regComp(String item) {
        String num = "";
        // 替换中文
        String reg = "[\\u4e00-\\u9fa5]+";
        //截取QTM=入后面数字
        String comp1 = "[\\s\\S]*([QTM=]\\d*)[\\s\\S]*";
        //截取波|第前面数字
        if (item.matches(comp1)) {
            num = item.replaceFirst(comp1, "$1").replaceAll(reg, "").replace("=","");
        }
        return num;
    }
}
