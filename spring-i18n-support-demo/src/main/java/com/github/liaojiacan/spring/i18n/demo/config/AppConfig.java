package com.github.liaojiacan.spring.i18n.demo.config;

import com.github.liaojiacan.spring.support.i18n.RefreshableMessageSource;
import com.github.liaojiacan.spring.support.i18n.provider.JdbcMessageSoucreProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.github.liaojiacan" })
public class AppConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Bean
	public JdbcMessageSoucreProvider jdbcMessageSoucreProvider(){
		JdbcMessageSoucreProvider provider = new JdbcMessageSoucreProvider();
		provider.setJdbcTemplate(jdbcTemplate);
		return provider;
	}

	@Bean
	public RefreshableMessageSource refreshableMessageSource(){
		RefreshableMessageSource messageSource = new RefreshableMessageSource(jdbcMessageSoucreProvider());
		messageSource.setReturnUnresolvedCode(true);
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(Locale.US);
		return resolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("locale");
		return interceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
}
