package com.github.liaojiacan.spring.i18n.demo.config;

import com.github.liaojiacan.spring.support.i18n.MessageSourceProvider;
import com.github.liaojiacan.spring.support.i18n.RefreshableMessageSource;
import com.github.liaojiacan.spring.support.i18n.adivce.I18nResponseBodyAdvice;
import com.github.liaojiacan.spring.support.i18n.manager.MessageSourceProviderManager;
import com.github.liaojiacan.spring.support.i18n.provider.CustomMessageSourceProvider;
import com.github.liaojiacan.spring.support.i18n.provider.JdbcMessageSourceProvider;
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

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.github.liaojiacan.spring.i18n.demo" })
public class AppConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Bean
	public JdbcMessageSourceProvider jdbcMessageSourceProvider(){
		JdbcMessageSourceProvider provider = new JdbcMessageSourceProvider("default",jdbcTemplate);
		provider.setJdbcTemplate(jdbcTemplate);
		return provider;
	}


	@Bean
	public MessageSourceProviderManager messageSourceProviderManager(){
		MessageSourceProviderManager providerManager = new MessageSourceProviderManager();
		MessageSourceProvider articleI18nProvider = new CustomMessageSourceProvider("articleI18nProvider",jdbcTemplate,"i18n_article",
				Stream.of("title","description","a","b").collect(Collectors.toSet()), "id","article");
		providerManager.setProviders(Arrays.asList(jdbcMessageSourceProvider(),articleI18nProvider));
		return providerManager;
	}

	@Bean
	public RefreshableMessageSource refreshableMessageSource(){
		RefreshableMessageSource messageSource = new RefreshableMessageSource(messageSourceProviderManager());
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

	@Bean
	public I18nResponseBodyAdvice i18nResponseBodyAdvice(){
		return  new I18nResponseBodyAdvice();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
}
