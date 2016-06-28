package com.example.web.config;

import java.time.LocalDate;

import javax.servlet.Filter;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.InitRunner;
import com.example.web.interceptor.DemoInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/profile").setViewName("profile");
		registry.addViewController("/test").setViewName("testAngular");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new DateFormatter());// 必须显示的指定，才能转换
		registry.addFormatterForFieldType(LocalDate.class, new LocalDateFormatter());
	}

	/*
	 * @Override public void addResourceHandlers(ResourceHandlerRegistry
	 * registry) { registry.addResourceHandler("/**").addResourceLocations(
	 * "classpath:/static/"); }
	 */
	@Bean
	public DemoInterceptor demoInterceptor() {
		return new DemoInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(demoInterceptor());
	}

	@Bean
	public FilterRegistrationBean someFilterRegistration() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(someFilter());
		registration.addUrlPatterns("/login");
		registration.addInitParameter("paramName", "paramValue");
		registration.setName("someFilter");
		registration.setOrder(1);
		return registration;
	}

	@Bean(name = "someFilter")
	public Filter someFilter() {
		return new SomeFilter();
	}

	/**
	 * tomcat 8 的获取真实IP的filter
	 * 
	 * @return
	 */
	/*
	 * @Bean public Filter remoteIPFilter() { return new RemoteIpFilter(); }
	 */
	/*@Bean
	public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
		return new ByteArrayHttpMessageConverter();
	}*/

	
	@Bean
	 public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
	  MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
	  ObjectMapper objectMapper = new ObjectMapper();
	  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	  jsonConverter.setObjectMapper(objectMapper);
	  return jsonConverter;
	 }

	@Bean
	public InitRunner initData() {
		return new InitRunner();
	}

}