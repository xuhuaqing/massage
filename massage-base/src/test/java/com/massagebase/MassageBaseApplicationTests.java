package com.massagebase;

import com.alibaba.fastjson.JSONObject;
import com.massagecommon.util.AddressUtil;
import com.massagecommon.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class MassageBaseApplicationTests {

    @Test
    void contextLoads() {

    }



    private static HashMap<String,Long> hashMap = new HashMap<>();




    public static void main(String[] args) {
       /* String addressByIp = AddressUtil.getAddressByIp("115.196.168.138");
        JSONObject jsonObject1 = JSONObject.parseObject(addressByIp);
        System.err.println(jsonObject1);
        JSONObject result = jsonObject1.getJSONObject("result");*/

       

    }
    public static void swap(List<?> list,int i,int j){
        final List l=list;
        l.set(i, l.set(j, l.get(i)));
    }



    public static String get(String str){
        String[] split = str.split("\\{");
        return "{"+split[split.length -1 ];
    }

}
