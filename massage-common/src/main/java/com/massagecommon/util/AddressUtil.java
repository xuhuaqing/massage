package com.massagecommon.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;


import java.util.HashMap;
import java.util.Map;

/**
 * @author:WuShuang
 * @date:2020/6/18
 * @ver:1.0
 **/
public class AddressUtil {

    public static String getAddressByIp(String ip){
        String host = "https://ips.market.alicloudapi.com";
        String path = "/iplocaltion";
        String method = "GET";
        String appcode = "2b79732eee174169a178cd74793e849e";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("ip", ip);
        //JDK 1.8示例代码请在这里下载：  http://code.fegine.com/Tools.zip
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
//System.out.println(response.toString());如不输出json, 请打开这行代码，打印调试头部状态码。
            //状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
            //获取response的body
            return EntityUtils.toString(response.getEntity(),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        String addressByIp = AddressUtil.getAddressByIp("115.196.168.138");
        JSONObject jsonObject1 = JSONObject.parseObject(addressByIp);
        System.err.println(jsonObject1);
        JSONObject result = jsonObject1.getJSONObject("result");
        String province = result.getString("province");
    }
}
