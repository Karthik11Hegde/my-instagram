package com.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.DiscoveryClient;

@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
