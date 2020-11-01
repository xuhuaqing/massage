package com.massageweb.conf;

import com.massagecommon.util.GenerateRandomCode;
import com.massagecommon.util.RedisUtil;
import com.massagedao.mapper.CronMapper;
import com.massagedao.mapper.DemoDao;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:WuShuang
 * @date:2019/6/12
 * @ver:1.0
 **/
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class DynamicScheduleTask implements SchedulingConfigurer {


    @Autowired
    private RedisUtil redisUtil;


    private long time =   30 * 60 * 1000;
    private long sendTime =   5 * 60 * 1000;
    public static final String APPKEY ="3624f7c8bb8fee2c6a98b68fb7055fb1";
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";


    @Autowired      //注入mapper
    @SuppressWarnings("all") //抑制所有类型的警告
     CronMapper cronMapper;
    @Autowired
    private DemoDao demoDao;

   // MapUserMapper mapUserMapper;



    /**
     * 执行定时任务.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () ->{
                    String s = GenerateRandomCode.generNumCode(7);
                    demoDao.updateTime(s);
                    redisUtil.remove(2+"*");
                    redisUtil.remove(1+"*");
                },
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    String cron = cronMapper.getCron();
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron)) {
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }



    public
    static  String net(String strUrl, Map params,String method) throws
            Exception {

        HttpURLConnection conn = null;

        BufferedReader reader = null;

        String rs = null;

        try
        {

            StringBuffer sb = new
                    StringBuffer();

            if(method==null
                    || method.equals("GET")){

                strUrl = strUrl+"?"+urlencode(params);

            }

            URL url = new
                    URL(strUrl);

            conn = (HttpURLConnection) url.openConnection();

            if(method==null
                    || method.equals("GET")){

                conn.setRequestMethod("GET");

            }else{

                conn.setRequestMethod("POST");

                conn.setDoOutput(true);

            }

            conn.setRequestProperty("User-agent", userAgent);

            conn.setUseCaches(false);

            conn.setConnectTimeout(DEF_CONN_TIMEOUT);

            conn.setReadTimeout(DEF_READ_TIMEOUT);

            conn.setInstanceFollowRedirects(false);

            conn.connect();

            if
            (params!= null
                    && method.equals("POST")) {

                try
                        (DataOutputStream out = new
                                DataOutputStream(conn.getOutputStream())) {

                    out.writeBytes(urlencode(params));

                }

            }

            InputStream is = conn.getInputStream();

            reader = new
                    BufferedReader(new InputStreamReader(is, DEF_CHATSET));

            String strRead = null;
            while
            ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch
        (IOException e) {
            e.printStackTrace();
        } finally
        {
            if
            (reader != null) {
                reader.close();
            }
            if
            (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }



    //将map型转为请求参数型

    public static  String urlencode(Map<String,Object>data) {
        StringBuilder sb = new
                StringBuilder();
        for
        (Map.Entry i : data.entrySet()) {
            try
            {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch
            (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


}