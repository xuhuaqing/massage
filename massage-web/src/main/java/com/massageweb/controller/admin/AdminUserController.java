package com.massageweb.controller.admin;

import com.massagecommon.entity.AdminCommon;
import com.massagecommon.entity.BusinessDTO;
import com.massagecommon.entity.EquipmentEntity;
import com.massagecommon.entity.ProjectEntity;
import com.massagecommon.util.GenerateRandomCode;
import com.massagecommon.util.MailUtils;
import com.massagecommon.util.RedisUtil;
import com.massagecommon.util.ResponseUtil;
import com.massageservice.service.AdminUserService;
import com.massageservice.service.BusinessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.massagecommon.constant.Constants.REMINDTIME;
import static com.massagecommon.constant.Constants.SUCCESS_ADMIN_CODE;
import static com.massagecommon.constant.MsgConstant.SUCCESS_MSG;

/**
 * @author:WuShuang
 * @date:2020/5/4
 * @ver:1.0
 **/
@RequestMapping("/admin/user/")
@RestController
public class AdminUserController {


    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BusinessService businessService;


    /**
     * 修改订单
     *
     * @Author: WuShuang on 2020/6/5  16:38
     * @param: []
     * @return: java.lang.String
     * @Description:
     */
    @PostMapping("updateOrder")
    public String updateOrder(String id,String totalTimes,String field,String userName){
        return adminUserService.updateOrder(id,totalTimes,field,userName);
    }


    /**
     * 用户登陆
     *
     * @param
     * @return java.lang.String
     * @methodName adminLogin
     * @author WuShunag
     * @date 0:55
     */
    @PostMapping("adminLogin")
    public String  adminLogin(AdminCommon adminCommon){
        return adminUserService.adminLogin(adminCommon);
    }


