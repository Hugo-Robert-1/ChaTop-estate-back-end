package com.chatop.estate.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
}
