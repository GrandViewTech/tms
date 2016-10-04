package com.loylty.application.enviornment;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class CorsFilter
	{
		@Bean
		public static WebMvcConfigurer corsConfigurer()
			{
				return new WebMvcConfigurerAdapter()
					{
						@Override
						public void addCorsMappings(CorsRegistry registry)
							{
								registry.addMapping("/").allowedOrigins("http://localhost:8080", "http://192.168.102.122:8080/");
							}
					};
			}
			
	}
