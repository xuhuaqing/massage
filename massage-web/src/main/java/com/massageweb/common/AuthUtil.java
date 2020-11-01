package com.massageweb.common;

/**
 * Created by WuShuang on 2020/6/5.
 */

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import net.sf.json.JSONObject;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取accessToken
 *
 */
import java.io.IOException;

/**
 *
 * 工具类
 * 用来根据接口地址进行网络请求
 * @author wlw
 *
 */
public class AuthUtil {
    public static final String APP_ID = "wx2902d0ead8bf9e42";     //填写自己的APPID
    public static final String APP_SECRET = "0613d1d1e31a508a2b3c88ba3622daf3";   //填写自己的APPSECRET
    public static cn.hutool.json.JSON doGetJson(String url) throws Exception, IOException {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        cn.hutool.json.JSON json = JSONUtil.parse(response);
        return json;
    }

}

