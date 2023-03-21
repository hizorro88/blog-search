package com.kakao.search.apiservice.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverBlogSearchFeignConfig extends FeignConfig {

  @Value("${naver.id}")
  private String id;

  @Value("${naver.secret}")
  private String secret;

  @Bean
  public RequestInterceptor requestInterceptor() {
    return template -> {
      template.header("X-Naver-Client-Id", id);
      template.header("X-Naver-Client-Secret", secret);
    };
  }
}
