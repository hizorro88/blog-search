package com.kakao.search.apiservice.error;

import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

  @Bean
  feign.Logger.Level feignLoggerLevel() {
    return feign.Logger.Level.BASIC;
  }

  @Bean
  @ConditionalOnMissingBean(value = ErrorDecoder.class)
  public FeignClientExceptionErrorDecoder commonFeignErrorDecoder() {
    return new FeignClientExceptionErrorDecoder();
  }
}