    /**
     * 发送邮件
     *
     * @param
     * @return java.lang.String
     * @methodName sendEmail
     * @author WuShunag
     * @date 1:16
     */
    @PostMapping("sendEmail")
    public String sendEmail(){
        String checkCode = GenerateRandomCode.generNumCode(6);

        MailUtils instance = MailUtils.getInstance();
        try {
            instance.sendMultipleEmail("327625498@qq.com",checkCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        redisUtil.set("sendMsg",checkCode, REMINDTIME);

        return ResponseUtil.toClient(SUCCESS_ADMIN_CODE+"",SUCCESS_MSG);

    }


    /**
     *  修改密码
     *
     * @param oldPassword
    * @param password
    * @param rePassword
     * @return java.lang.String
     * @methodName changePassword
     * @author WuShunag
     * @date 1:11
     */
    @PostMapping("changePassword")
    public String changePassword(String oldPassword,String password,String rePassword){
        return adminUserService.changePassword(oldPassword,password,rePassword);
    }

    /**
     * 商户管理
     *
     * agentId 是 经销商省份
     *
     * teacherType 是老师的身份
     *
     * @param
     * @return java.lang.String
     * @methodName findBusinessManage
     * @author WuShunag
     * @date 2:07
     */
    @PostMapping("findBusinessManage")
    public String findBusinessManage(Integer page,Integer pageSize , Integer type,@RequestParam(value = "agentId",required = false) String agentId,@RequestParam(value = "teacherType",required = false) String teacherType,@RequestParam(value = "userId",required = false) String userId){
        return adminUserService.findBusinessManage(page,pageSize,type,agentId,teacherType,userId);
    }

    /**
     * 删除商户
     *
     * @param
     * @return java.lang.String
     * @methodName deleteBusiness
     * @author WuShunag
     * @date 2:16
     */
    @PostMapping("deleteBusiness")
    public String deleteBusiness(String userId){
        return adminUserService.deleteBusiness(userId);
    }

    /**
     *  业绩结算
     *
     * @param
     * @return java.lang.String
     * @methodName settleBusiness
     * @author WuShunag
     * @date 0:45
     */
    @PostMapping("settleBusiness")
    public String settleBusiness(String userId){
        return adminUserService.settleBusiness(userId);
    }

    /**
     *  添加商户
     *
     * @param
     * @return java.lang.String
     * @methodName businessAdd
     * @author WuShunag
     * @date 2:28
     */
    @PostMapping("businessAdd")
    public String businessAdd(BusinessDTO businessDTO){
        return adminUserService.businessAdd(businessDTO);
    }

    /**
     *  修改商户
     *
     * @param
     * @return java.lang.String
     * @methodName updateBusiness
     * @author WuShunag
     * @date 2:35
     */
    @PostMapping("updateBusiness")
    public String updateBusiness(BusinessDTO businessDTO){
        return adminUserService.updateBusiness(businessDTO);

    }

    /**
     *  一级分类
     *
     * @param
     * @return java.lang.String
     * @methodName findOneClassification
     * @author WuShunag
     * @date 15:48
     */
    @PostMapping("findOneClassification")
    public String findOneClassification(Integer page,Integer pageSize,String projectId){
        return adminUserService.findOneClassification(page,pageSize,projectId);
    }

    /**修改一级图片
     *
     *
     * @param
     * @return java.lang.String
     * @methodName updOneClassification
     * @author WuShunag
     * @date 16:10
     */
    @PostMapping("updOneClassification")
    public String updOneClassification(String projectId,String projectImg){
        return adminUserService.updOneClassification(projectId,projectImg);
    }

    /**
     * 根据分类查询
     *
     * @param
     * @return java.lang.String
     * @methodName showClassification
     * @author WuShunag
     * @date 17:12
     */
    @PostMapping("showClassification")
    public String showClassification(String projectId){
        return adminUserService.showClassification(projectId);
    }


    /**
     *  修改分类信息
     *
     * @param
     * @return java.lang.String
     * @methodName updateClassificationProject
     * @author WuShunag
     * @date 17:32
     */
    @PostMapping("updateClassificationProject")
    public String updateClassificationProject(ProjectEntity.ProjectFour projectFour){
        return adminUserService.updateClassificationProject(projectFour);
    }

    /**
     *  设备列表
     *
     * @param
     * @return java.lang.String
     * @methodName findEquipment
     * @author WuShunag
     * @date 18:49
     */
    @PostMapping("findEquipment")
    public String findEquipment(String equipmentId,Integer page,Integer pageSize,String userName){
        return adminUserService.findEquipment(equipmentId,page,pageSize,userName);
    }

    /**
     *  停用 或 启用
     *
     * @param
     * @return java.lang.String
     * @methodName upEquipment
     * @author WuShunag
     * @date 19:51
     */
    @PostMapping("upEquipment")
    public String upEquipment(String equipmentId,String status){
        return adminUserService.upEquipment(equipmentId,status);
    }

    /**
     *  添加设备
     *
     * @param
     * @return java.lang.String
     * @methodName addEquipment
     * @author WuShunag
     * @date 20:13
     */
    @PostMapping("addEquipment")
    public String addEquipment(EquipmentEntity.EquipmentDTO equipmentDTO){
        return adminUserService.addEquipment(equipmentDTO);
    }

    @PostMapping("businessList")
    public String businessList(){
        return adminUserService.businessList();
    }

    /**
     *  修改
     *
     * @param
     * @return java.lang.String
     * @methodName updEquipment
     * @author WuShunag
     * @date 21:44
     */
    @PostMapping("updEquipment")
    public String updEquipment(EquipmentEntity.EquipmentDTO equipmentDTO){
        return adminUserService.updEquipment(equipmentDTO);
    }


    /**
     *  订单列表
     *
     * @param
     * @return java.lang.String
     * @methodName findOrder
     * @author WuShunag
     * @date 0:11
     */
    @PostMapping("findOrder")
    public String findOrder(String orderName,Integer page,Integer pageSize,String userId,@RequestParam(value = "projectName",required = false) String projectName){
        return adminUserService.findOrder(orderName,page,pageSize,userId,projectName);

    }

    /**
     * 导出信息
     *
     * @Author: WuShuang on 2020/3/11  16:28
     * @param: []
     * @return: com.pointcoil.util.Response
     * @Description:
     */
    @PostMapping("exportExcelByUser")
    public String exportExcelByUser(String [] id){
        System.err.println(id);
        return adminUserService.exportExcelByUser(id);
    }

    /**
     *  根据老师导出订单信息
     *
     * @param
     * @return java.lang.String
     * @methodName exportExcelByTeacherManage
     * @author WuShunag
     * @date 18:26
     */
    @PostMapping("exportExcelByTeacherManage")
    public String exportExcelByTeacherManage(String date,String [] userId){
            return adminUserService.exportExcelByTeacherManage(date,userId);
    }


    /**
     *  商家的消耗记录
     *
     * @param
     * @return java.lang.String
     * @methodName exportExcelByBusinessManage
     * @author WuShunag
     * @date 20:52
     */
    @PostMapping("exportExcelByBusinessManage")
    public String exportExcelByBusinessManage(String date,String [] userId){

        return adminUserService.exportExcelByBusinessManage(date,userId);
    }

    /**
     *  删除订单
     *
     * @param
     * @return java.lang.String
     * @methodName deleteOrder
     * @author WuShunag
     * @date 23:20
     */
    @PostMapping("deleteOrder")
    public String deleteOrder(String id){
        return adminUserService.deleteOrder(id);
    }


    /**
     *  店家的销售订单
     *
     * @param
     * @return java.lang.String
     * @methodName exportExcelByBusinessManageAchievement
     * @author WuShunag
     * @date 23:13
     */
    @PostMapping("exportExcelByBusinessManageAchievement")
    public String exportExcelByBusinessManageAchievement(String date,String [] userId){
        return adminUserService.exportExcelByBusinessManageAchievement(date,userId);
    }

    /**
     *  查询省份
     *
     * @param
     * @return java.lang.String
     * @methodName exportExcelByBusinessManageAchievement
     * @author WuShunag
     * @date 23:13
     */
    @PostMapping("findProvincial")
    public String findProvincial(Integer page,Integer pageSize){
        return adminUserService.findProvincial(page,pageSize);
    }


    /**
     *  查询价格区间
     *
     * @param
     * @return java.lang.String
     * @methodName exportExcelByBusinessManageAchievement
     * @author WuShunag
     * @date 23:13
     */
    @PostMapping("findRangeList")
    public String findRangeList(Integer page,Integer pageSize,String id){
        return adminUserService.findRangeList(page,pageSize,id);
    }

    /**
     *  修改价格区间
     *
     * @param
     * @return java.lang.String
     * @methodName exportExcelByBusinessManageAchievement
     * @author WuShunag
     * @date 23:13
     */
    @PostMapping("updateRange")
    public String updateRange(String id,String value,String field){
        return adminUserService.updateRange(id,value,field);
    }
}
