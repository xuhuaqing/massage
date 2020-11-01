package com.massageservice.service.impl;

import com.massagecommon.entity.Demo;
import com.massagedao.mapper.DemoDao;
import com.massageservice.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:WuShuang
 * @date:2020/2/27
 * @ver:1.0
 **/
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoDao demoDao;

    @Override
    public String add(Demo demo) {
        demoDao.add(demo);
        return "ok";
    }


}
