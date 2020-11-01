package com.massageservice.service.impl;

import com.github.pagehelper.Page;
import com.massagecommon.entity.BusinessEntity;
import com.massagecommon.entity.EquipmentEntity;
import com.massagecommon.entity.OrderEntity;
import com.massagecommon.util.ResponseUtil;
import com.massagedao.mapper.AdminUserMapper;
import com.massagedao.mapper.BusinessMapper;
import com.massagedao.mapper.EquipmentMapper;
import com.massageservice.service.BusinessService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author:WuShuang
 * @date:2020/5/3
 * @ver:1.0
 **/
@Service
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    private BusinessMapper businessMapper;
    @Autowired
    private EquipmentMapper equipmentMapper;


    @Override
    public String businessList(String businessName,String provincialId) {
       List<BusinessEntity> businessDTO = businessMapper.businessList(businessName,provincialId);
        return ResponseUtil.successToClient(businessDTO);
    }

   private static ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10);

    private static HashMap<String,String> hashMap = new HashMap<>();

    @Override
    public String moneyUnsettled(String businessId) {
        Double i = businessMapper.moneyUnsettled(businessId);
        Integer achievement =  businessMapper.findAchievement(businessId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("money",i);
        jsonObject.put("achievement",achievement);


        if(hashMap.get(businessId) == null){
            hashMap.put(businessId,getTime());
            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    Integer achievement =  businessMapper.findAchievement(businessId);
                    if(achievement == 0){
                        List<EquipmentEntity> equipmentEntities =  equipmentMapper.equipmentList(businessId,"1");
                        equipmentEntities.forEach(
                                s->{
                                    adminUserMapper.upEquipment(s.getEquipmentId(),"1");
                                }
                        );
                    }
                }
            } , 3 , TimeUnit.DAYS);
        }else if(dayDiff(getTime(),hashMap.get(businessId))>= 3){
            hashMap.clear();
            return moneyUnsettled(businessId);
        }
        return ResponseUtil.successToClient(jsonObject);
    }

    private static long dayDiff(String date1, String date2) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        long diff=0L;
        try {
            long d1 = formater.parse(date1).getTime();
            long d2 = formater.parse(date2).getTime();
            //diff=(Math.abs(d1-d2) / (1000 * 60 * 60 * 24));
            diff=(d1-d2)/(1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff;
    }

    private String getTime(){
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(day);
    }

    @Autowired
    private AdminUserMapper adminUserMapper;
    @Override
    public String orderListByBusiness(String searchName, String businessId) {
        List<OrderEntity> order = adminUserMapper.findOrder(searchName, businessId, "");
        if(!order.isEmpty()){
            return ResponseUtil.successToClient(order);
        }else {
            List<OrderEntity> list =  businessMapper.findOrder(searchName,businessId);
            return ResponseUtil.successToClient(list);
        }

    }

}
