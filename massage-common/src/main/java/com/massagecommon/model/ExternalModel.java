package com.massagecommon.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;

import javax.servlet.annotation.HttpConstraint;
import java.util.Objects;

/**
 * @program: massage
 * @description:
 * @author: wushuang
 * @create: 2021-03-24 21:52
 **/
@Data
public class ExternalModel {

    private Integer code;
    private String note;
    private Object data;


    public static ExternalModel parse(String text){
        return JSON.parseObject(text,ExternalModel.class);
    }

    public static boolean isSucc(Integer code){
        return Objects.equals(HttpStatus.SC_OK, code);
    }
}
