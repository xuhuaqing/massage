package com.massageweb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:WuShuang
 * @date:2020/5/4
 * @ver:1.0
 **/
@Controller
@RequestMapping(value = "/admin/page/")
public class AdminController {


    @GetMapping("privateBusinessManage")
    private String privateBusinessManage() {
        return "user/privateBusinessManage";
    }
    @GetMapping("privateEquipmentAdd")
    private String privateEquipmentAdd() {
        return "user/privateEquipmentAdd";
    }
    @GetMapping("rangeList")
    private String rangeList() {
        return "user/rangeList";
    }
  @GetMapping("privateOrderList")
    private String privateOrderList() {
        return "user/privateOrderList";
    } @GetMapping("privateEqManage")
    private String privateEqManage() {
        return "user/privateEqManage";
    }

    @GetMapping("business_provincial")
    private String business_provincial() {
        return "user/business_provincial";
    }

    @GetMapping("agentAdd")
    private String agentAdd() {
        return "user/agentAdd";
    }

    @GetMapping("seeAgent")
    private String seeAgent() {
        return "user/seeAgent";
    }
    @GetMapping("agentUpdate")
    private String agentUpdate() {
        return "user/agentUpdate";
    }

    @GetMapping("agent")
    private String agent() {
        return "user/agent_provincial";
    }
    @GetMapping("orderList")
    private String orderList() {
        return "user/orderList";
    }

    @GetMapping("equipmentUpdate")
    private String equipmentUpdate() {
        return "user/equipmentUpdate";
    }
    @GetMapping("equipmentAdd")
    private String equipmentAdd() {
        return "user/equipmentAdd";
    }

    @GetMapping("teacherAdd")
    private String teacherAdd() {
        return "user/teacherAdd";
    }
    @GetMapping("teacherUpdate")
    private String teacherUpdate() {
        return "user/teacherUpdate";
    }
    @GetMapping("equipmentManage")
    private String equipmentManage() {
        return "user/equipmentManage";
    }

    @GetMapping("classificationUpdate")
    private String classificationUpdate() {
        return "user/classificationUpdate";
    }

    @GetMapping("industryManageFour")
    private String industryManageFour() {
        return "user/industryManageFour";
    }

    @GetMapping("industryManageThree")
    private String industryManageThree() {
        return "user/industryManageThree";
    }

    @GetMapping("industryManageTwo")
    private String industryManageTwo() {
        return "user/industryManageTwo";
    }
    @GetMapping("oneClassificationUpdate")
    private String oneClassificationUpdate() {
        return "user/oneClassificationUpdate";
    }
    @GetMapping("oneClassification")
    private String oneClassification() {
        return "user/oneClassification";
    }
    @GetMapping("teacherManage")
    private String teacherManage() {
        return "user/teacherManage";
    }

    @GetMapping("businessUpdate")
    private String businessUpdate() {
        return "user/businessUpdate";
    }

    @GetMapping("businessAdd")
    private String businessAdd() {
        return "user/businessAdd";
    }

    @GetMapping("businessManage")
    private String businessManage() {
        return "user/businessManage";
    }

    @GetMapping("password")
    private String password() {
        return "user/password";
    }

    @GetMapping("login")
    private String login() {
        return "user/login";
    }
    @GetMapping("index")
    private String index() {
        return "index/index";
    }
}
