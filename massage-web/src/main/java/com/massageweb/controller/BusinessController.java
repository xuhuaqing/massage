package com.massageweb.controller;

import com.massageservice.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author:WuShuang
 * @date:2020/5/3
 * @ver:1.0
 **/
@RestController
@RequestMapping("/api/business/")
public class BusinessController {


    @Autowired
    private BusinessService businessService;

    /**
     *  商家列表
     *
     * @param
     * @return java.lang.String
     * @methodName businessList
     * @author WuShunag
     * @date 15:17
     */
    @PostMapping("businessList")
    public String businessList(@RequestParam(required = false) String businessName,@RequestParam(required = false) String provincialId ){
       return businessService.businessList(businessName,provincialId);
    }


    /**
     *  未结算中业绩
     *
     * @param
     * @return java.lang.String
     * @methodName moneyUnsettled
     * @author WuShunag
     * @date 10:54
     */
    @PostMapping("moneyUnsettled")
    public String moneyUnsettled(String businessId){
        return businessService.moneyUnsettled(businessId);
    }


    /**
     *  商家的所有订单  根据项目名称/顾客姓名
     *
     * @param
     * @return java.lang.String
     * @methodName orderListByBusiness
     * @author WuShunag
     * @date 19:33
     */
    @PostMapping("orderListByBusiness")
    public String orderListByBusiness(@RequestParam(required = false) String searchName,String businessId){
        return businessService.orderListByBusiness(searchName,businessId);
    }
}
