package com.massageservice.service;

public interface BusinessService {

    /**
     *  商家列表
     *
     * @param
     * @param businessName
     * @return java.lang.String
     * @methodName businessList
     * @author WuShunag
     * @date 15:18
     */
    String businessList(String businessName,String provincialId);

    /**
     * 
     * 
     * @param businessId 
     * @return java.lang.String
     * @methodName moneyUnsettled
     * @author WuShunag
     * @date 11:23
     */
    String moneyUnsettled(String businessId);

    String orderListByBusiness(String searchName, String businessId);
}
