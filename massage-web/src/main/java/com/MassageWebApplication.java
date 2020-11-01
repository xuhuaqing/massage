package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.massagedao")
public class MassageWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MassageWebApplication.class, args);
    }

}
