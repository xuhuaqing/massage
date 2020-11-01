package com.massageweb.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: WuShuang
 * @Date: 2019-04-17 08:56
 * @Version: 1.0.0
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "massage-program")
public class MassageProgramProperties {

   private String url;
   private String imageUploadUrl;
   private Redis redis;
   @Data
   public static class Redis{
      private String homePageInit;
      private String homePageInitNews;
      private String phoneSearch;
      private String toExamineEmail;
      private String loginHomeKey;
      private String isRegister;
      private String businessInit;
      private String businessZonePageInit;
      private String labelPageInit;
      private String myBusinessZone;
      private String isAccountSub;
   }

}
