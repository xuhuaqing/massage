package com.massagedao.mapper;

import com.massagecommon.entity.Demo;
import org.apache.ibatis.annotations.Param;

public interface DemoDao {
    /**
     * @param demo
     * @return void
     * @methodName add
     * @author WuShunag
     * @date 19:33
     */
    void add(@Param("demo") Demo demo);


    /**
     *  修改订单时间
     *
     * @param
     * @param s
     * @return void
     * @methodName updateTime
     * @author WuShunag
     * @date 22:04
     */
    void updateTime(@Param("s") String s);


    void updateBusiness();
}
