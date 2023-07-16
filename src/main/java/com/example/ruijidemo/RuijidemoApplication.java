package com.example.ruijidemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableCaching
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement//开启事务注解
public class RuijidemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuijidemoApplication.class, args);
        log.info("项目启动~~~~~");
    }

}
