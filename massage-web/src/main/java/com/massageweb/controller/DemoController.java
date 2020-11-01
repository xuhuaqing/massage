package com.massageweb.controller;

import com.massagecommon.entity.Demo;
import com.massagecommon.util.RedisUtil;
import com.massagecommon.util.ResponseUtil;
import com.massageservice.service.DemoService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author:WuShuang
 * @date:2020/2/27
 * @ver:1.0
 **/
@RestController
/*@RequestMapping("/facService/")*/
public class DemoController {
/*

    @RequestMapping("facStatus")
    public String facStatus(HttpServletRequest request){
        int contentLength = request.getContentLength();
        System.err.println("长度 = "+contentLength);
        Map map = request.getParameterMap();
        Set set = map.entrySet();
        Map.Entry entry = (Map.Entry) set.iterator().next();
        String key = entry.getKey().toString();
        System.err.println("key = "+key);
        String [] tmp = (String[]) entry.getValue();
        String value = "";
        if (tmp.length > 0) {
            value = tmp[0];
        } else {
            System.err.println("value 为空");
        }
        System.err.println("value = "+value);
        return "ok";
    };


    @RequestMapping(value = "facTest/{key}/{value}")
    public String facStatus(@PathVariable("key") String key, @PathVariable("value") String value) throws Exception {
        System.err.println("key" + key);
        System.err.println("value" + value);
        return "ok";
    }
*/


    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("xhq")
    public String test(String id) {
        Object o = redisUtil.get(id);
        if (o == null) {
            List<String> list = redisUtil.lRange("lineUp" + id);
            if (!list.isEmpty()) {
                String s1 = list.get(0);
                System.err.println(s1);
                redisUtil.deleteList("lineUp" + id,1, s1);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", 1000);
                jsonObject.put("msg", "ok");
                return jsonObject.toString();
            }
            String value1 = "{ID=" + id + ",QSN=" + "1131231" + ",VTA=0,VTB=4000000,VTC=0}";
            //1。把要返回的 数据进行加密
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 1000);
            jsonObject.put("msg", value1);
            return jsonObject.toString();
        }

        return ResponseUtil.successToClient();

    }
}