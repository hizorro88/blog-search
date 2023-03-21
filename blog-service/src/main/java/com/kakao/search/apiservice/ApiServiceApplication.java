package com.kakao.search.apiservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication
@EnableFeignClients(basePackages = "com.kakao.search.apiservice")
@Slf4j
@ComponentScan(basePackages = "com.kakao.search.apiservice")
@EntityScan(basePackages = {"com.kakao.search.apiservice.domains"})
public class ApiServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiServiceApplication.class, args);
  }
}
