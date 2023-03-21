package com.kakao.search.apiservice.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper allocationModelMapper = new ModelMapper();
    allocationModelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT)
        .setSkipNullEnabled(true);

    return allocationModelMapper;
  }

}
