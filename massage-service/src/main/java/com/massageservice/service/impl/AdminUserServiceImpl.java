package com.massageservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.massagecommon.entity.*;
import com.massagecommon.util.*;
import com.massagedao.mapper.AdminUserMapper;
import com.massagedao.mapper.BusinessMapper;
import com.massageservice.service.AdminUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.massagecommon.constant.Constants.*;
import static com.massagecommon.constant.MsgConstant.SUCCESS_MSG;

/**
 * @author:WuShuang
 * @date:2020/5/4
 * @ver:1.0
 **/
@Service
public class AdminUserServiceImpl implements AdminUserService


{
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String adminLogin(AdminCommon adminCommon) {
        String md5 = Md5Util.getMD5(adminCommon.getPassWord());
        adminCommon.setPassWord(md5);
        Integer integer = adminUserMapper.adminLogin(adminCommon);
        if(integer > 0){
            return ResponseUtil.toClient(SUCCESS_ADMIN_CODE+"",SUCCESS_MSG);
        }
        return ResponseUtil.errorMsgToClient(PASSWORD_ERROR_CODE+"", PASSWORD_ERROR_MSG);

    }

    @Override
    public String changePassword(String oldPassword, String password, String rePassword) {
        Integer i = adminUserMapper.selectOldPas(Md5Util.getMD5(oldPassword));
        String sendMsg = (String) redisUtil.get("sendMsg");

        if(sendMsg.equals(oldPassword)){
            return ResponseUtil.toClient(ERROR_CODE+"","验证码错误！");
        }

        if(!password.equals(rePassword)){
            return ResponseUtil.toClient(ERROR_CODE+"","两次密码不一致！");
        }
        adminUserMapper.changePassword(Md5Util.getMD5(password));
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String findBusinessManage(Integer page, Integer pageSize, Integer type,String agentId,String teacherType,String userId) {
        PageHelper.startPage(page,pageSize);
        Page<BusinessDTO> mapUserEntities =  adminUserMapper.findBusinessManage(type,agentId,teacherType,userId);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    @Override
    public String deleteBusiness(String userId) {
        adminUserMapper.deleteBusiness(userId);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String businessAdd(BusinessDTO businessDTO) {
        adminUserMapper.businessAdd(businessDTO);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);

    }

    @Override
    public String updateBusiness(BusinessDTO businessDTO) {
        adminUserMapper.updateBusiness(businessDTO);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);

    }

    @Override
    public String findOneClassification(Integer page, Integer pageSize, String projectId) {
        PageHelper.startPage(page,pageSize);
        Page<ProjectEntity.ProjectFour> mapUserEntities =  adminUserMapper.findOneClassification(projectId);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    @Override
    public String updOneClassification(String projectId, String projectImg) {
        adminUserMapper.updOneClassification(projectId,projectImg);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String showClassification(String projectId) {
        ProjectEntity.ProjectFour projectFour =  adminUserMapper.showClassification(projectId);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG,projectFour);
    }

    @Override
    public String updateClassificationProject(ProjectEntity.ProjectFour projectFour) {
        adminUserMapper.updateClassificationProject(projectFour);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String findEquipment(String equipmentId, Integer page, Integer pageSize, String userName) {
        PageHelper.startPage(page,pageSize);
        Page<EquipmentEntity.EquipmentDTO> mapUserEntities =  adminUserMapper.findEquipment(equipmentId,userName);
        List<EquipmentEntity.EquipmentDTO> collect = mapUserEntities.getResult().stream().map(
                equipmentDTO -> {
                    equipmentDTO.setAddress(getAddress(equipmentDTO.getEquipmentId()));
                    return equipmentDTO;
                }
        ).collect(Collectors.toList());
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, collect, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    private String getAddress(String id){
        Object o = redisUtil.get("ip" + id);
        if(o!= null){
            String string = o.toString();
            System.err.println("id="+id);
            String addressByIp = AddressUtil.getAddressByIp(string);
            JSONObject jsonObject1 = JSONObject.parseObject(addressByIp);
            if(jsonObject1 != null){
                JSONObject result = jsonObject1.getJSONObject("result");
                String province = result.getString("lng");
                String city = result.getString("lat");
                return getAddressNameByGeocoder1(province+","+city);
            }
        }
        return "";
    }


    /**
     * 获取地理位置名称
     *
     * param location
     *            经纬度坐标,经度在前,纬度在后，经纬度间以“,”分割
     * return
     */
    public static String getAddressNameByGeocoder1(String location) {
        String jsonString = sendGet(getRegeoUrl(location));
        System.err.println(jsonString);
        try{
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            String addressName = jsonObject.getJSONObject("regeocode").getString("formatted_address");
            return addressName;
        }catch (Exception e){
            return "";
        }

    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * param url
     *            发送请求的URL
     * return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            return null;
        } finally { // 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 逆地理编码url
     *
     * param location
     *            经纬度坐标,经度在前,纬度在后，经纬度间以“,”分割
     * return
     */
    public static String getRegeoUrl(String location) {
        return "http://restapi.amap.com/v3/geocode/regeo?location=" + location
                + "&key=17479d86c0c6a0305024e1142351a0a4";
    }



    @Override
    public String upEquipment(String equipmentId, String status) {
        adminUserMapper.upEquipment(equipmentId,status);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String addEquipment(EquipmentEntity.EquipmentDTO equipmentDTO) {
        Integer i =  adminUserMapper.isEquipment(equipmentDTO.getEquipmentId());
        if(i == 0){
            equipmentDTO.setId(Integer.parseInt(GenerateRandomCode.generNumCode(7)));
            adminUserMapper.addEquipment(equipmentDTO);
        }
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);

    }

    @Override
    public String businessList() {
        List<BusinessEntity> businessEntities = businessMapper.businessList("","");
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG,businessEntities);
    }

    @Override
    public String updEquipment(EquipmentEntity.EquipmentDTO equipmentDTO) {
        businessMapper.updEquipment(equipmentDTO);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String findOrder(String orderName, Integer page, Integer pageSize, String userId, String projectName) {
        PageHelper.startPage(page,pageSize);
        Page<OrderEntity> mapUserEntities =  adminUserMapper.findOrder(orderName,userId,projectName);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    @Override
    public String exportExcelByUser(String[] id) {
        List<OrderEntity> orderEntities = adminUserMapper.exportExcelByUser(id);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "",orderEntities);
    }

    @Override
    public String updateOrder(String id, String totalTimes, String field, String userName) {
        if(field.equals("userName")){
            adminUserMapper.updateName(totalTimes,userName);
            adminUserMapper.updateOrderName(id,totalTimes,userName);
            return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
        }
        adminUserMapper.updateOrder(id,totalTimes);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String settleBusiness(String userId) {
        adminUserMapper.settleBusiness(userId);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG);
    }

    @Override
    public String exportExcelByTeacherManage(String date, String[] userId) {
        String endDate = "";
        if(!StringUtils.isNotEmpty(date)){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar1=Calendar.getInstance();
            calendar1.set(Calendar.DAY_OF_MONTH, 1);
            date = sdf.format(calendar1.getTime());
            endDate = sdf.format(new Date());
        }else {
            String[] split = date.split(" - ");
            date = split[0];
            endDate = split[1];
        }
        List<TeacherOrderDTO> teacherOrderDTOS =  adminUserMapper.exportExcelByTeacherManage(date,endDate,userId);
        return ResponseUtil.successToClient(teacherOrderDTOS);
    }

    @Override
    public String exportExcelByBusinessManage(String date, String[] userId) {
        String endDate = "";
        if(!StringUtils.isNotEmpty(date)){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar1=Calendar.getInstance();
            calendar1.set(Calendar.DAY_OF_MONTH, 1);
            date = sdf.format(calendar1.getTime());
            endDate = sdf.format(new Date());
        }else {
            String[] split = date.split(" - ");
            date = split[0];
            endDate = split[1];
        }
        List<TeacherOrderDTO.BusinessOrderDTO> businessOrderDTOS =  adminUserMapper.exportExcelByBusinessManage(date,endDate,userId);

        return ResponseUtil.successToClient(businessOrderDTOS);

    }

    @Override
    public String deleteOrder(String id) {
        adminUserMapper.deleteOrder(id);
        return ResponseUtil.successToClient();
    }

    @Override
    public String exportExcelByBusinessManageAchievement(String date, String[] userId) {
        String endDate = "";
        if(!StringUtils.isNotEmpty(date)){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar1=Calendar.getInstance();
            calendar1.set(Calendar.DAY_OF_MONTH, 1);
            date = sdf.format(calendar1.getTime());
            endDate = sdf.format(new Date());
        }else {
            String[] split = date.split(" - ");
            date = split[0];
            endDate = split[1];
        }
       List<TeacherOrderDTO> teacherOrderDTOS =  adminUserMapper.exportExcelByBusinessManageAchievement(date,endDate,userId);
        return ResponseUtil.successToClient(teacherOrderDTOS);
    }

    @Override
    public String findProvincial(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        Page<ProvincialEntity> mapUserEntities =  adminUserMapper.findProvincial();
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, mapUserEntities, new HashMap<String,Object>(1) {
            {
                put("count", mapUserEntities.getTotal());
            }
        });
    }

    @Override
    public String findRangeList(Integer page, Integer pageSize, String id) {
        PageHelper.startPage(page,pageSize);
        Page<RangeDTO> rangeDTOS =  adminUserMapper.findRangeList(id);
        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE + "", SUCCESS_MSG, rangeDTOS, new HashMap<String,Object>(1) {
            {
                put("count", rangeDTOS.getTotal());
            }
        });
    }

    @Override
    public String updateRange(String id, String value, String field) {
        adminUserMapper.updateRange(id,value,field);
        return null;
    }

}
