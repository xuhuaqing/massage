package com.massageweb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;



/**
 * @author:WuShuang
 * @date:2020/5/6
 * @ver:1.0
 **/
@RestController
@RequestMapping("/facService/")
public class Equipment {


    @Autowired
    private Server1 service;
    @Value("${massage.SERVICE_IP}")
    public String SERVICE_IP;

    @Value("${massage.TcpService}")
    public int SERVICE_PORT;

    @RequestMapping("facStatus")
    public void test() throws IOException {
        service.startService(SERVICE_IP,SERVICE_PORT);
    }



}
