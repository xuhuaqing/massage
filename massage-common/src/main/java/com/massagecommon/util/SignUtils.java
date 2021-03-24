package com.massagecommon.util;

import org.springframework.util.DigestUtils;

import java.util.TreeMap;
import java.util.UUID;

/**
 * @program: massage
 * @description: 加密签名
 * @author: wushuang
 * @create: 2021-03-24 21:35
 **/
public class SignUtils {

    public static String getSign(TreeMap<String, Object> params,long time,String replace,String replace1){
        params.put("timestamp",time);
        params.put("nonce",replace);
        params.put("token",replace1);
        StringBuilder s1 = new StringBuilder();
        for (String key : params.keySet()) {
            s1.append(key).append("=").append(params.get(key)).append("&");
        }
        s1.append("secret").append("=").append("6bf8f5bb65dca36b6b100177e6fa3277");
        return DigestUtils.md5DigestAsHex(s1.toString().getBytes()).toUpperCase();
    }
}
