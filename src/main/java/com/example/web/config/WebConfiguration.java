package com.example.web.config;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.BookInitRunner;
import com.example.formatter.BookFormatter;
import com.example.repository.BookRepository;
import com.example.web.interceptor.DemoInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
	@Autowired
	private BookRepository repository;

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
		registry.addFormatter(new BookFormatter(repository));
	}


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
	/*
	 * @Bean public ByteArrayHttpMessageConverter
	 * byteArrayHttpMessageConverter() { return new
	 * ByteArrayHttpMessageConverter(); }
	 */

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(true);
	}
	//Spring Boot 默认配置的/**映射到/static（或/public ，/resources，/META-INF/resources）
	//，/webjars/**会映射到classpath:/META-INF/resources/webjars/。
	//下面的配置是在默认的基础上增加的额外静态资源的配置
	//在web页面引用静态资源是相对于上面说的那些路径的。所以写静态资源的时候，不要带上映射的目录名
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/internal/**").addResourceLocations("classpath:/");
	}

	@Bean
	public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
		return (ConfigurableEmbeddedServletContainer container) -> {
			container.setSessionTimeout(1, TimeUnit.MINUTES);
		};
	}

	@Bean
	public BookInitRunner initData() {
		return new BookInitRunner();
	}

	@Bean
	ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		return registrationBean;
	}

}