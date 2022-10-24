package com.lazun.usersapp.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    modelMapper.getConfiguration().setSkipNullEnabled(true);
    modelMapper.getConfiguration().setImplicitMappingEnabled(true);
    return modelMapper;
  }
}
