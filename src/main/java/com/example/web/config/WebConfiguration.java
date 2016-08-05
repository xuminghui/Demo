package com.example.web.config;

import java.time.LocalDate;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.ui.context.ThemeSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.resource.ContentVersionStrategy;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.theme.AbstractThemeResolver;
import org.springframework.web.servlet.theme.FixedThemeResolver;

import com.example.BookInitRunner;
import com.example.formatter.BookFormatter;
import com.example.formatter.ColorFormatter;
import com.example.formatter.SexFormatter;
import com.example.repository.BookRepository;
import com.example.web.filter.SomeFilter;
import com.example.web.interceptor.DemoInterceptor;
import com.example.web.interceptor.MyInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
	@Autowired
	private BookRepository repository;
	@Autowired
	private MessageSource messageSouce;
	/*
	 * @Autowired private MessageSource messageSource;
	 */

	/*
	 * @Override public Validator getValidator() { LocalValidatorFactoryBean
	 * factory = new LocalValidatorFactoryBean();
	 * factory.setValidationMessageSource(messageSource); return factory; }
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/profile").setViewName("profile");
		registry.addViewController("/test").setViewName("testAngular");
		registry.addViewController("/404").setViewName("404");
		registry.addViewController("/500").setViewName("500");
		registry.addViewController("/errorCustomer").setViewName("errorCustomer");
		registry.addViewController("/testAny").setViewName("testAny");
		registry.addViewController("/test/untilObjects").setViewName("thymeleafUtilObject");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		//spring boot已经内置了dataFormatter和numberFormmater
		//这里可以增加formatter和converter两种
		//registry.addFormatter(new DateFormatter());// 必须显示的指定，才能转
		//registry.addFormatter(new DateFormatter());
		registry.addFormatterForFieldType(LocalDate.class, new LocalDateFormatter());
		registry.addFormatter(new BookFormatter(repository));
		//registry.addConverter(new String2SexConvertor());
		registry.addFormatter(new SexFormatter());
		registry.addFormatter(new ColorFormatter());
	}

	/**
	 * 国际化配置
	 * 
	 * @return
	 */
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.US);
		return slr;
	}

	/**
	 * 切换 param设置为lang的值
	 * 
	 * @return
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Bean
	public DemoInterceptor demoInterceptor() {
		return new DemoInterceptor();
	}

	@Bean
	public WebContentInterceptor webContentInterceptor() {
		WebContentInterceptor webContentInterceptor = new MyInterceptor();
		webContentInterceptor.setCacheSeconds(0);
		// 对于静态资源如何过滤cache-control
		webContentInterceptor.addCacheMapping(CacheControl.maxAge(1, TimeUnit.HOURS), "/img/*");
		// webContentInterceptor.setCacheControl(CacheControl.maxAge(2,
		// TimeUnit.HOURS));

		return webContentInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(demoInterceptor());
		registry.addInterceptor(localeChangeInterceptor());
		registry.addInterceptor(webContentInterceptor());
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

	// Spring Boot 默认配置的/**映射到/static（或/public ，/resources，/META-INF/resources）
	// ，/webjars/**会映射到classpath:/META-INF/resources/webjars/。
	// 下面的配置是在默认的基础上增加的额外静态资源的配置
	// 在web页面引用静态资源是相对于上面说的那些路径的。所以写静态资源的时候，不要带上映射的目录名
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		VersionResourceResolver versionResourceResolver = new VersionResourceResolver()
				.addVersionStrategy(new ContentVersionStrategy(), "/**");
		// registry.addResourceHandler("/internal/**").addResourceLocations("classpath:/")
		// addResourcehandler:创建一个逻辑路径
		// addResourceLocations：指定一个具体的位置
		registry.addResourceHandler("/js123/*.js").addResourceLocations("classpath:/static/js/")
				.setCachePeriod(60 * 60 * 24 * 365).resourceChain(true).addResolver(versionResourceResolver);
		// 通过这个设置可以对静态资源进行缓存过滤 ,这个地方是重点！！！！
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/").setCachePeriod(3600);
	}

	@Bean
	public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
		return (ConfigurableEmbeddedServletContainer container) -> {
			container.setSessionTimeout(1, TimeUnit.MINUTES);
		};
	}

	/*
	 * @Bean public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
	 * return new ResourceUrlEncodingFilter(); }
	 */

	@Bean
	public BookInitRunner initData() {
		return new BookInitRunner();
	}
	/**
	 * 设置主体源
	 * @return
	 */
	@Bean
	public ThemeSource themeSource() {
		AbstractThemeResolver a;
		ReloadableResourceBundleMessageSource b;
		AbstractMessageSource c;
		ResourceBundleThemeSource themeSource = new ResourceBundleThemeSource();
		themeSource.setBasenamePrefix("themes/");
		return themeSource;
	}
	@Bean
	public ThemeResolver themeResolver() {
		//好几个ThemeResolver的实现，可以实验下
	  ThemeResolver themeResolver = new FixedThemeResolver();
	  return themeResolver;
	}
	/**
	 * message国际化 ，不需要指定下面的BEAN，只需要在application.properties中指定basenam即可spring.messages.basename
	 * 在页面就可以通过#{}引用对应的key
	 * @return
	 */
	/*@Bean
	public MessageSource messageSource() {
	    final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

	    //messageSource.setBasename("i18n/messages");
	    messageSource.setFallbackToSystemLocale(false);
	    messageSource.setCacheSeconds(0);
	    return messageSource;
	}*/ 
	/**
	 * 通过如下配置，成功获取了Bean对应的消息
	 */
	@Override
    public Validator getValidator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSouce);
        return factory;
    }

	@Bean
	ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		return registrationBean;
	}
}