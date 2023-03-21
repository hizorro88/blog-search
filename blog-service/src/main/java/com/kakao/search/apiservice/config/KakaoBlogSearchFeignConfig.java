package com.kakao.search.apiservice.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoBlogSearchFeignConfig extends FeignConfig {

  @Value("${kakao.key}")
  private String key;

  @Bean
  public RequestInterceptor requestInterceptor() {
    return template -> {
      template.header("Authorization", "KakaoAK " + key);
    };
  }
}
